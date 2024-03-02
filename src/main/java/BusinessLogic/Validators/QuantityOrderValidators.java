package BusinessLogic.Validators;

import Model.Orders;
/**
 * @Author Andreea Onaci
 * This class is validating the quantity of the product for an order
 */
public class QuantityOrderValidators implements Validators<Orders> {
    @Override
    public void validate(Orders orders) {
        if (orders.getQuantity() < 0 && Math.abs(orders.getQuantity()) != orders.getQuantity())
            throw new IllegalArgumentException("Check the quantity field!");
    }
}
