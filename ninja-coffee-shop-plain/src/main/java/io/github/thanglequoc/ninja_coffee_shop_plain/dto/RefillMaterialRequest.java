package io.github.thanglequoc.ninja_coffee_shop_plain.dto;

public class RefillMaterialRequest {
    private int coffeeBeans;
    private int servingCups;
    private int waterServings;

    public int getCoffeeBeans() {
        return coffeeBeans;
    }

    public void setCoffeeBeans(int coffeeBeans) {
        this.coffeeBeans = coffeeBeans;
    }

    public int getServingCups() {
        return servingCups;
    }

    public void setServingCups(int servingCups) {
        this.servingCups = servingCups;
    }

    public void setWaterServings(int waterServings) {
        this.waterServings = waterServings;
    }

    public int getWaterServings() {
        return waterServings;
    }
}
