package DataAccess;

import Model.Product;
import java.sql.SQLException;

/**
 * @Author Andreea Onaci
 * This class implements the CRUD operations from AbstractDAO, which are going to be used in ProductBLL
 */
public class ProductDAO extends AbstractDAO<Product>{
    public Product add(Product product) throws SQLException {
        return super.add(product);
    }
    public ProductDAO() {

    }
    @Override
    protected String getTableName() {
        return null;
    }
    public Product delete(Product product) throws SQLException {
        return super.delete(product);
    }
    public Product update(Product oldProduct) throws SQLException {
        return super.update(oldProduct);
    }
}
