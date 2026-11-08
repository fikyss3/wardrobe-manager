package com.wardrobe.service.strategy;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import com.wardrobe.entity.ClothingItem;


@Service
public class AnalysisContext {
    private final Map<String, AnalysisStrategy> strategies;

    public AnalysisContext(Map<String, AnalysisStrategy> strategies) {
        this.strategies = strategies;
    }

    public Map<String, Long> execute(String type, List<ClothingItem> items) {
        AnalysisStrategy strategy = strategies.get(type + "Strategy");
        if (strategy == null) throw new IllegalArgumentException("Неизвестная стратегия: " + type);
        return strategy.analyze(items);
    }
}