package com.shoppinglist.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.shoppinglist")
public class ShoppingListApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ShoppingListApplication.class, args);
	}

}
