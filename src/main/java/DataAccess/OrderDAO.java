package DataAccess;

import Model.Orders;
import java.sql.SQLException;

/**
 * @Author Andreea Onaci
 * This class implements the CRUD operations from AbstractDAO, which are going to be used in OrderBLL
 */
public class OrderDAO extends AbstractDAO<Orders> {
    public OrderDAO() {
    }
    @Override
    protected String getTableName() {
        return null;
    }
    public Orders add(Orders order) throws SQLException {
        return super.add(order);
    }
}
