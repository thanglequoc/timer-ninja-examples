package io.github.thanglequoc.ninja_coffee_shop_maven.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceTest {
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        paymentService = new PaymentService();
    }

    @Test
    void testPayWithCashReturnsTrue() {
        assertTrue(paymentService.payWithCash("TXN-1", 100));
    }

    @Test
    void testPayByTransferReturnsTrue() {
        assertTrue(paymentService.payByTransfer("TXN-2", "BANK-ACC", 200));
    }

    @Test
    void testPayWithCreditCardReturnsTrue() {
        assertTrue(paymentService.payWithCreditCard("TXN-3", 300));
    }

    // Additional tests for interruption can be added if needed
}
