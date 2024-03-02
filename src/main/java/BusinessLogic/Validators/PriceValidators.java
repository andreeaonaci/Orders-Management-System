package BusinessLogic.Validators;

import Model.Product;
/**
 * @Author Andreea Onaci
 * This class is validating the price of the product
 */
public class PriceValidators implements Validators<Product> {
    @Override
    public void validate(Product t) {
        if (t.getPrice() < 0 && Math.abs(t.getPrice()) != t.getPrice())
            throw new IllegalArgumentException("Check the price field!");
    }
}
