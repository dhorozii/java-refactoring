package com.gildedrose;

import com.gildedrose.executor.AgedExecutor;
import com.gildedrose.executor.BackstagePassExecutor;
import com.gildedrose.executor.Executor;
import com.gildedrose.executor.SulfurasExecutor;
import com.gildedrose.executor.UsualProductExecutor;
import com.gildedrose.util.ItemTypeAnalyzer;

public class ItemProcessor {

    private final Executor executor;
    private final Item item;

    public ItemProcessor(Item item) {
        this.item = item;
        this.executor = setUpProcessor(ItemTypeAnalyzer.analyze(item.name));
    }

    private Executor setUpProcessor(ItemType itemType) {
        switch (itemType) {
            case AGED:
                return new AgedExecutor(item);
            case SULFURAS:
                return new SulfurasExecutor(item);
            case BACKSTAGE_PASS:
                return new BackstagePassExecutor(item);
            case USUAL:
            default:
                return new UsualProductExecutor(item);
        }
    }

    public Executor getProcessor() {
        return executor;
    }
}
