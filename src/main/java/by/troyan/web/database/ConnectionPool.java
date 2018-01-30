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
 * Several methods allow to receive connection from list, use it and return back.
 * All connections are organised in ArrayBlockingQueue.
 */

public class ConnectionPool {
    private final static Logger LOG = LogManager.getLogger(ConnectionPool.class);
    private static final Properties DB_PROPERTIES = TotalizatorPropertiesReader.getTotalizatorProperties();
    private static final ConnectionPool CONNECTION_POOL = new ConnectionPool(DB_PROPERTIES);

    private ArrayBlockingQueue<Connection> connections;
    private ReentrantLock lockForReturnConnection;

    public static ConnectionPool getConnectionPool(){
        return CONNECTION_POOL;
    }


    /**
     * Constructor. Used to initialise pool of connections.
     *
     * @param  properties
     *         need DB_PROPERTIES that have string poolSize with the value of
     *         needed connections for pool
     */
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


    /**
     * Used to receive connection from connection pool. If there is no free connections,
     * query should wait.
     *
     * @return Connection
     *
     */
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


    /**
     * Used to return connection to the pool.
     *
     * @param  connection
     *         connection that should be returned
     *
     */
    public void returnConnectionToPool(Connection connection){
        lockForReturnConnection.lock();
        if(!connections.contains(connection)) {
            connections.offer(connection);
        }
        lockForReturnConnection.unlock();
    }

    /**
     * In case of wrong closing before calling returnConnectionToPool(Connection connection)
     * method. When the GC will find unused object of this class it calls this method
     * to close connections before destroying the object
     */
    @Override
    protected void finalize() throws Throwable {
        for(Connection connection : connections){
            connection.close();
        }
    }
}
