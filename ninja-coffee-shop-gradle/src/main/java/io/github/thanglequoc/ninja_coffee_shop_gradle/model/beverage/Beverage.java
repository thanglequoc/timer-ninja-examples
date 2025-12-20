package io.github.thanglequoc.ninja_coffee_shop_gradle.model.beverage;

import io.github.thanglequoc.ninja_coffee_shop_gradle.model.coffeebean.CoffeeBean;

public abstract class Beverage {
    private String name;
    private int price;
    private int sweetness;
    private CoffeeBean beanType;
    private boolean isHot;
    private String size;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSweetness() {
        return sweetness;
    }

    public void setSweetness(int sweetness) {
        this.sweetness = sweetness;
    }

    public CoffeeBean getBeanType() {
        return beanType;
    }

    public void setBeanType(CoffeeBean beanType) {
        this.beanType = beanType;
    }

    public boolean isHot() {
        return isHot;
    }

    public void setHot(boolean hot) {
        isHot = hot;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
