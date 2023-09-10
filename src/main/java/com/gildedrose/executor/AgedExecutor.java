package com.gildedrose.executor;

import com.gildedrose.Item;

public class AgedExecutor implements Executor {

    @Override
    public void execute(Item item) {
        increaseQuality(item);
        decreaseSellIn(item);
        if (item.sellIn < 0) {
            increaseQuality(item);
        }
    }

    private void increaseQuality(Item item) {
        if (item.quality < MAX_ITEM_QUALITY) {
            item.quality++;
        }
    }

    private void decreaseSellIn(Item item) {
        item.sellIn--;
    }
}
