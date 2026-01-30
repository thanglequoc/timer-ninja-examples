package io.github.thanglequoc.ninja_coffee_shop_maven.service;

import io.github.thanglequoc.ninja_coffee_shop_maven.dto.MaterialStatus;
import io.github.thanglequoc.ninja_coffee_shop_maven.dto.OrderRequest;
import io.github.thanglequoc.ninja_coffee_shop_maven.dto.RefillMaterialRequest;
import io.github.thanglequoc.ninja_coffee_shop_maven.model.beverage.Americano;
import io.github.thanglequoc.ninja_coffee_shop_maven.model.beverage.Beverage;
import io.github.thanglequoc.ninja_coffee_shop_maven.model.beverage.Cappuccino;
import io.github.thanglequoc.ninja_coffee_shop_maven.model.beverage.Latte;
import io.github.thanglequoc.timerninja.TimerNinjaTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CoffeeMachineService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoffeeMachineService.class);
    private int servingCup = 10;
    private int coffeeBeans = 30;
    private int coffeePowderServing = 0;
    private int iceServing = 0;
    private int hotWaterServing = 0;
    private int waterServing = 30;

    @TimerNinjaTracker
    public Beverage getDrinkType(int id) {
        return switch (id) {
            case 1 -> new Americano();
            case 2 -> new Cappuccino();
            case 3 -> new Latte();
            default -> throw new IllegalArgumentException("Unknown type of option");
        };
    }

    @TimerNinjaTracker(includeArgs = true)
    public Beverage makeDrink(OrderRequest order) {
        if (servingCup <= 0) {
            LOGGER.error("No serving cups available");
            throw new IllegalStateException("No serving cups available");
        }

        Beverage beverage = getDrinkType(order.getItemId());
        beverage.setHot(order.isHot());
        beverage.setSize(order.getSize());
        beverage.setSweetness(order.getSweetness());

        servingCup -= 1;

        if (order.isHot()) {
            if (hotWaterServing <= 0) {
                heatingWater();
            }
            consumeHotWaterServings(1);
        } else {
            if (iceServing <= 0) {
                makeIce();
            }
            consumeIceServings(1);
        }

        if (coffeePowderServing <= 0) {
            if (coffeeBeans > 0) {
                try {
                    grindBeans();
                } catch (IllegalStateException e) {
                    LOGGER.error("No beans to grind and no powder available");
                    throw new IllegalStateException("No coffee powder or beans to prepare the drink");
                }
            } else {
                LOGGER.error("No coffee powder and no beans available");
                throw new IllegalStateException("No coffee powder or beans to prepare the drink");
            }
        }

        coffeePowderServing -= 1;

        LOGGER.info("Prepared beverage: {} (size={}, hot={}, sweetness={}). Remaining - cups: {}, powder: {}, beans: {}, ice: {}, hotWater: {}",
                beverage.getName(), beverage.getSize(), beverage.isHot(), beverage.getSweetness(), servingCup, coffeePowderServing, coffeeBeans, iceServing, hotWaterServing);

        return beverage;
    }

    @TimerNinjaTracker
    public void grindBeans() {
        if (coffeeBeans <= 0) {
            LOGGER.error("Unable to grind any beans - no coffee beans available");
            throw new IllegalStateException("Unable to grind any beans, no beans in the machine");
        }

        LOGGER.info("Grinding some bean to make coffee powders...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        coffeeBeans -= 1;
        coffeePowderServing += 5;
    }

    @TimerNinjaTracker
    public void heatingWater() {
        LOGGER.info("Heating water...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        hotWaterServing += 5;
    }

    @TimerNinjaTracker
    public void makeIce() {
        LOGGER.info("Making ice...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        iceServing += 5;
    }

    public void consumeHotWaterServings(int amount) {
        if (hotWaterServing < amount) {
            throw new IllegalStateException("Not enough hot water servings");
        }
        hotWaterServing -= amount;
    }

    public void consumeIceServings(int amount) {
        if (iceServing < amount) {
            throw new IllegalStateException("Not enough ice servings");
        }
        iceServing -= amount;
    }

    public MaterialStatus getMaterialStatus() {
        MaterialStatus status = new MaterialStatus();
        status.setCoffeeBeans(coffeeBeans);
        status.setServingCups(servingCup);
        status.setIceServings(iceServing);
        status.setHotWaterServings(hotWaterServing);
        status.setWaterServings(waterServing);
        return status;
    }

    public MaterialStatus refillMaterial(RefillMaterialRequest refillRequest) {
        this.coffeeBeans += refillRequest.getCoffeeBeans();
        this.servingCup += refillRequest.getServingCups();
        this.waterServing += refillRequest.getWaterServings();
        return getMaterialStatus();
    }
}
