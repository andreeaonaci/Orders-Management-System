package DataAccess;

import java.sql.SQLException;

public interface Fields {
    int getId() throws SQLException;
    String getName();
    void setName(String name);
    void setId(int id);
    String getTableName();
    int getQuantity();
}
