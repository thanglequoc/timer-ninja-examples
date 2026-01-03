package io.github.thanglequoc.ninja_coffee_shop_gradle.service;

import io.github.thanglequoc.ninja_coffee_shop_gradle.dto.OrderRequest;
import io.github.thanglequoc.ninja_coffee_shop_gradle.dto.PaymentRequest;
import io.github.thanglequoc.ninja_coffee_shop_gradle.dto.Receipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/*
* The barista service store order & payment
*  */
@Service
public class BaristaService {

    private int index = 0;
    private int totalRevenue;
    private OrderRequest activeOrder;
    private PaymentService paymentService;

    @Autowired
    public BaristaService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public OrderRequest placeOrder(OrderRequest orderRequest) {
        // Use post-increment so first order id is ORDER-0, then ORDER-1, etc.
        orderRequest.setOrderID(String.format("ORDER-%d", index++));
        this.activeOrder = orderRequest;
        return activeOrder;
    }

    public Receipt handleUserPayment(PaymentRequest paymentRequest) {
        if (activeOrder == null) {
            throw new IllegalStateException("No active order to pay for");
        }
        if (!paymentRequest.getOrderId().equals(activeOrder.getOrderID())) {
            throw new IllegalStateException("Must pay to the active order");
        }

        String transactionId = UUID.randomUUID().toString();
        boolean paid;

        // Build receipt
        Receipt receipt = new Receipt();
        receipt.setOrderID(activeOrder.getOrderID());
        receipt.setReceiptID("RCPT-" + UUID.randomUUID().toString());
        receipt.setPaymentAmount(paymentRequest.getAmount());

        switch (paymentRequest.getPaymentChannel()) {
            case CASH -> paid = paymentService.payWithCash(transactionId, paymentRequest.getAmount());
            case TRANSFER -> paid = paymentService.payByTransfer(transactionId, "TIMER-NINJA-CORP-BNK", paymentRequest.getAmount());
            case CREDIT_CARD -> paid = paymentService.payWithCreditCard(transactionId, paymentRequest.getAmount());
            default -> paid = false;
        }

        // Update revenue
        if (paid) {
            this.totalRevenue += paymentRequest.getAmount();
            // Clear active order
            this.activeOrder = null;
            return receipt;
        } else {
            throw new IllegalStateException("Unable to pay for order");
        }
    }

}
