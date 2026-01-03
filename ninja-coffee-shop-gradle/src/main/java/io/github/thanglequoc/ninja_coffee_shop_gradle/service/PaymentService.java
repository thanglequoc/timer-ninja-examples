package io.github.thanglequoc.ninja_coffee_shop_gradle.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);

    public boolean payWithCash(String transactionID, int amount) {
        // Cha-ching, no wait time
        LOGGER.info("Paying transaction ID: {} with cash. Amount: {}", transactionID, amount);
        return true;
    }

    /* Simulate pay by transfer. Usually this will involve QR generation, but we keep it simple for the demo */
    public boolean payByTransfer(String transactionID, String sourceAccount, String receivingAccount, int amount) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        LOGGER.info("Paying transaction ID: {} by bank transfer. Source: {}, Target: {}, Amount: {}", transactionID, sourceAccount, receivingAccount, amount);
        return true;
    }

    public boolean payWithCreditCard(String transactionID, String cardNumber, int amount) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        LOGGER.info("Paying transaction ID: {} with credit card number {}. Amount: {}", transactionID, cardNumber, amount);
        return true;
    }




}
