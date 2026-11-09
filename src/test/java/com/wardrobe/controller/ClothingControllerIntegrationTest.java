package com.wardrobe.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class WardrobeIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // Помогает парсить JSON в тестах

    @Test
    void testFullCreateReadDeleteFlow() throws Exception {
        // 1. Создаем фейковый файл картинки
        MockMultipartFile file = new MockMultipartFile(
                "file", "test-shirt.jpg", "image/jpeg", "fake-image-data".getBytes()
        );

        // 2. Тест создания (POST)
        String createResponse = mockMvc.perform(multipart("/api/clothes")
                        .file(file)
                        .param("name", "Синяя рубашка")
                        .param("description", "Тестовое описание")
                        .param("category", "TOP")
                        .param("dressCode", "CASUAL"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Синяя рубашка"))
                .andReturn().getResponse().getContentAsString();

        // Достаем ID созданной вещи из JSON-ответа
        Long createdId = objectMapper.readTree(createResponse).get("id").asLong();

        // 3. Тест получения (GET)
        mockMvc.perform(get("/api/clothes/" + createdId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Синяя рубашка"));

        // 4. Тест статистики (GET)
        mockMvc.perform(get("/api/clothes/stats")
                        .param("type", "category"))
                .andExpect(status().isOk());

        // 5. Тест удаления (DELETE)
        mockMvc.perform(delete("/api/clothes/" + createdId))
                .andExpect(status().isNoContent());

        // 6. Проверяем, что после удаления вещь действительно исчезла (должно вернуть 404)
        mockMvc.perform(get("/api/clothes/" + createdId))
                .andExpect(status().isNotFound());
    }
}