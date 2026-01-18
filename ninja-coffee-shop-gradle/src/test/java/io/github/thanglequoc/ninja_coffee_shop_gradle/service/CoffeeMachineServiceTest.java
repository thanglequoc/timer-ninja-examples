package io.github.thanglequoc.ninja_coffee_shop_gradle.service;

import io.github.thanglequoc.ninja_coffee_shop_gradle.dto.MaterialStatus;
import io.github.thanglequoc.ninja_coffee_shop_gradle.dto.OrderRequest;
import io.github.thanglequoc.ninja_coffee_shop_gradle.dto.RefillMaterialRequest;
import io.github.thanglequoc.ninja_coffee_shop_gradle.model.beverage.Beverage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoffeeMachineServiceTest {
    private CoffeeMachineService coffeeMachineService;

    @BeforeEach
    void setUp() {
        coffeeMachineService = new CoffeeMachineService();
    }

    /**
     * Test that valid drink type IDs return a Beverage instance.
     */
    @Test
    void testGetDrinkTypeValid() {
        assertTrue(coffeeMachineService.getDrinkType(1) instanceof Beverage);
        assertTrue(coffeeMachineService.getDrinkType(2) instanceof Beverage);
        assertTrue(coffeeMachineService.getDrinkType(3) instanceof Beverage);
    }

    /**
     * Test that an invalid drink type ID throws IllegalArgumentException.
     */
    @Test
    void testGetDrinkTypeInvalid() {
        assertThrows(IllegalArgumentException.class, () -> coffeeMachineService.getDrinkType(99));
    }

    /**
     * Test making both hot and cold drinks updates the beverage properties correctly.
     */
    @Test
    void testMakeDrinkHotAndCold() {
        OrderRequest hotOrder = new OrderRequest();
        hotOrder.setItemId(1);
        hotOrder.setHot(true);
        hotOrder.setSize("M");
        hotOrder.setSweetness(2);
        Beverage hotBeverage = coffeeMachineService.makeDrink(hotOrder);
        assertTrue(hotBeverage.isHot());

        OrderRequest coldOrder = new OrderRequest();
        coldOrder.setItemId(2);
        coldOrder.setHot(false);
        coldOrder.setSize("L");
        coldOrder.setSweetness(1);
        Beverage coldBeverage = coffeeMachineService.makeDrink(coldOrder);
        assertFalse(coldBeverage.isHot());
    }

    /**
     * Test that making a drink with no cups left throws IllegalStateException.
     */
    @Test
    void testMakeDrinkNoCups() {
        // Deplete cups
        for (int i = 0; i < 10; i++) {
            OrderRequest order = new OrderRequest();
            order.setItemId(1);
            order.setHot(true);
            order.setSize("S");
            order.setSweetness(0);
            coffeeMachineService.makeDrink(order);
        }
        OrderRequest order = new OrderRequest();
        order.setItemId(1);
        order.setHot(true);
        order.setSize("S");
        order.setSweetness(0);
        assertThrows(IllegalStateException.class, () -> coffeeMachineService.makeDrink(order));
    }

    /**
     * Test that grinding beans with no beans left throws IllegalStateException.
     */
    @Test
    void testGrindBeansNoBeans() {
        // Deplete beans
        for (int i = 0; i < 30; i++) {
            coffeeMachineService.grindBeans();
        }
        assertThrows(IllegalStateException.class, () -> coffeeMachineService.grindBeans());
    }

    /**
     * Test that heating water with no water left throws IllegalStateException.
     */
    @Test
    void testHeatingWaterNoWater() {
        // Deplete water
        for (int i = 0; i < 30; i++) {
            coffeeMachineService.heatingWater();
        }
        assertThrows(IllegalStateException.class, () -> coffeeMachineService.heatingWater());
    }

    /**
     * Test that making ice with no water left throws IllegalStateException.
     */
    @Test
    void testMakeIceNoWater() {
        // Deplete water
        for (int i = 0; i < 30; i++) {
            coffeeMachineService.makeIce();
        }
        assertThrows(IllegalStateException.class, () -> coffeeMachineService.makeIce());
    }

    /**
     * Test consuming ice servings, including not enough ice scenario.
     */
    @Test
    void testConsumeIceServings() {
        coffeeMachineService.makeIce();
        coffeeMachineService.consumeIceServings(2);
        // Should not throw for valid amount, but should throw for too much
        assertThrows(IllegalStateException.class, () -> coffeeMachineService.consumeIceServings(100));
    }

    /**
     * Test consuming hot water servings, including not enough hot water scenario.
     */
    @Test
    void testConsumeHotWaterServings() {
        coffeeMachineService.heatingWater();
        coffeeMachineService.consumeHotWaterServings(2);
        // Should not throw for valid amount, but should throw for too much
        assertThrows(IllegalStateException.class, () -> coffeeMachineService.consumeHotWaterServings(100));
    }

    /**
     * Test that getMaterialStatus returns a non-null MaterialStatus object.
     */
    @Test
    void testGetMaterialStatus() {
        MaterialStatus status = coffeeMachineService.getMaterialStatus();
        assertNotNull(status);
    }

    /**
     * Test that refillMaterial increases the material counts as expected.
     */
    @Test
    void testRefillMaterial() {
        RefillMaterialRequest refill = new RefillMaterialRequest();
        refill.setCoffeeBeans(5);
        refill.setServingCups(5);
        refill.setWaterServings(5);
        MaterialStatus status = coffeeMachineService.refillMaterial(refill);
        assertTrue(status.getCoffeeBeans() >= 5);
        assertTrue(status.getServingCups() >= 5);
        assertTrue(status.getWaterServings() >= 5);
    }
    
    /**
     * Test that passing null to makeDrink throws NullPointerException.
     */
    @Test
    void testMakeDrinkWithNullOrderThrows() {
        assertThrows(NullPointerException.class, () -> coffeeMachineService.makeDrink(null));
    }
}
