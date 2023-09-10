package com.gildedrose.executor;

import com.gildedrose.Item;

public class BackstagePassExecutor implements Executor {
    private final static int SELL_IN_DOUBLE_QUALITY_DAYS = 11;
    private final static int SELL_IN_TRIPLE_QUALITY_DAYS = 6;

    @Override
    public void execute(Item item) {
        increaseQuality(item);

        if (item.sellIn < SELL_IN_DOUBLE_QUALITY_DAYS) {
            increaseQuality(item);
        }

        if (item.sellIn < SELL_IN_TRIPLE_QUALITY_DAYS) {
            increaseQuality(item);
        }

        decreaseSellIn(item);

        if (item.sellIn < 0) {
            item.quality = 0;
        }
    }

    private void increaseQuality(Item item) {
        if (item.quality < Executor.MAX_ITEM_QUALITY) {
            item.quality++;
        }
    }

    private void decreaseSellIn(Item item) {
        item.sellIn--;
    }
}
