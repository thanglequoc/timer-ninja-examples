package io.github.thanglequoc.ninja_coffee_shop_gradle.service;

import io.github.thanglequoc.ninja_coffee_shop_gradle.model.beverage.Americano;
import io.github.thanglequoc.ninja_coffee_shop_gradle.model.beverage.Beverage;
import io.github.thanglequoc.ninja_coffee_shop_gradle.model.beverage.Cappuccino;
import io.github.thanglequoc.ninja_coffee_shop_gradle.model.beverage.Latte;
import org.springframework.stereotype.Service;

@Service
public class CoffeeMachineService {

    public Beverage makeDrink(int id) {
        return switch (id) {
            case 1 -> new Americano();
            case 2 -> new Cappuccino();
            case 3 -> new Latte();
            default -> throw new IllegalArgumentException("Unknown type of option");
        };
    }

    public void grindBeans(String beanType) {
        System.out.println("Grinding some bean");

    }

    public void heatingWater() {

    }
}
