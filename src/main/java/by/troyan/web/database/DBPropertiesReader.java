package by.troyan.web.database;

import java.util.Properties;
import java.util.ResourceBundle;

/**
 * DBPropertiesReader. Class that reads info for database initialization from file.
 */

public class DBPropertiesReader {
    public static Properties getDBProperties(){
        Properties result = new Properties();
        ResourceBundle resource = ResourceBundle.getBundle("db");
        result.setProperty("url", resource.getString("url"));
        result.setProperty("username", resource.getString("username"));
        result.setProperty("password", resource.getString("password"));
        result.setProperty("poolSize", resource.getString("poolSize"));
        return result;
    }
}
