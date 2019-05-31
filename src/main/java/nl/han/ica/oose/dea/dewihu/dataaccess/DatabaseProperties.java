package nl.han.ica.oose.dea.dewihu.dataaccess;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

class DatabaseProperties {
    private Properties properties;

    public DatabaseProperties() {
        properties = new Properties();
        String databaseResource = "/database.properties";

        try {
            properties.load(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(databaseResource)));
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            String error = "Can't access property file database.properties";

            logger.log(Level.SEVERE, error, e);
        }
    }

    String driver() {
        return properties.getProperty("driver");
    }

    String connectionString() {
        return properties.getProperty("connectionString");
    }

    String connectionUser() {
        return properties.getProperty("user");
    }

    String connectionPassword() {
        return properties.getProperty("password");
    }
}
