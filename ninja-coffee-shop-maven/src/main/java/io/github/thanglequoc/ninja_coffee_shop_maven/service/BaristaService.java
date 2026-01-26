package io.github.thanglequoc.ninja_coffee_shop_maven.service;

import io.github.thanglequoc.ninja_coffee_shop_maven.dto.OrderRequest;
import io.github.thanglequoc.ninja_coffee_shop_maven.dto.PaymentRequest;
import io.github.thanglequoc.ninja_coffee_shop_maven.dto.Receipt;
import io.github.thanglequoc.timerninja.TimerNinjaTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BaristaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaristaService.class);
    private int index = 0;
    private int totalRevenue;
    private OrderRequest activeOrder;
    private PaymentService paymentService;

    @Autowired
    public BaristaService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @TimerNinjaTracker(includeArgs = true)
    public OrderRequest placeOrder(OrderRequest orderRequest) {
        if (activeOrder != null) {
            throw new IllegalStateException("Unable to place new order. Processing current order: " + activeOrder.getOrderID());
        }

        String orderID = generateOrderID();
        orderRequest.setOrderID(orderID);
        this.activeOrder = orderRequest;
        LOGGER.info("New order received: {} - Customer: {}", activeOrder.getOrderID(), activeOrder.getCustomerName());
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

        String paidOrderId = activeOrder.getOrderID();
        String paidCustomerName = activeOrder.getCustomerName();

        Receipt receipt = new Receipt();
        receipt.setOrderID(paidOrderId);
        receipt.setReceiptID("RCPT-" + UUID.randomUUID());
        receipt.setPaymentAmount(paymentRequest.getAmount());
        receipt.setCustomerName(paidCustomerName);

        switch (paymentRequest.getPaymentChannel()) {
            case CASH -> paid = paymentService.payWithCash(transactionId, paymentRequest.getAmount());
            case TRANSFER -> paid = paymentService.payByTransfer(transactionId, "TIMER-NINJA-CORP-BNK", paymentRequest.getAmount());
            case CREDIT_CARD -> paid = paymentService.payWithCreditCard(transactionId, paymentRequest.getAmount());
            default -> paid = false;
        }

        if (paid) {
            this.totalRevenue += paymentRequest.getAmount();
            this.activeOrder = null;
            LOGGER.info("Order {} has been paid successfully: paymentChannel - {}, amount - {} ", paidOrderId, paymentRequest.getPaymentChannel(), paymentRequest.getAmount());
            return receipt;
        } else {
            throw new IllegalStateException("Unable to pay for order");
        }
    }

    public int getTotalRevenue() {
        return totalRevenue;
    }

    public OrderRequest getActiveOrder() {
        if (activeOrder == null) {
            throw new IllegalStateException("There is no active order");
        }
        return activeOrder;
    }

    @TimerNinjaTracker
    private String generateOrderID() {
        return "ORDER-" + index++;
    }
}
