package Presentation;

import javax.swing.*;

/**
 * @Author Andreea Onaci
 * This class represents the GUI for Bills, composed by a table with data of bills
 */
public class ViewBills extends JFrame{
    private JTable table1;
    private JPanel basePanel;

    public void setDimension(int w, int h) {
        add(basePanel);
        setBounds(300, 200, w, h);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
    }
    public ViewBills() {
        setDimension(600, 500);
    }

    public JTable getTable1() {
        return table1;
    }
}
