package com.gildedrose.executor;

import com.gildedrose.Item;

public class AgedExecutor implements Executor {
    private final Item item;

    public AgedExecutor(Item item) {
        this.item = item;
    }

    @Override
    public void execute() {
        if (item.quality < 50) {
            item.quality = item.quality + 1;
        }
        item.sellIn = item.sellIn - 1;
        if (item.sellIn < 0) {
            if (item.quality < 50) {
                item.quality = item.quality + 1;
            }
        }
    }
}
