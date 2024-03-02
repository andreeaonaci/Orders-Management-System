package DataAccess;

import Model.Client;
import java.sql.SQLException;

/**
 * @Author Andreea Onaci
 * This class implements the CRUD operations from AbstractDAO, which are going to be used in ClientBLL
 */
public class ClientDAO extends AbstractDAO<Client>{
    @Override
    protected String getTableName() {
        return null;
    }
    public Client delete(Client client) throws SQLException {
        return super.delete(client);
    }
    public Client update(Client oldClient) throws SQLException {
        return super.update(oldClient);
    }
    public Client add(Client client) throws SQLException {
        return super.add(client);
    }
    public ClientDAO() {

    }
}
