package io.github.thanglequoc.ninja_coffee_shop_plain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaymentService {

    private Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);

    public boolean payWithCash(String transactionID, int amount) {
        LOGGER.info("Paying transaction ID: {} with cash. Amount: {}", transactionID, amount);
        return true;
    }

    public boolean payByTransfer(String transactionID, String receivingAccount, int amount) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        LOGGER.info("Paying transaction ID: {} by bank transfer. Target: {}, Amount: {}", transactionID, receivingAccount, amount);
        return true;
    }

    public boolean payWithCreditCard(String transactionID, int amount) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        LOGGER.info("Paying transaction ID: {} with credit card. Amount: {}", transactionID, amount);
        return true;
    }
}
