package Presentation;

import BusinessLogic.ProductBLL;
import Model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

/**
 * @Author Andreea Onaci
 * This class is creating the relation between the GUI and the Product operations
 */

public class ControllerProduct {
    private final ViewProduct viewProduct;
    private final ProductBLL productBLL;
    public ControllerProduct(ViewProduct viewProduct) throws SQLException {
        this.viewProduct = viewProduct;
        Product product = new Product(null, 0, 0, 0);
        productBLL = new ProductBLL();
        ArrayList<Product> list = new ArrayList<>();
        DefaultTableModel table = product.writeInTable(list, product);
        JTable table1 = viewProduct.getTable();
        table1.setModel(table);
        viewProduct.addAddButton(new addButton());
        viewProduct.addDeleteButton(new deleteButton());
        viewProduct.addUpdateButton(new updateButton());
        viewProduct.addBackToMenuButton(new backToMenu());
    }

    class addButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Product product = new Product(viewProduct.getAddName().getText(), 0, Integer.parseInt(viewProduct.getQuantity()), Double.parseDouble(viewProduct.getPrice()));
                product.setId(product.getIdForRecord(product));
                if (viewProduct.getAddName().getText().equals(""))
                    viewProduct.showMessage("Invalid Data!");
                else {
                    productBLL.add(product);
                    viewProduct.showMessage("Product added succesfully!");
                    ArrayList<Product> list = new ArrayList<>();
                    list.add(product);
                    list.clear();
                    DefaultTableModel table = product.writeInTable(list, product);
                    JTable table1 = viewProduct.getTable();
                    table1.setModel(table);
                }
            } catch (Exception exception) {
                viewProduct.showMessage("Something went wrong! :(");
                exception.printStackTrace();
            }
        }
    }

    class deleteButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Product product = new Product(Integer.parseInt(viewProduct.getDeleteName().getText()));
                if (viewProduct.getDeleteName().getText().equals(""))
                    viewProduct.showMessage("Invalid Data!");
                else {
                    productBLL.findById(Integer.parseInt(viewProduct.getDeleteName().getText()));
                    productBLL.delete(product);
                    viewProduct.showMessage("Product deleted succesfully!");
                    ArrayList<Product> list = new ArrayList<>();
                    DefaultTableModel table = product.writeInTable(list, product);
                    JTable table1 = viewProduct.getTable();
                    table1.setModel(table);
                }
            } catch (Exception exception) {
                viewProduct.showMessage("Something went wrong! :(");
                exception.printStackTrace();
            }
        }
    }

    class updateButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Product product = new Product(Integer.parseInt(viewProduct.getUpdateName().getText()));
                productBLL.findById(Integer.parseInt(viewProduct.getUpdateName().getText()));
                if (viewProduct.getUpdateName().equals(""))
                    viewProduct.showMessage("Invalid Data!");
                else {
                    int idProduct = product.getId();
                    int quantity;
                    double price;
                    String name;
                    if (viewProduct.getWithName().getText().equals("")) {
                        name = product.findName(product);
                    }
                    else
                        name = viewProduct.getWithName().getText();
                    if (viewProduct.getWithQuantity().getText().equals(""))
                        //quantity = product.getQuantity();
                        quantity = product.findField(product, "quantity").getQuantity();
                    else
                        quantity = Integer.parseInt(viewProduct.getWithQuantity().getText());
                    if (viewProduct.getWithPrice().getText().equals(""))
                        price = product.findField(product, "price").getPrice();
                        //price = product.getPrice();
                    else
                        price = Double.parseDouble(viewProduct.getWithPrice().getText());
                    Product product1 = new Product(name, idProduct, quantity, price);
                    productBLL.update(product1);
                    ArrayList<Product> list = new ArrayList<>();
                    DefaultTableModel table = product.writeInTable(list, product);
                    JTable table1 = viewProduct.getTable();
                    table1.setModel(table);
                    viewProduct.showMessage("Product updated succesfully!");
                }
            } catch (Exception exception) {
                viewProduct.showMessage("Something went wrong! :(");
                exception.printStackTrace();
            }
        }
    }
    class backToMenu implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            viewProduct.setVisible(false);
            ViewMenu viewMenu = new ViewMenu();
            viewMenu.setVisible(true);
            new ControllerMain(viewMenu);
        }
    }
}
