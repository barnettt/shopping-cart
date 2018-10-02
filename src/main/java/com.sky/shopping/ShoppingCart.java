package com.sky.shopping;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import com.sky.shopping.com.sky.promotion.PromotionCalculator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class ShoppingCart {

    private static final Double AUDIO_DISCOUNT_PERCENTAGE = 0.3d;
    private static final Double POWER_DISCOUNT_ONE_THIRD = 0.33;

    PromotionCalculator promotionCalculator = new PromotionCalculator();
    BiFunction<Double, Double, Double> percentagePromotion = (a, b) -> new Double(a * b);
    BiFunction<Double, Double, Double> threeForTwoPromotion = (a, b)  ->  new Double(a/b);

    List<Item> items = new ArrayList<>();

    public List<Item> getItems() {
        return items;
    }

    public void addItem(final Item item) {
        getItems().add(item);
    }

    public void calculateTotalBeforeDiscount() {
        getItems().forEach(item -> item.setTotalBeforeDiscount(item.getQuantity() * item.getPrice()));
    }

    public void calculateTotalsAfterDiscount() {
        getItems().forEach(item -> item.setTotalAfterDiscount(this.applyDiscounts(item)));
    }

    public Double applyDiscounts(final Item item) {

        switch (item.type) {
            case "Audio" : item.setTotalAfterDiscount(doCalculatePercentageDiscount(item));
                break;
            case "Power" : item.setTotalAfterDiscount(getDoCalculateThreeForTwo(item)); break;
            case "Food"  : item.setTotalAfterDiscount(item.getPrice() * item.getQuantity()); break;
        }
        return item.getTotalAfterDiscount();
    }

    private Double doCalculatePercentageDiscount(final Item item) {

        Double discount = promotionCalculator.findPercentageDiscount(percentagePromotion, item, AUDIO_DISCOUNT_PERCENTAGE);
        return (item.getPrice() - discount) * item.getQuantity();
    }

    private Double getDoCalculateThreeForTwo(final Item item) {
       Double discount = 3 * promotionCalculator.findThreeForTwoDiscount(threeForTwoPromotion, item, POWER_DISCOUNT_ONE_THIRD);
       return discount +( (item.getQuantity() - 3) * item.getPrice());
    }

    public void printCartItems() {
        System.out.println("Item,            Type,     Qty,   Price(Before Discount, After Discount)");
        this.getItems().forEach(item ->
            System.out.println(item.getName()+ "         "+ item.getType()+"    "+
                    item.getQuantity()+ "     "+item.getTotalBeforeDiscount() + "      "+ item.getTotalAfterDiscount())
        );
    }
}
