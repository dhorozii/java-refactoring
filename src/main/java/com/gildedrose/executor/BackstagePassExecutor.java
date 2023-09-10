package com.gildedrose.executor;

import com.gildedrose.Item;

public class BackstagePassExecutor implements Executor {
    private final Item item;

    public BackstagePassExecutor(Item item) {
        this.item = item;
    }

    @Override
    public void execute() {
    }
}
