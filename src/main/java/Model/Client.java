package Model;

import DataAccess.AbstractDAO;
import DataAccess.Fields;

/**
 * @Author Andreea Onaci
 * This class contains fields and their getters and setters for the Client, in fact, the data corresponding to a Client
 */

public class Client extends AbstractDAO<Client> implements Fields {
    private String name;
    private int id;
    public Client(String name, int id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return getIdForRecord(this);
    }

    private static final String TABLE_NAME = "warehouse.client";

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public int getQuantity() {
        return 0;
    }

    public Client(String name) {
        this.name = name;
    }

    public Client(int id) {
        this.id = id;
    }

    public Client() {
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
