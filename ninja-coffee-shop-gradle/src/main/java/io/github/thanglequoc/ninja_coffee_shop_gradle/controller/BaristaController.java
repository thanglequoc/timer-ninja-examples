package io.github.thanglequoc.ninja_coffee_shop_gradle.controller;

import io.github.thanglequoc.ninja_coffee_shop_gradle.dto.*;
import io.github.thanglequoc.ninja_coffee_shop_gradle.model.beverage.Beverage;
import io.github.thanglequoc.ninja_coffee_shop_gradle.service.BaristaService;
import io.github.thanglequoc.ninja_coffee_shop_gradle.service.CoffeeMachineService;
import io.github.thanglequoc.ninja_coffee_shop_gradle.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaristaController {
    private final CoffeeMachineService coffeeMachineService;
    private final BaristaService baristaService;

    @Autowired
    public BaristaController(CoffeeMachineService coffeeMachineService, BaristaService baristaService) {
        this.coffeeMachineService = coffeeMachineService;
        this.baristaService = baristaService;
    }

    @PostMapping("/order")
    public OrderRequest placeOrder(@RequestBody OrderRequest orderReq) {
        return baristaService.placeOrder(orderReq);
    }

    @PostMapping("/pay")
    public Receipt pay(@RequestBody PaymentRequest paymentRequest) {
        return baristaService.handleUserPayment(paymentRequest);
    }

    @PostMapping("/brew")
    public Beverage makeDrink(@RequestBody OrderRequest orderReq) {
        return coffeeMachineService.makeDrink(orderReq);
    }

    @GetMapping("/checkMaterial")
    public MaterialStatus getMaterialStatus() {
        return coffeeMachineService.getMaterialStatus();
    }

    @PostMapping("/refillMaterial")
    public MaterialStatus refillMaterials(@RequestBody RefillMaterialRequest refillRequest) {
        return coffeeMachineService.refillMaterial(refillRequest);
    }
}
