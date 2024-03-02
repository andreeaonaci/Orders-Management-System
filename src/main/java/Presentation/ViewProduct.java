package Presentation;

import javax.swing.*;
import java.awt.event.ActionListener;
/**
 * @Author Andreea Onaci
 * This class represents the GUI for Products
 */
public class ViewProduct extends JFrame {
    private JPanel basePanel;
    private JTable table;
    private JTextField addName;
    private JTextField deleteName;
    private JTextField updateName;
    private JTextField withName;
    private JButton addButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JTextField quantity;
    private JTextField price;
    private JTextField withQuantity;
    private JTextField withPrice;
    private JButton backToMenuButton;

    public void setDimension(int w, int h) {
        add(basePanel);
        setBounds(300, 200, w, h);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public ViewProduct() {
        setDimension(1000, 500);
    }

    public void addAddButton (ActionListener actionListener) {
        this.addButton.addActionListener(actionListener);
    }

    public String getPrice() {
        return price.getText();
    }

    public JTextField getWithQuantity() {
        return withQuantity;
    }

    public JTextField getWithPrice() {
        return withPrice;
    }

    public void addDeleteButton (ActionListener actionListener) {
        this.deleteButton.addActionListener(actionListener);
    }

    public void addBackToMenuButton(ActionListener actionListener) {
        this.backToMenuButton.addActionListener(actionListener);
    }

    public String getQuantity() {
        return quantity.getText();
    }

    public void addUpdateButton (ActionListener actionListener) {
        this.updateButton.addActionListener(actionListener);
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public JTextField getAddName() {
        return addName;
    }

    public void setAddName(JTextField addName) {
        this.addName = addName;
    }

    public JTextField getDeleteName() {
        return deleteName;
    }

    public void setDeleteName(JTextField deleteName) {
        this.deleteName = deleteName;
    }

    public JTextField getUpdateName() {
        return updateName;
    }

    public void setUpdateName(JTextField updateName) {
        this.updateName = updateName;
    }

    public JTextField getWithName() {
        return withName;
    }

    public void setWithName(JTextField withName) {
        this.withName = withName;
    }

    public void showMessage (String message) {
        JOptionPane.showMessageDialog(this, message, "Message", JOptionPane.INFORMATION_MESSAGE);
    }

}
