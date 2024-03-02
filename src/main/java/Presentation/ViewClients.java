package Presentation;

import javax.swing.*;
import java.awt.event.ActionListener;
/**
 * @Author Andreea Onaci
 * This class represents the GUI for Clients
 */
public class ViewClients extends JFrame {
    private JPanel basePanel;
    private JTextField deleteName;
    private JTextField addName;
    private JTextField withName;
    private JTextField updateName;
    private JTable table;
    private JButton addButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton backToMenuButton;

    public ViewClients() {
        setDimension(1000, 500);
    }

    public void setDimension(int w, int h) {
        add(basePanel);
        setBounds(300, 200, w, h);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void addAddButton (ActionListener actionListener) {
        this.addButton.addActionListener(actionListener);
    }

    public void addDeleteButton (ActionListener actionListener) {
        this.deleteButton.addActionListener(actionListener);
    }

    public JTextField getDeleteName() {
        return deleteName;
    }

    public void setDeleteName(JTextField deleteName) {
        this.deleteName = deleteName;
    }

    public String getAddName() {
        return addName.getText();
    }

    public void setAddName(JTextField addName) {
        this.addName = addName;
    }

    public JTextField getWithName() {
        return withName;
    }

    public void setWithName(JTextField withName) {
        this.withName = withName;
    }
    public void addBackToMenuButton(ActionListener actionListener) {
        this.backToMenuButton.addActionListener(actionListener);
    }

    public JTextField getUpdateName() {
        return updateName;
    }

    public void setUpdateName(JTextField updateName) {
        this.updateName = updateName;
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public void addUpdateButton (ActionListener actionListener) {
        this.updateButton.addActionListener(actionListener);
    }

    public void showMessage (String message) {
        JOptionPane.showMessageDialog(this, message, "Message", JOptionPane.INFORMATION_MESSAGE);
    }
}
