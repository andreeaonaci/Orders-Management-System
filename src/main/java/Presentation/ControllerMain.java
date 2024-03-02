package Presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * @Author Andreea Onaci
 * This class is creating the relation between the GUI and the selected interface
 */

public class ControllerMain {
    private ViewMenu viewMenu;
    public ControllerMain(ViewMenu viewMenu) {
        this.viewMenu = viewMenu;
        viewMenu.addClientsButton(new clientsButton());
        viewMenu.addOrdersButton(new ordersButton());
        viewMenu.addProductButton(new productButton());
    }

    class clientsButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ViewClients viewClients = new ViewClients();
            new ControllerClient(viewClients);
            viewClients.setVisible(true);
            viewMenu.setVisible(false);
        }
    }

    class ordersButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ViewOrders viewOrders = new ViewOrders();
            viewOrders.setVisible(true);
            viewMenu.setVisible(false);
            try {
                new ControllerOrder(viewOrders);
            } catch (SQLException | NoSuchMethodException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    class productButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ViewProduct viewProduct = new ViewProduct();
            viewProduct.setVisible(true);
            viewMenu.setVisible(false);
            try {
                new ControllerProduct(viewProduct);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
