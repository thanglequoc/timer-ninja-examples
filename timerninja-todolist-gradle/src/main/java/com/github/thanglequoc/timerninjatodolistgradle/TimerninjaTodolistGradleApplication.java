package com.github.thanglequoc.timerninjatodolistgradle;

import io.github.thanglequoc.timerninja.TimerNinjaConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TimerninjaTodolistGradleApplication {

	public static void main(String[] args) {
		TimerNinjaConfiguration.getInstance().toggleSystemOutLog(false);
		SpringApplication.run(TimerninjaTodolistGradleApplication.class, args);
	}

}
