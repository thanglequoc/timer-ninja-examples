package io.github.thanglequoc.ninja_coffee_shop_gradle.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceTest {
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        paymentService = new PaymentService();
    }

    /**
     * Test that payWithCash always returns true for a valid transaction.
     */
    @Test
    void testPayWithCashReturnsTrue() {
        assertTrue(paymentService.payWithCash("TXN-1", 100));
    }

    /**
     * Test that payByTransfer always returns true for a valid transaction.
     */
    @Test
    void testPayByTransferReturnsTrue() {
        assertTrue(paymentService.payByTransfer("TXN-2", "BANK-ACC", 200));
    }

    /**
     * Test that payWithCreditCard always returns true for a valid transaction.
     */
    @Test
    void testPayWithCreditCardReturnsTrue() {
        assertTrue(paymentService.payWithCreditCard("TXN-3", 300));
    }

    /**
     * Test that payByTransfer throws a RuntimeException with cause InterruptedException if the thread is interrupted.
     */
    @Test
    void testPayByTransferInterrupted() {
        Thread testThread = new Thread(() -> {
            try {
                paymentService.payByTransfer("TXN-INT", "BANK-ACC", 200);
            } catch (RuntimeException e) {
                assertTrue(e.getCause() instanceof InterruptedException);
            }
        });
        testThread.start();
        testThread.interrupt();
        try {
            testThread.join();
        } catch (InterruptedException ignored) {}
    }

    /**
     * Test that payWithCreditCard throws a RuntimeException with cause InterruptedException if the thread is interrupted.
     */
    @Test
    void testPayWithCreditCardInterrupted() {
        Thread testThread = new Thread(() -> {
            try {
                paymentService.payWithCreditCard("TXN-INT", 400);
            } catch (RuntimeException e) {
                assertTrue(e.getCause() instanceof InterruptedException);
            }
        });
        testThread.start();
        testThread.interrupt();
        try {
            testThread.join();
        } catch (InterruptedException ignored) {}
    }
}
