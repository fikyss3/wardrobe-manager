package com.wardrobe.service.strategy;

import com.wardrobe.entity.ClothingItem;
import java.util.List;
import java.util.Map;

public interface AnalysisStrategy {
    Map<String, Long> analyze(List<ClothingItem> items);
}