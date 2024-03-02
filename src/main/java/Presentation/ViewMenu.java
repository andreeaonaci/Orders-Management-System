package Presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
/**
 * @Author Andreea Onaci
 * This class represents the GUI for Menu, in which can be selected the Client, Order or Product button
 */
public class ViewMenu extends JFrame {
    private JPanel basePanel;
    private JButton ordersButton;
    private JButton clientsButton;
    private JButton productButton;

    public void setDimension(int w, int h) {
        add(basePanel);
        setBounds(300, 200, w, h);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public ViewMenu() {
        setDimension(600, 300);
    }

    public void addClientsButton (ActionListener actionListener) {
        this.clientsButton.addActionListener(actionListener);
    }

    public void addOrdersButton (ActionListener actionListener) {
        this.ordersButton.addActionListener(actionListener);
    }

    public void addProductButton (ActionListener actionListener) {
        this.productButton.addActionListener(actionListener);
    }
    public void showMessage (String message) {
        JOptionPane.showMessageDialog(this, message, "Message", JOptionPane.INFORMATION_MESSAGE);
    }
}
