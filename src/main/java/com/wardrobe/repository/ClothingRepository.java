package com.wardrobe.repository;

import com.wardrobe.entity.ClothingItem;
import org.springframework.data.jpa.repository.JpaRepository;

//Интерфейс для работы с базой данных. пустое тело автоматически генерир реализ методов
public interface ClothingRepository extends JpaRepository<ClothingItem, Long> {
}