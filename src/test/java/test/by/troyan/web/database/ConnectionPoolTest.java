package test.by.troyan.web.database;


import org.testng.Assert;
import org.testng.annotations.Test;
import by.troyan.web.database.ConnectionPool;

import java.sql.Connection;


public class ConnectionPoolTest {
    @Test
    public void ConnectionPoolTest(){
        ConnectionPool pool = ConnectionPool.getConnectionPool();
        Connection c = pool.getConnection();
        pool.returnConnectionToPool(c);
        Assert.assertEquals(true, c != null);
    }
}
