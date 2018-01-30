package by.troyan.web.database;

import java.util.Properties;
import java.util.ResourceBundle;

/**
 * TotalizatorPropertiesReader. Class that reads info for database initialization from file
 * using static method getTotalizatorProperties().
 */

public class TotalizatorPropertiesReader {

    private static final String FILENAME = "totalizator";

    /**
     * Used to receive information from TOTALIZATOR_PROPERTIES file
     * @return Properties object
     */
    public static Properties getTotalizatorProperties(){
        Properties result = new Properties();
        ResourceBundle resource = ResourceBundle.getBundle(FILENAME);
        result.setProperty("url", resource.getString("url"));
        result.setProperty("username", resource.getString("username"));
        result.setProperty("password", resource.getString("password"));
        result.setProperty("poolSize", resource.getString("poolSize"));

        result.setProperty("enterpriseCardNumber", resource.getString("enterpriseCardNumber"));
        result.setProperty("enterpriseCardValidityDate", resource.getString("enterpriseCardValidityDate"));
        result.setProperty("standardCreditAmount", resource.getString("standardCreditAmount"));
        result.setProperty("enterpriseCardCode", resource.getString("enterpriseCardCode"));

        return result;
    }
}
