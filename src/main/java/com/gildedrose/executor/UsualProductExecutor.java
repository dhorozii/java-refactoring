package com.gildedrose.executor;

import com.gildedrose.Item;

public class UsualProductExecutor implements Executor {
    private final Item item;

    public UsualProductExecutor(Item item) {
        this.item = item;
    }

    @Override
    public void execute() {
        decreaseQuality();
        decreaseSellIn();

        if (item.sellIn < 0) {
            decreaseQuality();
        }
    }

    private void decreaseQuality() {
        if (item.quality > 0) {
            item.quality--;
        }
    }

    private void decreaseSellIn() {
        item.sellIn--;
    }
}
