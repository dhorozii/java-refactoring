package com.gildedrose.util;

import com.gildedrose.ItemTypeExecutorEnum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTypeExecutorEnumAnalyzerTest {

    @Test
    void analyze_empty_name() {
        try {
            ItemTypeAnalyzer.analyze("");
        } catch (IllegalArgumentException e) {
            assertEquals("Item name should not be blank", e.getMessage());
        }
    }

    @Test
    void analyze_usual_item() {
        ItemTypeExecutorEnum itemTypeExecutorEnum = ItemTypeAnalyzer.analyze("+5 Dexterity Vest");
        assertEquals(ItemTypeExecutorEnum.USUAL, itemTypeExecutorEnum);
    }

    @Test
    void analyze_sulfuras_item() {
        ItemTypeExecutorEnum itemTypeExecutorEnum = ItemTypeAnalyzer.analyze("Sulfuras, Hand of Ragnaros");
        assertEquals(ItemTypeExecutorEnum.SULFURAS, itemTypeExecutorEnum);
    }

    @Test
    void analyze_sulfuras_item_with_usual_keyword() {
        ItemTypeExecutorEnum itemTypeExecutorEnum = ItemTypeAnalyzer.analyze("usual Sulfuras, Hand of Ragnaros");
        assertEquals(ItemTypeExecutorEnum.SULFURAS, itemTypeExecutorEnum);
    }

    @Test
    void analyze_backstage_item() {
        ItemTypeExecutorEnum itemTypeExecutorEnum = ItemTypeAnalyzer.analyze("Backstage passes to a TAFKAL80ETC concert");
        assertEquals(ItemTypeExecutorEnum.BACKSTAGE_PASS, itemTypeExecutorEnum);
    }

    @Test
    void analyze_conjured_item() {
        ItemTypeExecutorEnum itemTypeExecutorEnum = ItemTypeAnalyzer.analyze("conjured item lorem ipsum");
        assertEquals(ItemTypeExecutorEnum.CONJURED, itemTypeExecutorEnum);
    }

    @Test
    void analyze_aged_item() {
        ItemTypeExecutorEnum itemTypeExecutorEnum = ItemTypeAnalyzer.analyze("Aged Brie");
        assertEquals(ItemTypeExecutorEnum.AGED, itemTypeExecutorEnum);
    }
}