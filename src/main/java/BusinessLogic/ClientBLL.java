package BusinessLogic;

import BusinessLogic.Validators.NameValidators;
import BusinessLogic.Validators.Validators;
import DataAccess.ClientDAO;
import Model.Client;

import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *  Author: Andreea Onaci
 *  This class calls the methods from DAO, but only after validating the input
 */
public class ClientBLL {
    private List<Validators<Client>> validators;
    private ClientDAO clientDAO;

    public ClientBLL() {
        validators = new ArrayList<>();
        validators.add(new NameValidators());
        clientDAO = new ClientDAO();
    }

    public Client findById(int id) {
        Client client = new Client();
        client = clientDAO.findById(client, id);
        if (client == null) {
            throw new NoSuchElementException("The client with id =" + id + " was not found!");
        }
        return client;
    }
    public void add(Client client) throws Exception {
        for (Validators<Client> v : validators) {
            v.validate(client);
        }
        clientDAO.add(client);
    }

    public Client delete(Client client) throws SQLException {
        return clientDAO.delete(client);
    }

    public Client update(Client client) throws SQLException {
        for (Validators<Client> v : validators) {
            v.validate(client);
        }
        return clientDAO.update(client);
    }

    public DefaultTableModel writeInTable(List<Client> list, Client client) {
        return clientDAO.writeInTable(list, client);
    }
}

