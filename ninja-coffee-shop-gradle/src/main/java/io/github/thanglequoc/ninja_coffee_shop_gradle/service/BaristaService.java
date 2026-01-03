package io.github.thanglequoc.ninja_coffee_shop_gradle.service;

import io.github.thanglequoc.ninja_coffee_shop_gradle.dto.OrderRequest;
import io.github.thanglequoc.ninja_coffee_shop_gradle.dto.PaymentRequest;
import io.github.thanglequoc.ninja_coffee_shop_gradle.dto.Receipt;
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

        // Build receipt
        Receipt receipt = new Receipt();
        receipt.setOrderID(activeOrder.getOrderID());
        receipt.setReceiptID("RCPT-" + UUID.randomUUID().toString());
        receipt.setPaymentAmount(paymentRequest.getAmount());
        receipt.setPaymentChannel(paymentRequest.getPaymentChannel() != null ? paymentRequest.getPaymentChannel().name() : "UNKNOWN");

        // Update revenue
        this.totalRevenue += paymentRequest.getAmount();

        // Clear active order
        this.activeOrder = null;

        return receipt;
    }

}
