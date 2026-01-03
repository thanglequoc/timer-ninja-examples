package io.github.thanglequoc.ninja_coffee_shop_gradle.controller;

import io.github.thanglequoc.ninja_coffee_shop_gradle.dto.OrderRequest;
import io.github.thanglequoc.ninja_coffee_shop_gradle.dto.PaymentRequest;
import io.github.thanglequoc.ninja_coffee_shop_gradle.dto.Receipt;
import io.github.thanglequoc.ninja_coffee_shop_gradle.model.beverage.Beverage;
import io.github.thanglequoc.ninja_coffee_shop_gradle.service.CoffeeMachineService;
import io.github.thanglequoc.ninja_coffee_shop_gradle.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class BaristaController {

    private final CoffeeMachineService coffeeMachineService;
    private final PaymentService paymentService;

    @Autowired
    public BaristaController(CoffeeMachineService coffeeMachineService, PaymentService paymentService) {
        this.coffeeMachineService = coffeeMachineService;
        this.paymentService = paymentService;
    }

    @PostMapping("/order")
    public Beverage makeDrink(@RequestBody OrderRequest orderReq) {
        return coffeeMachineService.makeDrink(orderReq.getItemId());
    }

    @PostMapping("/pay")
    public Receipt pay(@RequestBody PaymentRequest paymentRequest) {
        // Create a simple transaction id
        String transactionId = UUID.randomUUID().toString();
        boolean paid;

        switch (paymentRequest.getPaymentChannel()) {
            case CASH -> paid = paymentService.payWithCash(transactionId, paymentRequest.getAmount());
            case TRANSFER -> paid = paymentService.payByTransfer(transactionId, "src-account", "dest-account", paymentRequest.getAmount());
            case CREDIT_CARD -> paid = paymentService.payWithCreditCard(transactionId, "4111111111111111", paymentRequest.getAmount());
            default -> paid = false;
        }

        Receipt receipt = new Receipt();
        receipt.setOrderID(paymentRequest.getOrderId());
        receipt.setPaymentAmount(paymentRequest.getAmount());
        receipt.setPaymentChannel(paymentRequest.getPaymentChannel().name());
        receipt.setReceiptID(transactionId);

        if (paid) {
            // In a real app we'd lookup the order and fill beverage/customer info. Keep it simple.
            return receipt;
        }

        // Payment failed — in a real app we'd throw an exception or return an error response. Return receipt with amount 0 to signal failure.
        receipt.setPaymentAmount(0);
        return receipt;
    }
}
