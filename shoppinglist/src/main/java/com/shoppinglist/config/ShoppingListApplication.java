package com.shoppinglist.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.shoppinglist")
@ComponentScan("com.shoppinglist")
@EntityScan(basePackages = "com.shoppinglist")
public class ShoppingListApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingListApplication.class, args);
		//ConfigurableApplicationContext context = SpringApplication.run(ShoppingListApplication.class, args);
	}

}
