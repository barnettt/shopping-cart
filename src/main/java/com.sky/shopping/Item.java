package com.sky.shopping;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Item {
    String name;
    String type;
    Integer quantity;
    Double price;
    Double totalBeforeDiscount;
    Double totalAfterDiscount;

    public Item(final String name, final String type, final Integer quantity, final double price) {
        this.name = name;
        this.type = type;
        this.quantity = quantity;
        this.price = price;

    }
}
