package web_patterns.inclassspring2025.persistence;

import java.sql.Connection;

public interface Connector {
    public Connection getConnection();
    public void freeConnection();
}
