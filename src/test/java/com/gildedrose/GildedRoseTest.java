package com.gildedrose;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    private final static int DAYS_IN_WEEK = 7;

    @Test
    public void updateQuality_quality_and_sellIn_decrease() {
        // Given 'standard' products
        Item[] items = new Item[]{
                new Item("+5 Dexterity Vest", 10, 20),
                new Item("Elixir of the Mongoose", 5, 7)
        };
        GildedRose app = new GildedRose(items);

        // When the day is gone
        app.updateQuality();

        // Then
        assertEquals(9, items[0].sellIn);
        assertEquals(19, items[0].quality);

        assertEquals(4, items[1].sellIn);
        assertEquals(6, items[1].quality);
    }

    @Test
    @DisplayName("Once the sell by date has passed, Quality degrades twice as fast")
    public void updateQuality_out_in_sellIn() {
        // Given 'standard' products
        Item[] items = new Item[]{
                new Item("+5 Dexterity Vest", 2, 20),
                new Item("Elixir of the Mongoose", 1, 23)
        };
        GildedRose app = new GildedRose(items);

        // When the period is 7 days
        for (int i = 0; i < DAYS_IN_WEEK; i++) {
            app.updateQuality();
        }

        // Then
        // During 2 days the 'standard product' should be decreased for 1 quality/day
        // starting from the 3rd day we do double decreased/day (5*2) = 10
        // 20-18-(5*2)=8
        assertEquals(-5, items[0].sellIn);
        assertEquals(8, items[0].quality);

        assertEquals(-6, items[1].sellIn);
        assertEquals(10, items[1].quality);
    }

    @Test
    @DisplayName("The Quality of an item is never negative")
    public void updateQuality_quality_never_negative() {
        // Given
        Item[] items = new Item[]{
                new Item("+5 Dexterity Vest", 10, 2),
                new Item("Elixir of the Mongoose", 10, 3)
        };
        GildedRose app = new GildedRose(items);

        // When
        for (int i = 0; i < DAYS_IN_WEEK; i++) {
            app.updateQuality();
        }

        // Then
        assertEquals(3, items[0].sellIn);
        assertEquals(0, items[0].quality);

        assertEquals(3, items[1].sellIn);
        assertEquals(0, items[1].quality);
    }

    @Test
    @DisplayName("'Aged Brie' actually increases in Quality the older it gets")
    public void updateQuality_aged_brie_day_change() {
        // Given
        Item[] items = new Item[]{
                new Item("Aged Brie", 2, 0)
        };
        GildedRose app = new GildedRose(items);

        // When
        app.updateQuality();

        // Then
        assertEquals(1, items[0].sellIn);
        assertEquals(1, items[0].quality);
    }

    @Test
    public void updateQuality_aged_brie_week_period_sellIn_month() {
        // Given
        Item[] items = new Item[]{
                new Item("Aged Brie", 30, 0)
        };
        GildedRose app = new GildedRose(items);

        // When
        for (int i = 0; i < DAYS_IN_WEEK; i++) {
            app.updateQuality();
        }

        // Then
        assertEquals(7, items[0].quality);
        assertEquals(23, items[0].sellIn);
    }

    @Test
    public void updateQuality_aged_brie_sellIn_double_quality() {
        // Given
        Item[] items = new Item[]{
                new Item("Aged Brie", -1, 0)
        };
        GildedRose app = new GildedRose(items);

        // When
        for (int i = 0; i < DAYS_IN_WEEK; i++) {
            app.updateQuality();
        }

        // Then
        assertEquals(2, items[0].quality);
        assertEquals(-2, items[0].sellIn);
    }

    @Test
    @DisplayName("The Quality of an item is never more than 50")
    public void updateQuality_aged_brie_and_passes_should_reach_quality_limit() {
        // Given
        Item[] items = new Item[]{
                new Item("Aged Brie", 30, 48),
                new Item("Backstage passes to a TAFKAL80ETC concert", 15, 48)
        };
        GildedRose app = new GildedRose(items);

        // When
        for (int i = 0; i < DAYS_IN_WEEK; i++) {
            app.updateQuality();
        }

        // Then
        //The Quality of an item is never more than 50
        assertEquals(50, items[0].quality);
        assertEquals(50, items[1].quality);
    }

    @Test
    @DisplayName("'Sulfuras', being a legendary item, never has to be sold or decreases in Quality")
    public void updateQuality_sulfuras_quality_and_sellIn_do_not_change() {
        // Given
        Item[] items = new Item[]{
                new Item("Sulfuras, Hand of Ragnaros", 0, 80)
        };
        GildedRose app = new GildedRose(items);

        // When
        for (int i = 0; i < DAYS_IN_WEEK; i++) {
            app.updateQuality();
        }

        // Then
        assertEquals(0, items[0].sellIn);
        assertEquals(80, items[0].quality);
    }

    @Test
    @DisplayName("Backstage passes: Quality increases by 2 when there are 10 days or less")
    public void updateQuality_passes_3_days_period_sellIn_in_10_days() {
        // Given
        Item[] items = new Item[]{
                new Item("Backstage passes to a TAFKAL80ETC concert", 10, 0)
        };
        GildedRose app = new GildedRose(items);

        // When
        for (int i = 0; i < 3; i++) {
            app.updateQuality();
        }

        // Then
        assertEquals(6, items[0].quality);
    }

    @Test
    @DisplayName("Backstage passes: Quality increases by 3 when there are 5 days or less")
    public void updateQuality_sulfuras_3_dayes_period_sellIn_in_5_days() {
        // Given
        Item[] items = new Item[]{
                new Item("Backstage passes to a TAFKAL80ETC concert", 5, 0)
        };
        GildedRose app = new GildedRose(items);

        // When
        for (int i = 0; i < 3; i++) {
            app.updateQuality();
        }

        // Then
        assertEquals(9, items[0].quality);
    }

    @Test
    @DisplayName("Backstage passes mix quality")
    public void updateQuality_passes_3_dayes_period_sellIn_in_mixed_days() {
        // Given
        Item[] items = new Item[]{
                new Item("Backstage passes to a TAFKAL80ETC concert", 16, 0)
        };
        GildedRose app = new GildedRose(items);

        // When
        for (int i = 0; i < 16; i++) {
            app.updateQuality();
        }

        /* Then
        "Backstage passes", increases in Quality as its SellIn value approaches;
       	Quality increases by 2 when there are 10 days or less and by 3 when there are 5 days or less but
        Quality drops to 0 after the concert

        Initial SellIn = 16
        We have 3 periods
        SellIn > 10 = 1 quality
        SellIn > 5 && SellIn<= 10 = 2 quality
        SellIn <= 5 = 3 qualities

         Calculus looks like this:
        1. 6*1 = 6....
        2. 5*2 = 10
        3. 5*3 = 15
        4. 6+10+15=31
        */
        assertEquals(31, items[0].quality);
    }

    @Test
    @DisplayName("Backstage passes: Quality drops to 0 after the concert")
    public void updateQuality_passes_out_of_sellin() {
        // Given
        Item[] items = new Item[]{
                new Item("Backstage passes to a TAFKAL80ETC concert", 2, 0)
        };
        GildedRose app = new GildedRose(items);

        // When 2 days left
        app.updateQuality();

        // Then
        assertEquals(3, items[0].quality);
        assertEquals(1, items[0].sellIn);

        // When 1 day left
        app.updateQuality();

        // Then
        assertEquals(6, items[0].quality);
        assertEquals(0, items[0].sellIn);

        // When after the concert
        app.updateQuality();

        // Then quality drops to 0
        assertEquals(0, items[0].quality);
        assertEquals(-1, items[0].sellIn);
    }

}
