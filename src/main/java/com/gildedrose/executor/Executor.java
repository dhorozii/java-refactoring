package com.gildedrose.executor;

import com.gildedrose.Item;

public interface Executor {
    int MAX_ITEM_QUALITY = 50;

    void execute(Item item);
}
