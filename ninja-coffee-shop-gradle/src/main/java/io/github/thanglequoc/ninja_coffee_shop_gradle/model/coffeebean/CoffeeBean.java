package io.github.thanglequoc.ninja_coffee_shop_gradle.model.coffeebean;

public abstract class CoffeeBean {
    private final String name;
    private final String flavor;
    private final int caffeineLevel;

    public CoffeeBean(String name, String flavor, int caffeineLevel) {
        this.name = name;
        this.flavor = flavor;
        this.caffeineLevel = caffeineLevel;
    }

    public String getName() {
        return name;
    }

    public String getFlavor() {
        return flavor;
    }

    public int getCaffeineLevel() {
        return caffeineLevel;
    }
}
