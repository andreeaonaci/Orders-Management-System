package Presentation;

import javax.swing.*;
import java.awt.event.ActionListener;
/**
 * @Author Andreea Onaci
 * This class represents the GUI for Orders
 */
public class ViewOrders extends JFrame{
    private JTextField quantityField;
    private JTable table;
    private JComboBox<String> product;
    private JComboBox<String> client;
    private JLabel productStock;
    private JPanel basePanel;
    private JButton addOrderButton;
    private JButton backToMenuButton;
    private JTable table1;
    private JButton viewBills;

    public void setDimension(int w, int h) {
        add(basePanel);
        setBounds(300, 200, w, h);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public ViewOrders() {
        setDimension(1000, 500);
    }

    public String getQuantityField() {
        return quantityField.getText();
    }

    public void setQuantityField(String quantityField) {
        this.quantityField.setText(quantityField);
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public JComboBox<String> getProduct() {
        return product;
    }
    public void addBackToMenuButton(ActionListener actionListener) {
        this.backToMenuButton.addActionListener(actionListener);
    }

    public void setProduct(JComboBox<String> product) {
        this.product = product;
    }

    public JComboBox<String> getClient() {
        return client;
    }

    public void setClient(JComboBox<String> client) {
        this.client = client;
    }

    public void setProductStock(String productStock) {
        this.productStock.setText(productStock);
    }

    public JTable getTable1() {
        return table1;
    }

    public void addOrderButton (ActionListener actionListener) {
        this.addOrderButton.addActionListener(actionListener);
    }
    public void showMessage (String message) {
        JOptionPane.showMessageDialog(this, message, "Message", JOptionPane.INFORMATION_MESSAGE);
    }
    public void addViewBills(ActionListener actionListener) {
        this.viewBills.addActionListener(actionListener);
    }
}
