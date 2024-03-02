package Presentation;

import BusinessLogic.OrderBLL;
import BusinessLogic.ProductBLL;
import DataAccess.OrderDAO;
import Model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * @Author Andreea Onaci
 * This class is creating the relation between the GUI and the Orders operations
 */

public class ControllerOrder {
    private ViewOrders viewOrders;
    private OrderBLL orderBLL;
    private ProductBLL productBLL;
    private LogFile logFile;
    private Orders order;
    private Product product;
    private Client client;
    private ViewMenu viewMenu;

    public ControllerOrder(ViewOrders viewOrders) throws SQLException, NoSuchMethodException {
        this.viewOrders = viewOrders;
        this.product = new Product(null, 0, 0, 0);
        productBLL = new ProductBLL();
        order = new Orders(0, 0, 0, 0, 0);
        orderBLL = new OrderBLL();
        this.client = new Client(null, 0);
        logFile = new LogFile();
        product.fillCombo(viewOrders.getProduct(), product);
        client.fillCombo(viewOrders.getClient(), client);
        viewOrders.addOrderButton(new addOrder());
        viewOrders.addBackToMenuButton(new backToMenu());
        viewOrders.addViewBills(new viewBills());
        ArrayList<Orders> list = new ArrayList<>();
        DefaultTableModel table = order.writeInTable(list, order);
        JTable table1 = viewOrders.getTable();
        table1.setModel(table);
        ArrayList<Product> list1 = new ArrayList<>();
        DefaultTableModel tableProduct = product.writeInTable(list1, product);
        JTable table2 = viewOrders.getTable1();
        table2.setModel(tableProduct);
    }

    class addOrder implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (viewOrders.getQuantityField().equals("")) viewOrders.showMessage("Invalid Data!");
                else {
                    client.setName(viewOrders.getClient().getItemAt(viewOrders.getClient().getSelectedIndex()));
                    product.setName(viewOrders.getProduct().getItemAt(viewOrders.getProduct().getSelectedIndex()));
                    product.setId(product.findId(product));
                    Product productAux = new Product(viewOrders.getProduct().getItemAt(viewOrders.getProduct().getSelectedIndex()));
                    product.setQuantity(product.findField(product, "quantity").getQuantity());
                    product.setPrice(product.findField(product, "price").getPrice());
                    double price = product.getPrice();
                    if (product.getQuantity() - Integer.parseInt(viewOrders.getQuantityField()) >= 0) {
                        Orders newOrder = new Orders(client.findId(client), ControllerOrder.this.product.findId(ControllerOrder.this.product), order.getIdForRecord(order), Integer.parseInt(viewOrders.getQuantityField()), price);
                        orderBLL.add(newOrder);
                        Product product1 = new Product(viewOrders.getProduct().getItemAt(viewOrders.getProduct().getSelectedIndex()), productAux.findId(productAux), ControllerOrder.this.product.getQuantity() - Integer.parseInt(viewOrders.getQuantityField()), price);
                        productBLL.findById(productAux.findId(productAux));
                        productBLL.update(product1);
                        LocalDateTime currentDateTime = LocalDateTime.now();
                        new LogFile(order.getIdForRecord(order) - 1, currentDateTime, ControllerOrder.this.product.findId(ControllerOrder.this.product), client.findId(client), Integer.parseInt(viewOrders.getQuantityField()), price, price * Integer.parseInt(viewOrders.getQuantityField()));
                        viewOrders.showMessage("Order added successfully!");
                        ArrayList<Orders> list = new ArrayList<>();
                        DefaultTableModel table = newOrder.writeInTable(list, order);
                        JTable table1 = viewOrders.getTable();
                        table1.setModel(table);
                        ArrayList<Product> list1 = new ArrayList<>();
                        DefaultTableModel tableProduct = product1.writeInTable(list1, product);
                        JTable table2 = viewOrders.getTable1();
                        table2.setModel(tableProduct);
                    } else JOptionPane.showMessageDialog(null, "Insufficient stock!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Something went wrong! :(");
                throw new RuntimeException(ex);
            }
        }
    }

    class backToMenu implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            viewOrders.setVisible(false);
            viewMenu = new ViewMenu();
            viewMenu.setVisible(true);
            new ControllerMain(viewMenu);
        }
    }

    class viewBills implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ViewBills viewBills = new ViewBills();
            viewBills.setVisible(true);
            DefaultTableModel table = logFile.writeInTableLog();
            JTable table1 = viewBills.getTable1();
            table1.setModel(table);
        }
    }
}
