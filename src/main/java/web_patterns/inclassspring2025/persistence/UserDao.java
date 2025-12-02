package web_patterns.inclassspring2025.persistence;

import java.sql.SQLException;

public interface UserDao {
    public boolean register(String username, String password) throws SQLException;
    public boolean login(String username, String password) throws SQLException;
    public void closeConnection();
}
