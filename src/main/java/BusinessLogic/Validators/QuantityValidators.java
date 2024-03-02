package BusinessLogic.Validators;

import Model.Product;
/**
 * @Author Andreea Onaci
 * This class is validating the quantity of a product
 */
public class QuantityValidators implements Validators<Product> {
    @Override
    public void validate(Product t) {
        if (t.getQuantity() < 0 && Math.abs(t.getQuantity()) != t.getQuantity())
            throw new IllegalArgumentException("Check the quantity field!");
    }
}
