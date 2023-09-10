package com.gildedrose.executor;

import com.gildedrose.Item;

public class BackstagePassExecutor implements Executor {
    private final static int SELL_IN_DOUBLE_QUALITY_DAYS = 11;
    private final static int SELL_IN_TRIPLE_QUALITY_DAYS = 6;
    private final Item item;

    public BackstagePassExecutor(Item item) {
        this.item = item;
    }

    @Override
    public void execute() {
        increaseQuality();

        if (item.sellIn < SELL_IN_DOUBLE_QUALITY_DAYS) {
            increaseQuality();
        }

        if (item.sellIn < SELL_IN_TRIPLE_QUALITY_DAYS) {
            increaseQuality();
        }

        decreaseSellIn();

        if (item.sellIn < 0) {
            item.quality = 0;
        }
    }

    private void increaseQuality() {
        if (item.quality < Executor.MAX_ITEM_QUALITY) {
            item.quality++;
        }
    }

    private void decreaseSellIn() {
        item.sellIn--;
    }
}
