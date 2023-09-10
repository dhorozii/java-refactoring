package com.gildedrose;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class GildedRose {

    private final List<ItemProcessor> itemProcessor;

    public GildedRose(Item[] items) {
        if (items == null || items.length == 0) {
            throw new IllegalArgumentException("Items array should not be null or empty");
        }
        this.itemProcessor = Arrays.stream(items).map(ItemProcessor::new).collect(Collectors.toList());
    }

    public void updateQuality() {
        itemProcessor.forEach(ItemProcessor::execute);
    }
}