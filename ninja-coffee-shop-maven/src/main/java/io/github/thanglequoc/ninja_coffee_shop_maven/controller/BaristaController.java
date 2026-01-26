package io.github.thanglequoc.ninja_coffee_shop_maven.controller;

import io.github.thanglequoc.ninja_coffee_shop_maven.dto.*;
import io.github.thanglequoc.ninja_coffee_shop_maven.model.beverage.Beverage;
import io.github.thanglequoc.ninja_coffee_shop_maven.service.BaristaService;
import io.github.thanglequoc.ninja_coffee_shop_maven.service.CoffeeMachineService;
import io.github.thanglequoc.timerninja.TimerNinjaTracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @TimerNinjaTracker
    public Beverage makeDrink() {
        OrderRequest currentActiveOrder = baristaService.getActiveOrder();
        return coffeeMachineService.makeDrink(currentActiveOrder);
    }

    @GetMapping("/checkMaterial")
    public MaterialStatus getMaterialStatus() {
        return coffeeMachineService.getMaterialStatus();
    }

    @PostMapping("/refillMaterial")
    public MaterialStatus refillMaterials(@RequestBody RefillMaterialRequest refillRequest) {
        return coffeeMachineService.refillMaterial(refillRequest);
    }

    @GetMapping("/checkRevenue")
    public int checkRevenue() {
        return baristaService.getTotalRevenue();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalStateException ex) {
        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }
}
