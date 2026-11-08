package com.wardrobe.service.strategy;

import com.wardrobe.entity.ClothingItem;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component("categoryStrategy")
public class CategoryStrategy implements AnalysisStrategy {
    @Override
    public Map<String, Long> analyze(List<ClothingItem> items) {
        return items.stream().collect(Collectors.groupingBy(i -> i.getCategory().name(), Collectors.counting()));
    }
}