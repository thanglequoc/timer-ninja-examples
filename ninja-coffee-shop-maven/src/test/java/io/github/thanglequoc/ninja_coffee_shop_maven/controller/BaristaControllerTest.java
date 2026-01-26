package io.github.thanglequoc.ninja_coffee_shop_maven.controller;

import io.github.thanglequoc.ninja_coffee_shop_maven.dto.OrderRequest;
import io.github.thanglequoc.ninja_coffee_shop_maven.dto.PaymentRequest;
import io.github.thanglequoc.ninja_coffee_shop_maven.dto.Receipt;
import io.github.thanglequoc.ninja_coffee_shop_maven.service.BaristaService;
import io.github.thanglequoc.ninja_coffee_shop_maven.service.CoffeeMachineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BaristaControllerTest {
    @Mock
    private CoffeeMachineService coffeeMachineService;
    @Mock
    private BaristaService baristaService;
    @InjectMocks
    private BaristaController baristaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPlaceOrder() {
        OrderRequest req = new OrderRequest();
        when(baristaService.placeOrder(req)).thenReturn(req);
        assertEquals(req, baristaController.placeOrder(req));
    }

    @Test
    void testPay() {
        PaymentRequest paymentRequest = new PaymentRequest();
        Receipt receipt = new Receipt();
        when(baristaService.handleUserPayment(paymentRequest)).thenReturn(receipt);
        assertEquals(receipt, baristaController.pay(paymentRequest));
    }

    @Test
    void testCheckRevenue() {
        when(baristaService.getTotalRevenue()).thenReturn(42);
        assertEquals(42, baristaController.checkRevenue());
    }

    @Test
    void testHandleIllegalArgument() {
        ResponseEntity<String> response = baristaController.handleIllegalArgument(new IllegalArgumentException("bad arg"));
        assertEquals(400, response.getStatusCode().value());
        assertEquals("bad arg", response.getBody());
    }
}
