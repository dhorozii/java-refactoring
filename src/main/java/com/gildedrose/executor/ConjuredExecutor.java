package com.gildedrose.executor;

import com.gildedrose.Item;

public class ConjuredExecutor implements Executor {
    @Override
    public void execute(Item item) {
        decreaseQuality(item);
        decreaseSellIn(item);

        if (item.sellIn < 0) {
            decreaseQuality(item);
        }
    }

    private void decreaseQuality(Item item) {
        if (item.quality > 0) {
            item.quality -= 2;
        }
    }

    private void decreaseSellIn(Item item) {
        item.sellIn--;
    }
}
