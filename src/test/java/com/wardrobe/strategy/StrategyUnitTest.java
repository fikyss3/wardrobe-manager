package com.wardrobe.service.strategy;

import com.wardrobe.entity.ClothingItem;
import com.wardrobe.entity.Category;
import com.wardrobe.entity.DressCode;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

// Это настоящий модульный тест. Мы тестируем чистую Java-логику без запуска Spring и без Mockito.
class StrategyUnitTest {

    @Test
    void testCategoryStrategy() {
        // 1. Создаем реальные объекты (без моков!)
        CategoryStrategy strategy = new CategoryStrategy();

        ClothingItem item1 = new ClothingItem();
        item1.setCategory(Category.TOP);

        ClothingItem item2 = new ClothingItem();
        item2.setCategory(Category.TOP);

        ClothingItem item3 = new ClothingItem();
        item3.setCategory(Category.BOTTOM);

        // 2. Вызываем метод
        Map<String, Long> result = strategy.analyze(List.of(item1, item2, item3));

        // 3. Проверяем результат
        assertNotNull(result);
        assertEquals(2, result.get("TOP"));
        assertEquals(1, result.get("BOTTOM"));
    }

    @Test
    void testDressCodeStrategy() {
        DressCodeStrategy strategy = new DressCodeStrategy();

        ClothingItem item1 = new ClothingItem();
        item1.setDressCode(DressCode.CASUAL);

        ClothingItem item2 = new ClothingItem();
        item2.setDressCode(DressCode.HOME);

        Map<String, Long> result = strategy.analyze(List.of(item1, item2));

        assertEquals(1, result.get("CASUAL"));
        assertEquals(1, result.get("HOME"));
    }
}