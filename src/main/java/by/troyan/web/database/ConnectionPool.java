package by.troyan.web.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ConnectionPool. Contains list of connections and maintain it.
 * Several methods allow to receive connection  form list, use it and return back.
 */

public class ConnectionPool {
    private final static Logger LOG = LogManager.getLogger(ConnectionPool.class);
    private static final Properties properties = DBPropertiesReader.getDBProperties();
    private static final ConnectionPool connectionPool = new ConnectionPool(properties);

    private ArrayBlockingQueue<Connection> connections;
    private ReentrantLock lockForReturnConnection;

    public static ConnectionPool getConnectionPool(){
        return connectionPool;
    }

    private ConnectionPool(Properties properties){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException exc){
            LOG.error(exc);
        }
        int poolSize = Integer.parseInt(properties.getProperty("poolSize"));
        String url = properties.getProperty("url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        connections = new ArrayBlockingQueue<>(poolSize);
        lockForReturnConnection = new ReentrantLock();
        Connection connection;
        for(int i = 0; i < poolSize; i++){
            try {
                connection = DriverManager.getConnection(url, username, password);
                connections.offer(connection);
            }
            catch (SQLException exc){
                LOG.error(exc);
            }
        }
    }

    public Connection getConnection(){
        Connection connection;
        try {
            connection = connections.take();
        }
        catch(InterruptedException exc){
            connection = null;
            LOG.error(exc);
        }
        return connection;
    }

    public void returnConnectionToPool(Connection c){
        lockForReturnConnection.lock();
        if(!connections.contains(c)) {
            connections.offer(c);
        }
        lockForReturnConnection.unlock();
    }

    @Override
    protected void finalize() throws Throwable {
        for(Connection c : connections){
            c.close();
        }
    }
}
