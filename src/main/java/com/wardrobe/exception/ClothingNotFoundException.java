package com.wardrobe.exception;

public class ClothingNotFoundException extends RuntimeException {
    public ClothingNotFoundException(Long id) {
        super("Вещь с ID " + id + " не найдена");
    }
}