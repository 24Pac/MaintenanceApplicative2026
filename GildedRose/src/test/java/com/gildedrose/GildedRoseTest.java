package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    // ─── Helper ───────────────────────────────────────────────

    private Item updateItem(String name, int sellIn, int quality) {
        Item[] items = new Item[] { new Item(name, sellIn, quality) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        return app.items[0];
    }

    // ─── Item ─────────────────────────────────────────────────

    @Test
    void itemToString() {
        Item item = new Item("foo", 5, 10);
        assertEquals("foo, 5, 10", item.toString());
    }

    // ─── Normal items ─────────────────────────────────────────

    @Test
    void normalItem_qualityDecreasesByOne() {
        Item item = updateItem("Normal Item", 10, 20);
        assertEquals(19, item.quality);
        assertEquals(9, item.sellIn);
    }

    @Test
    void normalItem_qualityNeverNegative() {
        Item item = updateItem("Normal Item", 10, 0);
        assertEquals(0, item.quality);
    }

    @Test
    void normalItem_pastSellIn_qualityDecreasesTwice() {
        Item item = updateItem("Normal Item", 0, 10);
        assertEquals(8, item.quality);
        assertEquals(-1, item.sellIn);
    }

    @Test
    void normalItem_pastSellIn_qualityNeverNegative() {
        Item item = updateItem("Normal Item", 0, 0);
        assertEquals(0, item.quality);
    }

    // ─── Aged Brie ────────────────────────────────────────────

    @Test
    void agedBrie_qualityIncreasesByOne() {
        Item item = updateItem("Aged Brie", 10, 20);
        assertEquals(21, item.quality);
        assertEquals(9, item.sellIn);
    }

    @Test
    void agedBrie_qualityMaxAt50() {
        Item item = updateItem("Aged Brie", 10, 50);
        assertEquals(50, item.quality);
    }

    @Test
    void agedBrie_pastSellIn_qualityIncreasesByTwo() {
        Item item = updateItem("Aged Brie", 0, 20);
        assertEquals(22, item.quality);
    }

    @Test
    void agedBrie_pastSellIn_qualityMaxAt50() {
        Item item = updateItem("Aged Brie", 0, 50);
        assertEquals(50, item.quality);
    }

    @Test
    void agedBrie_pastSellIn_qualityCapsAt50WhenAt49() {
        Item item = updateItem("Aged Brie", 0, 49);
        assertEquals(50, item.quality);
    }

    // ─── Sulfuras ─────────────────────────────────────────────

    @Test
    void sulfuras_neverChanges() {
        Item item = updateItem("Sulfuras, Hand of Ragnaros", 10, 80);
        assertEquals(80, item.quality);
        assertEquals(10, item.sellIn);
    }

    @Test
    void sulfuras_pastSellIn_neverChanges() {
        Item item = updateItem("Sulfuras, Hand of Ragnaros", -1, 80);
        assertEquals(80, item.quality);
        assertEquals(-1, item.sellIn);
    }

    // ─── Backstage passes ─────────────────────────────────────

    @Test
    void backstage_moreThan10Days_qualityIncreasesByOne() {
        Item item = updateItem("Backstage passes to a TAFKAL80ETC concert", 15, 20);
        assertEquals(21, item.quality);
        assertEquals(14, item.sellIn);
    }

    @Test
    void backstage_exactly11Days_qualityIncreasesByOne() {
        Item item = updateItem("Backstage passes to a TAFKAL80ETC concert", 11, 20);
        assertEquals(21, item.quality);
    }

    @Test
    void backstage_10DaysOrLess_qualityIncreasesByTwo() {
        Item item = updateItem("Backstage passes to a TAFKAL80ETC concert", 10, 20);
        assertEquals(22, item.quality);
    }

    @Test
    void backstage_5DaysOrLess_qualityIncreasesByThree() {
        Item item = updateItem("Backstage passes to a TAFKAL80ETC concert", 5, 20);
        assertEquals(23, item.quality);
    }

    @Test
    void backstage_1Day_qualityIncreasesByThree() {
        Item item = updateItem("Backstage passes to a TAFKAL80ETC concert", 1, 20);
        assertEquals(23, item.quality);
    }

    @Test
    void backstage_afterConcert_qualityDropsToZero() {
        Item item = updateItem("Backstage passes to a TAFKAL80ETC concert", 0, 20);
        assertEquals(0, item.quality);
    }

    @Test
    void backstage_qualityMaxAt50_moreThan10Days() {
        Item item = updateItem("Backstage passes to a TAFKAL80ETC concert", 15, 50);
        assertEquals(50, item.quality);
    }

    @Test
    void backstage_qualityMaxAt50_10DaysOrLess() {
        Item item = updateItem("Backstage passes to a TAFKAL80ETC concert", 10, 49);
        assertEquals(50, item.quality);
    }

    @Test
    void backstage_qualityMaxAt50_5DaysOrLess() {
        Item item = updateItem("Backstage passes to a TAFKAL80ETC concert", 5, 49);
        assertEquals(50, item.quality);
    }

    @Test
    void backstage_qualityAt50_5DaysOrLess() {
        Item item = updateItem("Backstage passes to a TAFKAL80ETC concert", 5, 50);
        assertEquals(50, item.quality);
    }

    @Test
    void backstage_qualityAt48_5DaysOrLess() {
        Item item = updateItem("Backstage passes to a TAFKAL80ETC concert", 5, 48);
        assertEquals(50, item.quality);
    }

    // ─── Multiple items ───────────────────────────────────────

    @Test
    void multipleItems_allUpdated() {
        Item[] items = new Item[] {
                new Item("Normal Item", 5, 10),
                new Item("Aged Brie", 5, 10)
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(9, items[0].quality);
        assertEquals(11, items[1].quality);
    }

    // ─── Edge: empty array ────────────────────────────────────

    @Test
    void emptyItems_noError() {
        Item[] items = new Item[] {};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
    }
}
