package Model;

import DataAccess.AbstractDAO;
import DataAccess.Fields;

import java.sql.SQLException;

/**
 * @Author Andreea Onaci
 * This class contains fields and their getters and setters for a Product, in fact, the data corresponding to a Product
 */

public class Product extends AbstractDAO<Product> implements Fields {
    private String name;
    private int id;
    public void setPrice(double price) {
        this.price = price;
    }
    private int quantity;
    private double price;
    public double getPrice() {
        return price;
    }

    public Product(String name) {
        this.name = name;
    }
    public Product(int id) {this.id = id;}

    public Product(String name, int id, int quantity, double price) {
        this.name = name;
        this.id = id;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Product() {
    }

    public int getId() throws SQLException {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    private static final String TABLE_NAME = "warehouse.product";

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
