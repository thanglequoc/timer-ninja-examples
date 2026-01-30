package io.github.thanglequoc.ninja_coffee_shop_maven;

import io.github.thanglequoc.ninja_coffee_shop_maven.service.ShopKeepingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;
import io.github.thanglequoc.timerninja.TimerNinjaTracker;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class NinjaCoffeeShopMavenApplication {

	private static final Logger logger = LoggerFactory.getLogger(NinjaCoffeeShopMavenApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(NinjaCoffeeShopMavenApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	@TimerNinjaTracker
	public void openTheCoffeeShop() {
		logger.info("Cleaning the coffee shop...");
		ShopKeepingService shopKeepingService = new ShopKeepingService();
		shopKeepingService.cleanTheShop();
		logger.info("Opening the door. The coffee shop is now open!");
	}

}
