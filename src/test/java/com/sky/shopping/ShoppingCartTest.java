package com.sky.shopping;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.function.BiFunction;

import com.sky.shopping.com.sky.promotion.PromotionCalculator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingCartTest {

    ArgumentCaptor<Item> itemCaptor = ArgumentCaptor.forClass(Item.class);

    @Test
    public void canAddItemsToShoppingCart() {

        ShoppingCart shoppingCart = mock(ShoppingCart.class);
        Item item = new Item("Speaker", "Audio", 1, 85.00d);
        shoppingCart.addItem(item);
        verify(shoppingCart, times(1)).addItem(itemCaptor.capture());

        List<Item> items = itemCaptor.getAllValues();
        assertThat(items.get(0).getName(), is("Speaker"));
        assertThat(items.get(0).getType(), is("Audio"));
        assertThat(items.get(0).getQuantity(), is(1));
    }

    @Test
    public void shouldApplyDiscountToItems() {
        Item item = new Item("Speaker", "Audio", 1, 85.00d);
        BiFunction<Double, Double, Double> promotion = (a, b) -> new Double(a * b);
        PromotionCalculator calculator = new PromotionCalculator();
        assertThat(calculator.findPercentageDiscount(promotion, item, 0.3), is(25.5));
    }

    @Test
    public void shouldApply3For2Discount() {
        Item item = new Item("AAA Batteries", "Power", 5, 0.85d);
        BiFunction<Double, Double, Double> promotion = (a, b) -> new Double(a / b);
        PromotionCalculator calculator = new PromotionCalculator();
        assertThat(calculator.findThreeForTwoDiscount(promotion, item, 0.33d), is(.57));
    }

    @Test
    public void shouldCalculateItemTotals() {

        ShoppingCart shoppingCart = getPopulatedShoppingCart();

        shoppingCart.calculateTotalBeforeDiscount();

        shoppingCart.getItems().forEach(item ->
        {
            assertThat(item.getTotalBeforeDiscount(), notNullValue());
            assertThat(item.getTotalBeforeDiscount(), is(item.getPrice() * item.getQuantity()));
        });
    }

    @Test
    public void shouldCalculateTotalsWithDiscount() {
        ShoppingCart shoppingCart = getPopulatedShoppingCart();

        shoppingCart.calculateTotalsAfterDiscount();

        shoppingCart.getItems().forEach(item ->
        {
            assertThat(item.getTotalAfterDiscount(), notNullValue());
            assertThat(item.getTotalAfterDiscount(), is(shoppingCart.applyDiscounts(item)));
        });

        System.out.println(shoppingCart.toString());
    }

    private ShoppingCart getPopulatedShoppingCart() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(new Item("AAA Batteries", "Power", 5, 0.85d));
        shoppingCart.addItem(new Item("Speaker", "Audio", 1, 85.00d));
        shoppingCart.addItem(new Item("HeadPhones", "Audio", 1, 150.00d));
        shoppingCart.addItem(new Item("Protein Bars (Box)", "Food", 2, 52.00d));
        return shoppingCart;
    }


}
