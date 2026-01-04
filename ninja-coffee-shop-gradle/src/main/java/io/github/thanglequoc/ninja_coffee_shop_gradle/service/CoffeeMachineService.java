package io.github.thanglequoc.ninja_coffee_shop_gradle.service;

import io.github.thanglequoc.ninja_coffee_shop_gradle.dto.MaterialStatus;
import io.github.thanglequoc.ninja_coffee_shop_gradle.dto.OrderRequest;
import io.github.thanglequoc.ninja_coffee_shop_gradle.dto.RefillMaterialRequest;
import io.github.thanglequoc.ninja_coffee_shop_gradle.model.beverage.Americano;
import io.github.thanglequoc.ninja_coffee_shop_gradle.model.beverage.Beverage;
import io.github.thanglequoc.ninja_coffee_shop_gradle.model.beverage.Cappuccino;
import io.github.thanglequoc.ninja_coffee_shop_gradle.model.beverage.Latte;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CoffeeMachineService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoffeeMachineService.class);
    private int servingCup = 10;
    private int coffeeBeans = 30; // number of whole beans packs/units
    private int coffeePowderServing = 0; // number of powder servings available
    private int iceServing = 0;
    private int hotWaterServing = 0;
    private int waterServing = 30;

    public Beverage getDrinkType(int id) {
        return switch (id) {
            case 1 -> new Americano();
            case 2 -> new Cappuccino();
            case 3 -> new Latte();
            default -> throw new IllegalArgumentException("Unknown type of option");
        };
    }

    // Overload: build a beverage using full OrderRequest details
    public Beverage makeDrink(OrderRequest order) {
        // Validate basic supplies
        if (servingCup <= 0) {
            LOGGER.error("No serving cups available");
            throw new IllegalStateException("No serving cups available");
        }

        Beverage beverage = getDrinkType(order.getItemId());
        beverage.setHot(order.isHot());
        beverage.setSize(order.getSize());
        beverage.setSweetness(order.getSweetness());

        // Deduct a cup
        servingCup -= 1;

        // Manage hot water / ice servings
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

        // Ensure we have coffee powder; grind if necessary
        if (coffeePowderServing <= 0) {
            if (coffeeBeans > 0) {
                // attempt to grind using requested bean type (fallback to arabica)
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

        // Use one powder serving for this beverage
        coffeePowderServing -= 1;

        LOGGER.info("Prepared beverage: {} (size={}, hot={}, sweetness={}). Remaining - cups: {}, powder: {}, beans: {}, ice: {}, hotWater: {}",
                beverage.getName(), beverage.getSize(), beverage.isHot(), beverage.getSweetness(), servingCup, coffeePowderServing, coffeeBeans, iceServing, hotWaterServing);

        return beverage;
    }

    public void grindBeans() {
        if (coffeeBeans <= 0) {
            LOGGER.error("Unable to grind any beans - no coffee beans available");
            throw new IllegalStateException("Unable to grind any beans, no beans in the machine");
        }

        LOGGER.info("Grinding some bean to make coffee powders...");
        // Simulate grinding time
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }

        // Update internal state: consume one bean unit to produce powder servings
        coffeeBeans -= 1;
        // assume each bean unit produces 2 powder servings
        coffeePowderServing += 2;
        LOGGER.info("Grinding complete. Beans left: {}, Powder servings: {}", coffeeBeans, coffeePowderServing);
    }

    public void heatingWater() {
        if (waterServing <= 0) {
            throw new IllegalStateException("No water available");
        }

        LOGGER.info("Heating water...");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
        hotWaterServing += 5;
        waterServing -= 1;
        LOGGER.info("Boiling water complete. Hot water servings: {}", hotWaterServing);
    }

    public void makeIce() {
        if (waterServing <= 0) {
            throw new IllegalStateException("No water available");
        }

        LOGGER.info("Making some ice...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
        iceServing += 5;
        waterServing -= 1;
        LOGGER.info("Making ice complete. Ice Serving: {}", iceServing);
    }

    // Explicit consumers for flexibility / tests
    public void consumeIceServings(int amount) {
        if (amount <= 0) return;
        if (iceServing < amount) {
            LOGGER.error("Not enough ice servings: requested {}, available {}", amount, iceServing);
            throw new IllegalStateException("Not enough ice servings available");
        }
        iceServing -= amount;
        LOGGER.info("Consumed {} ice servings. Remaining ice servings: {}", amount, iceServing);
    }

    public void consumeHotWaterServings(int amount) {
        if (amount <= 0) return;
        if (hotWaterServing < amount) {
            LOGGER.error("Not enough hot water servings: requested {}, available {}", amount, hotWaterServing);
            throw new IllegalStateException("Not enough hot water servings available");
        }
        hotWaterServing -= amount;
        LOGGER.info("Consumed {} hot water servings. Remaining hot water servings: {}", amount, hotWaterServing);
    }

    public MaterialStatus getMaterialStatus() {
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }

        MaterialStatus status = new MaterialStatus();
        status.setCoffeeBeans(coffeeBeans);
        status.setServingCups(servingCup);
        status.setIceServings(iceServing);
        status.setHotWaterServings(hotWaterServing);
        status.setWaterServings(waterServing);
        return status;
    }

    public MaterialStatus refillMaterial(RefillMaterialRequest refillMaterialRequest) {
        coffeeBeans += refillMaterialRequest.getCoffeeBeans();
        servingCup += refillMaterialRequest.getServingCups();
        waterServing += refillMaterialRequest.getWaterServings();
        LOGGER.info("Refill material completed");
        return getMaterialStatus();
    }

}
