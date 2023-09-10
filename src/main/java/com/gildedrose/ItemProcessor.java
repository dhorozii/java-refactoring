package com.gildedrose;

import com.gildedrose.executor.Executor;
import com.gildedrose.util.ItemTypeAnalyzer;

public class ItemProcessor {

    private final Executor executor;
    private final Item item;

    public ItemProcessor(Item item) {
        this.item = item;
        this.executor = ItemTypeAnalyzer.analyze(item.name).getExecutor();
    }

    public void execute() {
        executor.execute(item);
    }
}
