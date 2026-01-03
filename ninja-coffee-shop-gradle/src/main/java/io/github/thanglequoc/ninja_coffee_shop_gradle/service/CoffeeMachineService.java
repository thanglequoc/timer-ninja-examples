package io.github.thanglequoc.ninja_coffee_shop_gradle.service;

import io.github.thanglequoc.ninja_coffee_shop_gradle.dto.OrderRequest;
import io.github.thanglequoc.ninja_coffee_shop_gradle.model.beverage.Americano;
import io.github.thanglequoc.ninja_coffee_shop_gradle.model.beverage.Beverage;
import io.github.thanglequoc.ninja_coffee_shop_gradle.model.beverage.Cappuccino;
import io.github.thanglequoc.ninja_coffee_shop_gradle.model.beverage.Latte;
import io.github.thanglequoc.ninja_coffee_shop_gradle.model.coffeebean.Arabica;
import io.github.thanglequoc.ninja_coffee_shop_gradle.model.coffeebean.Robusta;
import io.github.thanglequoc.ninja_coffee_shop_gradle.model.coffeebean.CoffeeBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CoffeeMachineService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoffeeMachineService.class);
    private int servingCup = 0;
    private int coffeeBeans = 0; // number of whole beans packs/units
    private int coffeePowderServing = 0; // number of powder servings available
    private int iceServing = 0;
    private int hotWaterServing = 0;

    public Beverage makeDrink(int id) {
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

        Beverage beverage = makeDrink(order.getItemId());
        beverage.setHot(order.isHot());
        beverage.setSize(order.getSize());
        beverage.setSweetness(order.getSweetness());

        // map bean type string to a CoffeeBean instance if provided
        String bean = order.getBeanType();
        if (bean != null && !bean.isBlank()) {
            CoffeeBean coffeeBean = mapBean(bean.trim());
            beverage.setBeanType(coffeeBean);
        }

        // Deduct a cup
        servingCup -= 1;

        // Manage hot water / ice servings
        if (order.isHot()) {
            if (hotWaterServing <= 0) {
                heatingWater();
            }
            consumeHotWaterServings(1);
        } else {
            consumeIceServings(1);
        }

        // Ensure we have coffee powder; grind if necessary
        if (coffeePowderServing <= 0) {
            if (coffeeBeans > 0) {
                // attempt to grind using requested bean type (fallback to arabica)
                try {
                    grindBeans(bean == null || bean.isBlank() ? "arabica" : bean);
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

        LOGGER.info("Prepared beverage: {} (size={}, hot={}, sweetness={}, bean={}). Remaining - cups: {}, powder: {}, beans: {}, ice: {}, hotWater: {}",
                beverage.getName(), beverage.getSize(), beverage.isHot(), beverage.getSweetness(), order.getBeanType(), servingCup, coffeePowderServing, coffeeBeans, iceServing, hotWaterServing);

        return beverage;
    }

    private CoffeeBean mapBean(String beanType) {
        return switch (beanType.toLowerCase()) {
            case "arabica" -> new Arabica();
            case "robusta" -> new Robusta();
            default -> new Arabica(); // default to Arabica if unknown
        };
    }

    public void grindBeans(String beanType) {
        LOGGER.info("Grinding some bean: {}", beanType);
        if (coffeeBeans <= 0) {
            LOGGER.error("Unable to grind any beans - no coffee beans available");
            throw new IllegalStateException("Unable to grind any beans, no beans in the machine");
        }

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
        LOGGER.info("Heating water...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
        hotWaterServing += 5;
        LOGGER.info("Boiling water complete. Hot water servings: {}", hotWaterServing);
    }

    // Helper methods to manage machine supplies
    public void addCoffeeBeans(int amount) {
        if (amount <= 0) return;
        coffeeBeans += amount;
        LOGGER.info("Added {} coffee beans. Total beans: {}", amount, coffeeBeans);
    }

    // Alias / clearer API for refill
    public void refillCoffeeBeans(int amount) {
        addCoffeeBeans(amount);
    }

    public void addServingCups(int amount) {
        if (amount <= 0) return;
        servingCup += amount;
        LOGGER.info("Added {} serving cups. Total cups: {}", amount, servingCup);
    }

    public void addIceServings(int amount) {
        if (amount <= 0) return;
        iceServing += amount;
        LOGGER.info("Added {} ice servings. Total ice servings: {}", amount, iceServing);
    }

    // Alias for refill ice
    public void refillIceServings(int amount) {
        addIceServings(amount);
    }

    public void addHotWaterServings(int amount) {
        if (amount <= 0) return;
        hotWaterServing += amount;
        LOGGER.info("Added {} hot water servings. Total hot water servings: {}", amount, hotWaterServing);
    }

    // Alias for refill hot water
    public void refillHotWaterServings(int amount) {
        addHotWaterServings(amount);
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

    public int getAvailableBeans() {
        return coffeeBeans;
    }

    public int getAvailablePowderServings() {
        return coffeePowderServing;
    }

    public int getAvailableCups() {
        return servingCup;
    }

    public int getAvailableIceServings() {
        return iceServing;
    }

    public int getAvailableHotWaterServings() {
        return hotWaterServing;
    }
}
