package Model;

import DataAccess.AbstractDAO;
import DataAccess.Fields;
import java.time.LocalDateTime;

/**
 * @Author Andreea Onaci
 * This class represents the inserting of the bill according to data from an order
 */
public class LogFile extends AbstractDAO<LogFile> implements Fields {
    public LogFile(int id, LocalDateTime localDateTime, int idProduct, int idClient, int quantity, double priceUnit, double totalPrice) {
        Bill bill = new Bill(id, localDateTime, idProduct, idClient, quantity, priceUnit, totalPrice);
        insert(bill);
    }

    public LogFile() {
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void setId(int id) {

    }

    @Override
    public String getTableName() {
        return "warehouse.log";
    }

    @Override
    public int getQuantity() {
        return 0;
    }
}
