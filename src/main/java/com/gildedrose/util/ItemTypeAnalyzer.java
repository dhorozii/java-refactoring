package com.gildedrose.util;

import com.gildedrose.ItemTypeExecutorEnum;

import java.util.Arrays;

public class ItemTypeAnalyzer {

    public static ItemTypeExecutorEnum analyze(String itemName) {
        if (isBlankString(itemName)) {
            throw new IllegalArgumentException("Item name should not be blank");
        }
        return Arrays.stream(ItemTypeExecutorEnum.values())
                //we need to exclude 'USUAL' from the search because we don't have such keyword
                .filter(el -> !ItemTypeExecutorEnum.USUAL.getValue().equals(el.getValue()))
                .filter(type -> itemName.toLowerCase().contains(type.getValue()))
                .findFirst()
                .orElse(ItemTypeExecutorEnum.USUAL);
    }

    private static boolean isBlankString(String value) {
        return value == null || value.isEmpty();
    }
}
