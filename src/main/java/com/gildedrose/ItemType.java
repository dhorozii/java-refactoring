package com.gildedrose;

public enum ItemType {

    USUAL("usual"),
    AGED("aged"),
    SULFURAS("sulfuras"),
    BACKSTAGE_PASS("backstage");
    private final String value;

    ItemType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}