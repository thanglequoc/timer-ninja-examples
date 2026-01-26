package io.github.thanglequoc.ninja_coffee_shop_plain.controller;

import io.github.thanglequoc.ninja_coffee_shop_plain.dto.*;
import io.github.thanglequoc.ninja_coffee_shop_plain.model.beverage.Beverage;
import io.github.thanglequoc.ninja_coffee_shop_plain.service.BaristaService;
import io.github.thanglequoc.ninja_coffee_shop_plain.service.CoffeeMachineService;
import io.github.thanglequoc.timerninja.TimerNinjaTracker;

public class BaristaController {
    private final CoffeeMachineService coffeeMachineService;
    private final BaristaService baristaService;

    public BaristaController(CoffeeMachineService coffeeMachineService, BaristaService baristaService) {
        this.coffeeMachineService = coffeeMachineService;
        this.baristaService = baristaService;
    }

    public OrderRequest placeOrder(OrderRequest orderReq) {
        return baristaService.placeOrder(orderReq);
    }

    public Receipt pay(PaymentRequest paymentRequest) {
        return baristaService.handleUserPayment(paymentRequest);
    }

    @TimerNinjaTracker
    public Beverage makeDrink() {
        OrderRequest currentActiveOrder = baristaService.getActiveOrder();
        return coffeeMachineService.makeDrink(currentActiveOrder);
    }

    public MaterialStatus getMaterialStatus() {
        return coffeeMachineService.getMaterialStatus();
    }

    public MaterialStatus refillMaterials(RefillMaterialRequest refillRequest) {
        return coffeeMachineService.refillMaterial(refillRequest);
    }

    public int checkRevenue() {
        return baristaService.getTotalRevenue();
    }
}
