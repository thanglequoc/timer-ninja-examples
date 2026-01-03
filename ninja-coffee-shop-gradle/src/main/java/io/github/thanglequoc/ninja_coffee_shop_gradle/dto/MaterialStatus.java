package io.github.thanglequoc.ninja_coffee_shop_gradle.dto;

public class MaterialStatus {
    private int coffeeBeans;
    private int servingCups;
    private int iceServings;
    private int hotWaterServings;
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

    public int getIceServings() {
        return iceServings;
    }

    public void setIceServings(int iceServings) {
        this.iceServings = iceServings;
    }

    public int getHotWaterServings() {
        return hotWaterServings;
    }

    public void setHotWaterServings(int hotWaterServings) {
        this.hotWaterServings = hotWaterServings;
    }

    public int getWaterServings() {
        return waterServings;
    }

    public void setWaterServings(int waterServings) {
        this.waterServings = waterServings;
    }
}
