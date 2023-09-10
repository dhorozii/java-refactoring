package com.gildedrose.util;

import com.gildedrose.ItemType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTypeAnalyzerTest {

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
        ItemType itemType = ItemTypeAnalyzer.analyze("+5 Dexterity Vest");
        assertEquals(ItemType.USUAL, itemType);
    }

    @Test
    void analyze_sulfuras_item() {
        ItemType itemType = ItemTypeAnalyzer.analyze("Sulfuras, Hand of Ragnaros");
        assertEquals(ItemType.SULFURAS, itemType);
    }

    @Test
    void analyze_sulfuras_item_with_usual_keyword() {
        ItemType itemType = ItemTypeAnalyzer.analyze("usual Sulfuras, Hand of Ragnaros");
        assertEquals(ItemType.SULFURAS, itemType);
    }

    @Test
    void analyze_backstage_item() {
        ItemType itemType = ItemTypeAnalyzer.analyze("Backstage passes to a TAFKAL80ETC concert");
        assertEquals(ItemType.BACKSTAGE_PASS, itemType);
    }

    @Test
    void analyze_aged_item() {
        ItemType itemType = ItemTypeAnalyzer.analyze("Aged Brie");
        assertEquals(ItemType.AGED, itemType);
    }
}