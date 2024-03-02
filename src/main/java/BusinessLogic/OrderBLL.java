package BusinessLogic;

import BusinessLogic.Validators.QuantityOrderValidators;
import BusinessLogic.Validators.Validators;
import DataAccess.OrderDAO;
import Model.Orders;

import java.util.ArrayList;
import java.util.List;

/**
 *  Author: Andreea Onaci
 *  This class calls the methods from DAO, but only after validating the input
 */
public class OrderBLL {
    private List<Validators<Orders>> validators;
    private OrderDAO orderDAO;

    public OrderBLL() {
        validators = new ArrayList<>();
        validators.add(new QuantityOrderValidators());
        orderDAO = new OrderDAO();
    }

    public void add(Orders order) throws Exception {
        for (Validators<Orders> v : validators) {
            v.validate(order);
        }
        orderDAO.add(order);
    }
}
