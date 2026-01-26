package io.github.thanglequoc.ninja_coffee_shop_maven.service;

import io.github.thanglequoc.ninja_coffee_shop_maven.dto.MaterialStatus;
import io.github.thanglequoc.ninja_coffee_shop_maven.dto.OrderRequest;
import io.github.thanglequoc.ninja_coffee_shop_maven.dto.RefillMaterialRequest;
import io.github.thanglequoc.ninja_coffee_shop_maven.model.beverage.Beverage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoffeeMachineServiceTest {
    private CoffeeMachineService coffeeMachineService;

    @BeforeEach
    void setUp() {
        coffeeMachineService = new CoffeeMachineService();
    }

    @Test
    void testGetDrinkTypeValid() {
        assertTrue(coffeeMachineService.getDrinkType(1) instanceof Beverage);
        assertTrue(coffeeMachineService.getDrinkType(2) instanceof Beverage);
        assertTrue(coffeeMachineService.getDrinkType(3) instanceof Beverage);
    }

    @Test
    void testGetDrinkTypeInvalid() {
        assertThrows(IllegalArgumentException.class, () -> coffeeMachineService.getDrinkType(99));
    }

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

    @Test
    void testMakeDrinkNoCups() {
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

    @Test
    void testGrindBeansNoBeans() {
        for (int i = 0; i < 30; i++) {
            coffeeMachineService.grindBeans();
        }
        assertThrows(IllegalStateException.class, () -> coffeeMachineService.grindBeans());
    }

    // Additional tests can be added here as needed
}
