package Presentation;

import BusinessLogic.ClientBLL;
import DataAccess.ClientDAO;
import Model.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

/**
 * @Author Andreea Onaci
 * This class is creating the relation between the GUI and the Client operations
 */
public class ControllerClient {
    private final ViewClients viewClients;
    private final Client client;
    private final ClientBLL clientBLL;
    public ControllerClient(ViewClients viewClients) {
        this.viewClients = viewClients;
        client = new Client(null, 0);
        clientBLL = new ClientBLL();
        ArrayList<Client> listClients = new ArrayList<>();
        DefaultTableModel table = clientBLL.writeInTable(listClients, client);
        JTable table1 = viewClients.getTable();
        table1.setModel(table);
        viewClients.addAddButton(new addButton());
        viewClients.addDeleteButton(new deleteButton());
        viewClients.addUpdateButton(new updateButton());
        viewClients.addBackToMenuButton(new backToMenu());
    }

    class addButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Client client = new Client(viewClients.getAddName(), 0);
                client.setId(client.getIdForRecord(ControllerClient.this.client));
                if (viewClients.getAddName().equals(""))
                    viewClients.showMessage("Invalid Data!");
                else {
                    clientBLL.add(client);
                    ArrayList<Client> listClients = new ArrayList<>();
                    DefaultTableModel table = clientBLL.writeInTable(listClients, client);
                    JTable table1 = viewClients.getTable();
                    table1.setModel(table);
                    viewClients.showMessage("Client added successfully!");
                }
            } catch (Exception exception) {
                viewClients.showMessage("Something went wrong! :(");
                exception.printStackTrace();
            }
        }
    }

    class deleteButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                clientBLL.findById(Integer.parseInt(viewClients.getDeleteName().getText()));
                Client client1 = new Client(Integer.parseInt(viewClients.getDeleteName().getText()));
                if (viewClients.getDeleteName().getText().equals(""))
                    viewClients.showMessage("Invalid Data!");
                else {
                    clientBLL.delete(client1);
                    ArrayList<Client> listClients = new ArrayList<>();
                    DefaultTableModel table = clientBLL.writeInTable(listClients, client);
                    JTable table1 = viewClients.getTable();
                    table1.setModel(table);
                    viewClients.showMessage("Client deleted succesfully!");
                }
            } catch (Exception exception) {
                viewClients.showMessage("Something went wrong! :(");
                exception.printStackTrace();
            }
        }
    }

    class updateButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Client client = new Client(Integer.parseInt(viewClients.getUpdateName().getText()));
                if (viewClients.getUpdateName().getText().equals(""))
                    viewClients.showMessage("Invalid Data!");
                else {
                    int idClient = Integer.parseInt(viewClients.getUpdateName().getText());
                    clientBLL.findById(idClient);
                    String name;
                    if (viewClients.getWithName().getText().equals("")) {
                        name = client.findName(client);
                    } else
                        name = viewClients.getWithName().getText();
                    Client client1 = new Client(name, idClient);
                    clientBLL.update(client1);
                    ArrayList<Client> listClients = new ArrayList<>();
                    DefaultTableModel table = clientBLL.writeInTable(listClients, client);
                    JTable table1 = viewClients.getTable();
                    table1.setModel(table);
                    viewClients.showMessage("Client updated succesfully!");
                }
            } catch (Exception exception) {
                viewClients.showMessage("Something went wrong! :(");
                exception.printStackTrace();
            }
        }
    }

    class backToMenu implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            viewClients.setVisible(false);
            ViewMenu viewMenu = new ViewMenu();
            viewMenu.setVisible(true);
            new ControllerMain(viewMenu);
        }
    }
}
