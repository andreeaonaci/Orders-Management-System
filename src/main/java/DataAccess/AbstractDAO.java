package DataAccess;

import Model.Bill;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author: Andreea Onaci
 * @param <T>
 * This class contains the methods for DB connection
 * Insert method is for inserting in a given DB
 * Delete method is for deleting from a given DB
 * Update method is for updating a given DB with a given object at a given ID
 * getIdForRecord method creates a valid ID for the record, different from the existing ones
 * createSelectQuery creates the query for selecting based on a criterion
 * findById is a method that retrieves the object knowing its ID
 * createObjects returns the list of objects based on the provided ResultSet
 * findField returns the object at a given ID for getting its fields
 * findName returns the name knowing the ID
 * findId returns the id of an object
 * writeInTable returns the DefaultTableModel for the GUI tables
 * fillCombo is for filling the Combo Boxes for orders GUI
 * insert is the method which fills the table with Bills
 * writeInTableLog is the method which fills the table for bills
 */
public abstract class AbstractDAO<T extends Fields> extends JFrame {
    private Connection connection;
    private int id = 0;
    private PreparedStatement statement;
    private ResultSet resultSet;
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    private final Class<T> type; //used in getIdForRecord
    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    public T add(T object) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(object.getTableName());
        sb.append(" (");
        Field[] fields = object.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length - 1; i++) {
            sb.append(fields[i].getName());
            if (i < fields.length - 2) {
                sb.append(", ");
            }
        }
        sb.append(") VALUES (");
        for (int i = 0; i < fields.length - 1; i++) {
            sb.append("?");
            if (i < fields.length - 2) {
                sb.append(", ");
            }
        }
        sb.append(")");
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost/postgres", "postgres", "0000");
            statement = connection.prepareStatement(sb.toString(), Statement.RETURN_GENERATED_KEYS);
            int index = 0;
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(object);
                if (index < fields.length - 1)
                    statement.setObject(++index, value);
            }
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Insert failed, no rows affected.");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(2);
                object.setId(id);
            } else {
                throw new SQLException("Insert failed, no ID obtained.");
            }

        } catch (SQLException | IllegalAccessException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw new RuntimeException(e);
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return object;
    }
    public int getIdForRecord(T object) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT ");
            sb.append(" COUNT(*) ");
            sb.append(" FROM ");
            sb.append(object.getTableName());
            connection = DriverManager.getConnection("jdbc:postgresql://localhost/postgres", "postgres", "0000");
            PreparedStatement statement = connection.prepareStatement(sb.toString());
            resultSet = statement.executeQuery();
            resultSet.next();
            int recordCount = resultSet.getInt(1);
            do {
                id = recordCount + 1;
                Field idField = type.getDeclaredField("id");
                idField.setAccessible(true);
                idField.set(object, id);
                String checkIdQuery = "SELECT COUNT(*) FROM " + object.getTableName() + " WHERE id = ?";
                PreparedStatement checkIdStatement = connection.prepareStatement(checkIdQuery);
                checkIdStatement.setInt(1, id);
                ResultSet checkIdResultSet = checkIdStatement.executeQuery();
                checkIdResultSet.next();
                int count = checkIdResultSet.getInt(1);
                if (count == 0) {
                    return id;
                }
                recordCount++;
            } while (true);
        } catch (SQLException | NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    private String createSelectQuery(T object, String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(object.getTableName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }
    protected abstract String getTableName();
    public T findById(T object, int id) {
        String query = createSelectQuery(object, "id");
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost/postgres", "postgres", "0000");
            statement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            return createObjects(object, resultSet).get(0);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private List<T> createObjects(T object, ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor<?>[] ctors = type.getDeclaredConstructors();
        Constructor<?> ctor = null;
        for (int i = 0; i < ctors.length - 1; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            if (!resultSet.isBeforeFirst()) {
                System.out.println("ResultSet is empty");
                throw new IndexOutOfBoundsException("Please insert a valid ID! :(");
            } else {
                ResultSetMetaData metaData = resultSet.getMetaData();
                int numColumns = metaData.getColumnCount();
                List<String> columnNames = new ArrayList<>();
                for (int i = 1; i <= numColumns; i++) {
                    String columnName = metaData.getColumnName(i);
                    columnNames.add(columnName);
                }
                columnNames.remove(columnNames.size() - 1);
                resultSet.next();
                do {
                    ctor.setAccessible(true);
                    T instance = (T) object.getClass().getDeclaredConstructor().newInstance();
                    for (String fieldName : columnNames) {
                        Object value = resultSet.getObject(fieldName);
                        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                        Method setter = propertyDescriptor.getWriteMethod();
                        setter.invoke(instance, value);
                    }
                    list.add(instance);
                } while (resultSet.next());
            }
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException |
                 InvocationTargetException | SQLException | IntrospectionException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public T findField(T object, String fieldName) {
        String query = createSelectQuery(object, "id");
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost/postgres", "postgres", "0000");
            statement = connection.prepareStatement(query);
            Field idField = object.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            int idValue = (int) idField.get(object);
            statement.setInt(1, idValue);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                T resultObject = (T) object.getClass().getDeclaredConstructor().newInstance();
                Field field = object.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                Object value = resultSet.getObject(fieldName);
                field.set(resultObject, value);
                return resultObject;
            } else {
                throw new IllegalArgumentException("No results found");
            }
        } catch (SQLException | ClassNotFoundException | NoSuchFieldException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
    public String findName(T object) throws SQLException {
        String query = createSelectQuery(object, "id");
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost/postgres", "postgres", "0000");
            statement = connection.prepareStatement(query);
            Field idField = object.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            statement.setInt(1, (int) idField.get(object));
            resultSet = statement.executeQuery();
        } catch (SQLException | ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        resultSet.next();
        return resultSet.getString("name");
    }
    public int findId(T object) {
        String query = createSelectQuery(object, "name");
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost/postgres", "postgres", "0000");
            statement = connection.prepareStatement(query,  ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            Field nameField = object.getClass().getDeclaredField("name");
            nameField.setAccessible(true);
            statement.setString(1, (String) nameField.get(object));
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            } else {
                throw new RuntimeException("Object not found in database");
            }
        } catch (SQLException | ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    public T delete(T object) throws SQLException {
        resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost/postgres", "postgres", "0000");

            StringBuilder sb = new StringBuilder();
            sb.append("DELETE FROM ");
            sb.append(object.getTableName());
            sb.append(" WHERE ");
            Field[] fields = object.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                if (fields[i].getName().equals("id")) {
                    sb.append(fields[i].getName());
                    sb.append(" = ?");
                    break;
                }
            }
            statement = connection.prepareStatement(sb.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            Field idField = object.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            statement.setObject(1, idField.get(object));
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Delete failed, no rows affected.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return object;
    }
    public T update(T object) throws SQLException{
        String tableName = object.getTableName();
        StringBuilder query = new StringBuilder();
        query.append("UPDATE " + tableName + " SET ");
        Field[] allFields = object.getClass().getDeclaredFields();
        List<Object> values = new ArrayList<>();
        try {
            for (int i = 0; i < allFields.length; i++) {
                allFields[i].setAccessible(true);
                String fieldName = allFields[i].getName();
                Object value = allFields[i].get(object);
                String fieldType = allFields[i].getType().getSimpleName();
                if (!fieldName.equals("TABLE_NAME") && value != null) {
                    query.append(fieldName);
                    query.append(" = ");
                    if (fieldType.equals("String"))
                        query.append("'" + value + "'");
                    else
                        query.append(value);
                    query.append(", ");
                    values.add(value);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error at updating");
        }
        query.deleteCharAt(query.length() - 2);
        query.append("WHERE ");
        Field firstField = allFields[1];
        firstField.setAccessible(true);
        String fieldName = firstField.getName();
        query.append(fieldName).append(" = ");
        try {
            Object value = firstField.get(object);
            query.append(value);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error at getting id value");
        }
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost/postgres", "postgres", "0000");
            statement = connection.prepareStatement(query.toString());
            statement.executeUpdate();
            connection.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Exception when executing update query");
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return object;
    }
    public DefaultTableModel writeInTable(List<T> list, T object) {
        DefaultTableModel tableModel;
        Field[] fields = object.getClass().getDeclaredFields();
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost/postgres", "postgres", "0000");
            statement = connection.prepareStatement("SELECT * FROM " + object.getTableName());
            resultSet = statement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numColumns = metaData.getColumnCount();
            String[] columnNames = new String[numColumns];
            for (int i = 0; i < numColumns; i++) {
                columnNames[i] = metaData.getColumnName(i + 1);
            }
            while (resultSet.next()) {
                fields = object.getClass().getDeclaredFields();
                T o = (T) object.getClass().getDeclaredConstructor().newInstance();
                for (int columnIndex = 1; columnIndex <= fields.length; columnIndex++) {
                    Field field = fields[columnIndex - 1];
                    field.setAccessible(true);
                    String fieldName = field.getName();
                    if (!fieldName.equals("TABLE_NAME")) {
                        Object value = resultSet.getObject(columnIndex);
                        field.set(o, value);
                    }
                }
                list.add(o);
            }
            Object[][] data = new Object[list.size()][numColumns];
            for (int i = 0; i < list.size(); i++) {
                T obj = list.get(i);
                for (int j = 0; j < numColumns; j++) {
                    Field field = fields[j];
                    field.setAccessible(true);
                    Object value = field.get(obj);
                    data[i][j] = value;
                }
            }
            tableModel = new DefaultTableModel(data, columnNames) {
                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    return String.class;
                }
            };
            DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
            headerRenderer.setFont(headerRenderer.getFont().deriveFont(Font.BOLD));
            JTable table = new JTable(tableModel);
            table.getTableHeader().setDefaultRenderer(headerRenderer);

        } catch (SQLException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return tableModel;
    }
    public void fillCombo(JComboBox<String> comboBox, T object) throws NoSuchMethodException {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost/postgres", "postgres", "0000")) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + object.getTableName());
            ResultSet resultSet = statement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numColumns = metaData.getColumnCount();
            String[] columnNames = new String[numColumns];
            for (int i = 0; i < numColumns; i++) {
                columnNames[i] = metaData.getColumnName(i + 1);
            }
            while (resultSet.next()) {
                T o = (T) object.getClass().getDeclaredConstructor().newInstance(); // Create a new instance for each iteration
                for (String columnName : columnNames) {
                    Field field = null;
                    try {
                        field = object.getClass().getDeclaredField(columnName);
                    } catch (NoSuchFieldException e) {
                    }
                    if (field != null) {
                        field.setAccessible(true);
                        Object value = resultSet.getObject(columnName);
                        field.set(o, value);
                    }
                }
                comboBox.addItem(o.getName());
            }
        } catch (SQLException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
    public void insert(Bill bill) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append("warehouse.log");
        sb.append(" (");
        Field[] fields = bill.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            sb.append(fields[i].getName());
            if (i < fields.length - 1) {
                sb.append(", ");
            }
        }
        sb.append(") VALUES (");
        for (int i = 0; i < fields.length; i++) {
            sb.append("?");
            if (i < fields.length - 1) {
                sb.append(", ");
            }
        }
        sb.append(")");
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost/postgres", "postgres", "0000");
            statement = connection.prepareStatement(sb.toString(), Statement.RETURN_GENERATED_KEYS);
            int index = 0;
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(bill);
                if (index < fields.length)
                    statement.setObject(++index, value);
            }
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Insert failed, no rows affected.");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Insert failed, no ID obtained.");
            }
        } catch (SQLException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    public DefaultTableModel writeInTableLog() {
        DefaultTableModel tableModel;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost/postgres", "postgres", "0000");
            statement = connection.prepareStatement("SELECT * FROM warehouse.log", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numColumns = metaData.getColumnCount();
            String[] columnNames = new String[numColumns];
            for (int i = 0; i < numColumns; i++) {
                columnNames[i] = metaData.getColumnName(i + 1);
            }
            tableModel = new DefaultTableModel(null, columnNames) {
                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    return String.class;
                }
            };
            tableModel.setColumnIdentifiers(columnNames);
            while (resultSet.next()) {
                Object[] rowData = new Object[numColumns];
                for (int i = 0; i < numColumns; i++) {
                    rowData[i] = resultSet.getObject(i + 1);
                }
                tableModel.addRow(rowData);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tableModel;
    }
}
