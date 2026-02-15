package io.github.thanglequoc.ninja_coffee_shop_gradle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;

import io.github.thanglequoc.ninja_coffee_shop_gradle.service.ShopKeepingService;
import io.github.thanglequoc.timerninja.TimerNinjaTracker;

import org.springframework.boot.context.event.ApplicationReadyEvent;

@SpringBootApplication
public class NinjaCoffeeShopGradleApplication {

	private static final Logger logger = LoggerFactory.getLogger(NinjaCoffeeShopGradleApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(NinjaCoffeeShopGradleApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	@TimerNinjaTracker
	public void openTheCoffeeShop() {
		logger.info("Cleaning the coffee shop...", getShopNameBanner());
		ShopKeepingService shopKeepingService = new ShopKeepingService();
		shopKeepingService.cleanTheShop();
		logger.info("Opening the door. The coffee shop is now open!");
	}

	@TimerNinjaTracker
	public static String getShopNameBanner() {
		return "The Timer Ninja Coffee Shop";
	}
}
