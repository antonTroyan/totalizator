package by.troyan.web.dao.implementation;

import by.troyan.web.dao.OperationDAO;
import by.troyan.web.dao.exception.DAOException;
import by.troyan.web.database.ConnectionPool;
import by.troyan.web.entity.Operation;
import by.troyan.web.exception.OperationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;

/**
 * Operation DAO implementation. Class supporting all money operations.
 */

public class OperationDAOImpl implements OperationDAO {
    private final static Logger LOG = LogManager.getLogger(OperationDAOImpl.class);
    public static final String INPUT = "INPUT";
    public static final String OUTPUT = "OUTPUT";
    private static final String SQL_FOR_ADD_OPERATION = "INSERT INTO `moneyoperation`(`user_id`, `operation_type`, `amount`, `card_number`, `validity_date`) " +
            "VALUES(?, ?, ?, ?, ?);";


    private static final OperationDAOImpl instance = new OperationDAOImpl();
    private final ConnectionPool pool = ConnectionPool.getConnectionPool();

    private OperationDAOImpl(){}

    public static OperationDAOImpl getInstance(){
        return instance;
    }

    @Override
    public Operation addOperation(Operation operation) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = pool.getConnection();
            connection.setAutoCommit(false);
            Savepoint savepoint = connection.setSavepoint();
            try {
                statement = connection.prepareStatement(SQL_FOR_ADD_OPERATION);
                statement.setInt(1, operation.getUserId());
                statement.setString(2, operation.getOperationType());
                statement.setBigDecimal(3, operation.getAmount());
                statement.setString(4, operation.getCardNumber());
                statement.setString(5, operation.getValidityDate());
                statement.executeUpdate();
            } catch (SQLException exc) {
                connection.rollback(savepoint);
                LOG.error(exc);
                throw new DAOException(exc);
            } finally {
                connection.setAutoCommit(true);
                if(statement != null){
                    statement.close();
                }
            }
        } catch (SQLException exc){
            LOG.error(exc);
            throw new DAOException(exc);
        } finally {
            if(connection != null){
                pool.returnConnectionToPool(connection);
            }
        }
        return operation;
    }

}
