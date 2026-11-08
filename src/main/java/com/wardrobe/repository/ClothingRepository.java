package com.wardrobe.repository;

import com.wardrobe.entity.ClothingItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClothingRepository extends JpaRepository<ClothingItem, Long> {
}