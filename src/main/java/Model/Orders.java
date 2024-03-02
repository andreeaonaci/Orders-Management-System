package Model;

import DataAccess.AbstractDAO;
import DataAccess.Fields;

/**
 * @Author Andreea Onaci
 * This class contains fields and their getters and setters for an Order, in fact, the data corresponding to an Order
 */

public class Orders extends AbstractDAO<Orders> implements Fields {
    private int client;
    private int product;
    private int id;

    public Orders(int client, int product, int id, int quantity, double price) {

        this.client = client;
        this.id = id;
        this.quantity = quantity;
        this.product = product;
        this.price = price;
    }
    public Orders() {

    }
    private int quantity;
    private double price;

    public int getProduct() {
        return product;
    }

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    private static final String TABLE_NAME = "warehouse.order";

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public void setId(int id) {
        this.id = id;
    }
}
