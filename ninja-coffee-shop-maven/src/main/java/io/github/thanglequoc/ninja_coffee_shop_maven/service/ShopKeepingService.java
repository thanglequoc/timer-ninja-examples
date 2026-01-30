package io.github.thanglequoc.ninja_coffee_shop_maven.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.thanglequoc.timerninja.TimerNinjaTracker;

public class ShopKeepingService {

    // General CoffeeShop Keeping service

    private Logger LOGGER = LoggerFactory.getLogger(ShopKeepingService.class);

    @TimerNinjaTracker
    public void cleanTheShop() {
        mopTheFloor();
        takeOutTheTrash();
    }

    @TimerNinjaTracker
    private void mopTheFloor() {
        try {
            // Simulate cleaning process
            Thread.sleep(500);
            LOGGER.info("The shop has been mopped");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @TimerNinjaTracker
    private boolean takeOutTheTrash() {
        try {
            // Simulate trash taking process
            Thread.sleep(200);
            LOGGER.info("The trash has been taken out");
            return true;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }
}
