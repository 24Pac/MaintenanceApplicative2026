package com.gildedrose;

class AgedBrieUpdater implements ItemUpdater {

    @Override
    public void update(Item item) {
        item.sellIn--;

        if (item.quality < 50) {
            item.quality++;
        }

        if (item.sellIn < 0 && item.quality < 50) {
            item.quality++;
        }
    }
}
