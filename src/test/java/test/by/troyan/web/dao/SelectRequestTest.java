package test.by.troyan.web.dao;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import by.troyan.web.database.ConnectionPool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class SelectRequestTest {
    private final static Logger LOG = LogManager.getLogger("SelectRequestTest");

    @Test
    public void selectRequest(){
        ResultSet resultSet = null;
        ConnectionPool pool = ConnectionPool.getConnectionPool();
        Connection c = pool.getConnection();
        try {
            Statement statement = c.createStatement();
            statement.execute("SELECT * FROM `event`");
            resultSet = statement.getResultSet();
            while(resultSet.next()){
                LOG.info(resultSet.getString("event_name"));
            }
        }
        catch (SQLException exc){
            LOG.error(exc);
        }
        Assert.assertEquals(true, resultSet != null);
    }
}
