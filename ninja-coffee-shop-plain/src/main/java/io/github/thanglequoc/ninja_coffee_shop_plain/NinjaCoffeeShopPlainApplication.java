package io.github.thanglequoc.ninja_coffee_shop_plain;

import io.github.thanglequoc.ninja_coffee_shop_plain.dto.*;
import io.github.thanglequoc.ninja_coffee_shop_plain.model.beverage.Beverage;
import io.github.thanglequoc.ninja_coffee_shop_plain.service.BaristaService;
import io.github.thanglequoc.ninja_coffee_shop_plain.service.CoffeeMachineService;
import io.github.thanglequoc.ninja_coffee_shop_plain.service.PaymentService;

public class NinjaCoffeeShopPlainApplication {
	public static void main(String[] args) {
		System.out.println("Ninja Coffee Shop Plain Application started.");

		// Setup services
		PaymentService paymentService = new PaymentService();
		BaristaService baristaService = new BaristaService(paymentService);
		CoffeeMachineService coffeeMachineService = new CoffeeMachineService();

		// 1. Simulate user placing an order
		OrderRequest order = new OrderRequest();
		order.setItemId(1); // 1 = Americano
		order.setHot(true);
		order.setSweetness(2);
		order.setSize("M");
		order.setCustomerName("Alice");
		System.out.println("Placing order for: " + order.getCustomerName());
		baristaService.placeOrder(order);

		// 2. Simulate user paying for the order
		PaymentRequest paymentRequest = new PaymentRequest();
		paymentRequest.setOrderId(order.getOrderID());
		paymentRequest.setAmount(50); // Assume price is 50
		paymentRequest.setPaymentChannel(PaymentChannel.CASH);
		System.out.println("Paying for order: " + paymentRequest.getOrderId());
		Receipt receipt = baristaService.handleUserPayment(paymentRequest);
		System.out.println("Payment receipt: " + receipt.getReceiptID() + ", Amount: " + receipt.getPaymentAmount());

		// 3. Brew the coffee
		System.out.println("Brewing coffee...");
		// Place order again to simulate active order for brewing
		baristaService.placeOrder(order);
		Beverage beverage = coffeeMachineService.makeDrink(order);
		System.out.println("Coffee brewed: " + beverage.getName() + " (" + beverage.getSize() + ", hot: " + beverage.isHot() + ", sweetness: " + beverage.getSweetness() + ")");
	}
}
