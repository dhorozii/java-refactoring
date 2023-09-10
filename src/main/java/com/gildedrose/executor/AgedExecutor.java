package com.gildedrose.executor;

import com.gildedrose.Item;

public class AgedExecutor implements Executor {
    private final Item item;

    public AgedExecutor(Item item) {
        this.item = item;
    }

    @Override
    public void execute() {
        increaseQuality();
        decreaseSellIn();
        if (item.sellIn < 0) {
            increaseQuality();
        }
    }

    private void increaseQuality() {
        if (item.quality < MAX_ITEM_QUALITY) {
            item.quality++;
        }
    }

    private void decreaseSellIn() {
        item.sellIn--;
    }
}
