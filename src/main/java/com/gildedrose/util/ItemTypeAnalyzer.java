package com.gildedrose.util;

import com.gildedrose.ItemType;

import java.util.Arrays;

public class ItemTypeAnalyzer {

    public static ItemType analyze(String itemName) {
        if (isBlankString(itemName)) {
            throw new IllegalArgumentException("Item name should not be blank");
        }
        return Arrays.stream(ItemType.values())
                //we need to exclude 'USUAL' from the search because we don't have such a keyword
                .filter(el -> !ItemType.USUAL.getValue().equals(el.getValue()))
                .filter(type -> itemName.toLowerCase().contains(type.getValue()))
                .findFirst()
                .orElse(ItemType.USUAL);
    }

    private static boolean isBlankString(String value) {
        return value == null || value.isEmpty();
    }
}
