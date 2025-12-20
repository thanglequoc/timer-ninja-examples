package io.github.thanglequoc.ninja_coffee_shop_gradle.controller;

import io.github.thanglequoc.ninja_coffee_shop_gradle.dto.OrderRequest;
import io.github.thanglequoc.ninja_coffee_shop_gradle.model.beverage.Beverage;
import io.github.thanglequoc.ninja_coffee_shop_gradle.service.CoffeeMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaristaController {

    private final CoffeeMachineService coffeeMachineService;

    @Autowired
    public BaristaController(CoffeeMachineService coffeeMachineService) {
        this.coffeeMachineService = coffeeMachineService;
    }

    @PostMapping("/order")
    public Beverage makeDrink(@RequestBody OrderRequest orderReq) {
        return coffeeMachineService.makeDrink(orderReq.getItemId());
    }
}
