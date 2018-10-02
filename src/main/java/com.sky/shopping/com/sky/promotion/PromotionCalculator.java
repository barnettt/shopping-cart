package com.sky.shopping.com.sky.promotion;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;


import com.sky.shopping.Item;

public class PromotionCalculator {

    private static Double ONE_THIRD  = 0.33;

    BiPredicate<String, String> typeCheck  = (a,b) -> a.equals(b);
    BiPredicate<Integer,Integer> threeForTwoCheck = (a,b) -> a >= b;

    public Double findPercentageDiscount(final BiFunction<Double, Double,Double> promotion, final Item item, final double value) {
            if(typeCheck.test(item.getType(),"Audio")) {
                Double intermediateValue = promotion.apply( item.getPrice(),value);
                return doRoundUpAndDecimalPlaces(intermediateValue);
            }
            return item.getPrice();
    }

    public Double findThreeForTwoDiscount(final BiFunction<Double, Double, Double> promotion, final Item item, final double v) {
        if(typeCheck.test(item.getType(),"Power") && threeForTwoCheck.test(item.getQuantity(), 3)) {
            Double intermediateValue = (item.getPrice() - (item.getPrice() * ONE_THIRD ));
            return doRoundUpAndDecimalPlaces(intermediateValue);
        }
        return item.getPrice();
    }

    private Double doRoundUpAndDecimalPlaces(final double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
