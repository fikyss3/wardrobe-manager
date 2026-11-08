package com.wardrobe.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class ImageController {
    private final Path uploadDir = Paths.get("uploads").toAbsolutePath().normalize();

    @GetMapping("/api/images/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) throws Exception {
        Path filePath = this.uploadDir.resolve(filename).normalize();
        Resource resource = new UrlResource(filePath.toUri());
        if (!resource.exists() || !resource.isReadable()) {
            throw new Exception("Файл не найден");
        }
        return ResponseEntity.ok().body(resource);
    }
}