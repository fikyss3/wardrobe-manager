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
            @RequestPart("name") String name,
            @RequestPart(value = "description", required = false) String description,
            @RequestPart("category") String category,
            @RequestPart("dressCode") String dressCode,
            @RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok(service.save(name, description, category, dressCode, file));
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> getStats(@RequestParam String type) {
        return ResponseEntity.ok(service.getStats(type));
    }
}