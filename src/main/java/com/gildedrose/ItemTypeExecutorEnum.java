package com.gildedrose;

import com.gildedrose.executor.*;

public enum ItemTypeExecutorEnum {

    USUAL("usual", new UsualProductExecutor()),
    AGED("aged", new AgedExecutor()),
    SULFURAS("sulfuras", new SulfurasExecutor()),
    BACKSTAGE_PASS("backstage", new BackstagePassExecutor()),
    CONJURED("conjured", new ConjuredExecutor());
    private final String value;
    private final Executor executor;

    ItemTypeExecutorEnum(String value, Executor executor) {
        this.value = value;
        this.executor = executor;
    }

    public String getValue() {
        return value;
    }

    public Executor getExecutor() {
        return executor;
    }
}
