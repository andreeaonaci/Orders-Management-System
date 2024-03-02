package BusinessLogic;

import BusinessLogic.Validators.PriceValidators;
import BusinessLogic.Validators.QuantityValidators;
import BusinessLogic.Validators.Validators;
import DataAccess.ProductDAO;
import Model.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *  Author: Andreea Onaci
 *  This class calls the methods from DAO, but only after validating the input
 */
public class ProductBLL {
    private List<Validators<Product>> validators;
    private ProductDAO productDAO;

    public ProductBLL() {
        validators = new ArrayList<>();
        validators.add(new QuantityValidators());
        validators.add(new PriceValidators());
        productDAO = new ProductDAO();
    }

    public Product findById(int id) {
        Product product = new Product();
        product = productDAO.findById(product, id);
        if (product == null) {
            throw new NoSuchElementException("The product with id =" + id + " was not found!");
        }
        return product;
    }

    public void add(Product product) throws SQLException {
        for (Validators<Product> v : validators) {
            v.validate(product);
        }
        productDAO.add(product);
    }

    public Product delete(Product product) throws SQLException {
        return productDAO.delete(product);
    }

    public Product update(Product product) throws SQLException {
        for (Validators<Product> v : validators) {
            v.validate(product);
        }
        return productDAO.update(product);
    }
}
