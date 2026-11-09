package com.wardrobe.controller;

import com.wardrobe.entity.ClothingItem;
import com.wardrobe.service.ClothingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clothes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ClothingController {
    private final ClothingService service;

    @GetMapping
    public ResponseEntity<List<ClothingItem>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClothingItem> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<ClothingItem> create(
            @RequestParam("name") String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam("category") String category,
            @RequestParam("dressCode") String dressCode,
            @RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok(service.save(name, description, category, dressCode, file));
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> getStats(@RequestParam String type) {
        return ResponseEntity.ok(service.getStats(type));
    }
    // Эндпоинт для удаления
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Эндпоинт для обновления
    @PutMapping("/{id}")
    public ResponseEntity<ClothingItem> update(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam(required = false) String description,
            @RequestParam String category,
            @RequestParam String dressCode) {
        return ResponseEntity.ok(service.update(id, name, description, category, dressCode));
    }
}