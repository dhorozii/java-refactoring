package com.gildedrose.executor;

import com.gildedrose.Item;

public class AgedExecutor implements Executor {
    private final Item item;

    public AgedExecutor(Item item) {
        this.item = item;
    }

    @Override
    public void execute() {
    }
}