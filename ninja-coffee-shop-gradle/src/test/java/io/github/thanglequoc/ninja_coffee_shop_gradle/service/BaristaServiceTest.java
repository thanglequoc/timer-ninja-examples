package io.github.thanglequoc.ninja_coffee_shop_gradle.service;

import io.github.thanglequoc.ninja_coffee_shop_gradle.dto.OrderRequest;
import io.github.thanglequoc.ninja_coffee_shop_gradle.dto.PaymentRequest;
import io.github.thanglequoc.ninja_coffee_shop_gradle.dto.Receipt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BaristaServiceTest {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private BaristaService baristaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPlaceOrderSetsActiveOrder() {
        OrderRequest order = new OrderRequest();
        baristaService.placeOrder(order);
        assertEquals(order, baristaService.getActiveOrder());
    }

    @Test
    void testHandleUserPaymentUpdatesRevenue() {
        PaymentRequest paymentRequest = mock(PaymentRequest.class);
        OrderRequest order = new OrderRequest();
        order.setOrderID("ORDER-0");
        order.setCustomerName("Test Customer");
        baristaService.placeOrder(order);
        when(paymentRequest.getOrderId()).thenReturn("ORDER-0");
        when(paymentRequest.getAmount()).thenReturn(100);
        when(paymentRequest.getPaymentChannel()).thenReturn(io.github.thanglequoc.ninja_coffee_shop_gradle.dto.PaymentChannel.CASH);
        // Mock the paymentService method for CASH
        when(paymentService.payWithCash(anyString(), eq(100))).thenReturn(true);
        Receipt result = baristaService.handleUserPayment(paymentRequest);
        assertNotNull(result);
        assertEquals("ORDER-0", result.getOrderID());
        assertEquals(100, result.getPaymentAmount());
    }

    @Test
    void testGetTotalRevenue() {
        assertEquals(0, baristaService.getTotalRevenue());
    }

    @Test
    void testGetActiveOrderThrowsIfNone() {
        assertThrows(IllegalStateException.class, () -> baristaService.getActiveOrder());
    }
}
