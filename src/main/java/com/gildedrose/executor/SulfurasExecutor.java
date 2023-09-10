package com.gildedrose.executor;

import com.gildedrose.Item;

public class SulfurasExecutor implements Executor {
    private final Item item;

    public SulfurasExecutor(Item item) {
        this.item = item;
    }

    @Override
    public void execute() {
        // Sulfuras items do not change in quality or sellIn, so this method remains empty.
    }
}
