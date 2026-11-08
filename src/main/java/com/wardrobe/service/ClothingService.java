package com.wardrobe.service;

import com.wardrobe.entity.Category;
import com.wardrobe.entity.ClothingItem;
import com.wardrobe.entity.DressCode;
import com.wardrobe.exception.ClothingNotFoundException;
import com.wardrobe.exception.InvalidFileException;
import com.wardrobe.repository.ClothingRepository;
import com.wardrobe.service.strategy.AnalysisContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClothingService {
    private final ClothingRepository repository;
    private final AnalysisContext analysisContext;

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    public ClothingItem save(String name, String description, String category, String dressCode, MultipartFile file) {
        if (file == null || file.isEmpty()) throw new InvalidFileException("Файл обязателен");
        if (file.getContentType() == null || !file.getContentType().startsWith("image/")) {
            throw new InvalidFileException("Только изображения (JPG, PNG)");
        }

        try {
            Path path = Paths.get(uploadDir).toAbsolutePath().normalize();
            if (!Files.exists(path)) Files.createDirectories(path);

            String originalFilename = file.getOriginalFilename();
            String ext = originalFilename != null && originalFilename.contains(".")
                    ? originalFilename.substring(originalFilename.lastIndexOf("."))
                    : ".jpg";
            String filename = UUID.randomUUID().toString() + ext;
            Files.copy(file.getInputStream(), path.resolve(filename));

            ClothingItem item = new ClothingItem();
            item.setName(name);
            item.setDescription(description);
            item.setCategory(Category.valueOf(category.toUpperCase()));
            item.setDressCode(DressCode.valueOf(dressCode.toUpperCase()));
            item.setImageUrl("/api/images/" + filename);

            return repository.save(item);
        } catch (IOException e) {
            throw new InvalidFileException("Ошибка сохранения файла: " + e.getMessage());
        }
    }

    public List<ClothingItem> getAll() {
        return repository.findAll();
    }

    public ClothingItem getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ClothingNotFoundException(id));
    }

    public Map<String, Long> getStats(String type) {
        return analysisContext.execute(type, repository.findAll());
    }
}