package com.sky;

import com.sky.shopping.Item;
import com.sky.shopping.ShoppingCart;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static final void main (String[] args) {
        SpringApplication.run(Application.class, args);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(new Item("Speaker", "Audio", 1, 85.00d));
        shoppingCart.addItem(new Item("AAA Batteries", "Power", 5, 0.85d));
        shoppingCart.addItem(new Item("Protein Bars (Box)", "Food", 2, 52.00d));

        shoppingCart.calculateTotalBeforeDiscount();
        shoppingCart.calculateTotalsAfterDiscount();
        shoppingCart.printCartItems();
    }

}
