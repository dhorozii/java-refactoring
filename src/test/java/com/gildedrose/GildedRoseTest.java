package com.gildedrose;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    private final static int DAYS_IN_WEEK = 7;

    @Test
    public void updateQuality_empty_item_array() {
        try {
            new GildedRose(null);
        } catch (IllegalArgumentException e) {
            assertEquals("Items array should not be null or empty", e.getMessage());
        }
    }

    @Test
    public void updateQuality_quality_and_sellIn_decrease() {
        given()
                .addItem(itemBuilder().name("+5 Dexterity Vest").sellIn(10).quality(20).create())
                .addItem(itemBuilder().name("Elixir of the Mongoose").sellIn(5).quality(7).create())
                .whenUpdateQuality()
                .thenAssertItemSellIn(9, 0)
                .thenAssertItemQuality(19, 0)
                .nextAssertion()
                .thenAssertItemSellIn(4, 1)
                .thenAssertItemQuality(6, 1);
    }

    @Test
    @DisplayName("Once the sell by date has passed, Quality degrades twice as fast")
    public void updateQuality_out_in_sellIn() {
        given()
                .addItem(itemBuilder().name("+5 Dexterity Vest").sellIn(2).quality(20).create())
                .addItem(itemBuilder().name("Elixir of the Mongoose").sellIn(1).quality(23).create())
                .whenUpdateQuality(DAYS_IN_WEEK)
                // During 2 days the 'standard product' should be decreased for 1 quality/day
                // starting from the 3rd day we do double decreased/day (5*2) = 10
                // 20-18-(5*2)=8
                .thenAssertItemSellIn(-5, 0)
                .thenAssertItemQuality(8, 0)
                .nextAssertion()
                .thenAssertItemSellIn(-6, 1)
                .thenAssertItemQuality(10, 1);
    }

    @Test
    @DisplayName("The Quality of an item is never negative")
    public void updateQuality_quality_never_negative() {
        given()
                .addItem(itemBuilder().name("+5 Dexterity Vest").sellIn(10).quality(2).create())
                .addItem(itemBuilder().name("Elixir of the Mongoose").sellIn(10).quality(3).create())
                .whenUpdateQuality(DAYS_IN_WEEK)
                .thenAssertItemSellIn(3, 0)
                .thenAssertItemQuality(0, 0)
                .nextAssertion()
                .thenAssertItemSellIn(3, 1)
                .thenAssertItemQuality(0, 1);
    }

    @Test
    @DisplayName("'Aged Brie' actually increases in Quality the older it gets")
    public void updateQuality_aged_brie_day_change() {
        given()
                .addItem(itemBuilder().name("Aged Brie").sellIn(2).quality(0).create())
                .whenUpdateQuality()
                .thenAssertItemSellIn(1, 0)
                .thenAssertItemQuality(1, 0);
    }

    @Test
    public void updateQuality_aged_brie_week_period_sellIn_month() {
        given()
                .addItem(itemBuilder().name("Aged Brie").sellIn(30).quality(0).create())
                .whenUpdateQuality(DAYS_IN_WEEK)
                .thenAssertItemSellIn(23, 0)
                .thenAssertItemQuality(7, 0);
    }

    @Test
    public void updateQuality_aged_brie_sellIn_double_quality() {
        given()
                .addItem(itemBuilder().name("Aged Brie").sellIn(-1).quality(0).create())
                .whenUpdateQuality(DAYS_IN_WEEK)
                .thenAssertItemSellIn(-8, 0)
                .thenAssertItemQuality(14, 0);
    }

    @Test
    @DisplayName("The Quality of an item is never more than 50")
    public void updateQuality_aged_brie_and_passes_should_reach_quality_limit() {
        given()
                .addItem(itemBuilder().name("Aged Brie").sellIn(30).quality(48).create())
                .addItem(itemBuilder().name("Backstage passes to a TAFKAL80ETC concert").sellIn(15).quality(48).create())
                .whenUpdateQuality(DAYS_IN_WEEK)
                .thenAssertItemQuality(50, 0)
                .thenAssertItemQuality(50, 1);
    }

    @Test
    @DisplayName("'Sulfuras', being a legendary item, never has to be sold or decreases in Quality")
    public void updateQuality_sulfuras_quality_and_sellIn_do_not_change() {
        given()
                .addItem(itemBuilder().name("Sulfuras, Hand of Ragnaros").sellIn(0).quality(80).create())
                .whenUpdateQuality(DAYS_IN_WEEK)
                .thenAssertItemSellIn(0, 0)
                .thenAssertItemQuality(80, 0);
    }

    @Test
    @DisplayName("Backstage passes: Quality increases by 2 when there are 10 days or less")
    public void updateQuality_passes_3_days_period_sellIn_in_10_days() {
        given()
                .addItem(itemBuilder().name("Backstage passes to a TAFKAL80ETC concert").sellIn(10).quality(0).create())
                .whenUpdateQuality(3)
                .thenAssertItemQuality(6, 0);
    }

    @Test
    @DisplayName("Backstage passes: Quality increases by 3 when there are 5 days or less")
    public void updateQuality_sulfuras_3_dayes_period_sellIn_in_5_days() {
        given()
                .addItem(itemBuilder().name("Backstage passes to a TAFKAL80ETC concert").sellIn(5).quality(0).create())
                .whenUpdateQuality(3)
                .thenAssertItemQuality(9, 0);
    }

    @Test
    @DisplayName("Backstage passes mix quality")
    public void updateQuality_passes_3_dayes_period_sellIn_in_mixed_days() {
        /*
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
        given()
                .addItem(itemBuilder().name("Backstage passes to a TAFKAL80ETC concert").sellIn(16).quality(0).create())
                .whenUpdateQuality(16)
                .thenAssertItemQuality(31, 0);
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

    @Test
    @DisplayName("'Conjured' items degrade in Quality twice as fast as normal items")
    public void updateQuality_quality_and_sellIn_decrease_conjured() {
        given()
                .addItem(itemBuilder().name("conjured Dexterity Vest").sellIn(10).quality(20).create())
                .addItem(itemBuilder().name("Conjured of the Mongoose").sellIn(0).quality(7).create())
                .whenUpdateQuality()
                .thenAssertItemSellIn(9, 0)
                //20 - 2 (twice than usual) = 18
                .thenAssertItemQuality(18, 0)
                .nextAssertion()
                .thenAssertItemSellIn(-1, 1)
                //7 - 2 (twice than usual) - 2 (additional expired sell in condition) = 3
                .thenAssertItemQuality(3, 1);
    }

    private Given given() {
        return new Given();
    }

    class Given {

        private List<Item> items = new ArrayList<>();

        public Given() {
        }

        Given addItem(Item item) {
            items.add(item);
            return this;
        }

        ResponseAssert whenUpdateQuality() {
            Item[] itemsResult = items.toArray(new Item[0]);
            GildedRose app = new GildedRose(itemsResult);
            app.updateQuality();
            return new ResponseAssert(itemsResult);
        }

        ResponseAssert whenUpdateQuality(int days) {
            Item[] itemsResult = items.toArray(new Item[0]);
            GildedRose app = new GildedRose(itemsResult);
            for (int i = 0; i < days; i++) {
                app.updateQuality();
            }
            return new ResponseAssert(itemsResult);
        }

        private class ResponseAssert {

            Item[] items;

            ResponseAssert(Item[] items) {
                this.items = items;
            }

            ResponseAssert thenAssertItemSellIn(int expected, int index) {
                assertEquals(expected, items[index].sellIn);
                return this;
            }

            ResponseAssert thenAssertItemQuality(int expected, int index) {
                assertEquals(expected, items[index].quality);
                return this;
            }

            ResponseAssert nextAssertion() {
                return this;
            }
        }
    }

    private ItemBuilder itemBuilder() {
        return new ItemBuilder();
    }

    public class ItemBuilder {
        private String name;
        private int sellIn;
        private int quality;

        ItemBuilder name(String name) {
            this.name = name;
            return this;
        }

        ItemBuilder sellIn(int sellIn) {
            this.sellIn = sellIn;
            return this;
        }

        ItemBuilder quality(int quality) {
            this.quality = quality;
            return this;
        }

        Item create() {
            return new Item(name, sellIn, quality);
        }
    }
}
