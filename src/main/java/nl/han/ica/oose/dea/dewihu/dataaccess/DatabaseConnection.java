package nl.han.ica.oose.dea.dewihu.dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

abstract class DatabaseConnection {

    private Logger logger = Logger.getLogger(getClass().getName());
    private DatabaseProperties databaseProperties;

    DatabaseConnection(DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
        tryLoadJdbcDriver(databaseProperties);
    }

    void executeStatement(String sql) {
        try {
            Connection conn = DriverManager.getConnection(getDatabaseProperties().connectionString(), getDatabaseProperties().connectionUser(),
                    getDatabaseProperties().connectionPassword());
            PreparedStatement st = conn.prepareStatement(sql);
            if (st.execute()) {
                st.close();
                conn.close();
            }
        } catch (SQLException e) {
            String error = "Error communicating with database " + getDatabaseProperties().connectionString();

            getLogger().log(Level.SEVERE, error, e);
        }
    }

    private void tryLoadJdbcDriver(DatabaseProperties databaseProperties) {
        String errorMessage = "Can't load JDBC Driver " + databaseProperties.driver();

        try {
            Class.forName(databaseProperties.driver());
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, errorMessage, e);
        }
    }

    Logger getLogger() {
        return logger;
    }

    DatabaseProperties getDatabaseProperties() {
        return databaseProperties;
    }

}

