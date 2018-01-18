package by.troyan.web.dao.implementation;

import by.troyan.web.dao.UserDAO;
import by.troyan.web.dao.exception.DAOException;
import by.troyan.web.database.ConnectionPool;
import by.troyan.web.entity.User;
import by.troyan.web.exception.UserException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User DAO implementation. Class for working with users.
 */

public class UserDAOImpl implements UserDAO {
    private final static Logger LOG = LogManager.getLogger(UserDAOImpl.class);
    private static final UserDAOImpl instance = new UserDAOImpl();
    private final ConnectionPool pool = ConnectionPool.getConnectionPool();
    private static final String SQL_FOR_GET_ALL_USERS = "SELECT * FROM `user`";
    private static final String SQL_FOR_CREATE_USER = "INSERT INTO `user`(`login`, `pass_hash`, `email`) VALUES (?, ?, ?);";
    private static final String SQL_FOR_GET_USER_BY_LOGIN = "SELECT `login`, `pass_hash` , `role`, `banned` " +
            "FROM `user` " +
            "WHERE `login` = ?";
    private static final String SQL_FOR_GET_FULL_USER_INFORMATION_BY_LOGIN = "SELECT `login` , `role`, `balance`, " +
            "`user_id`, `banned`, `email` " +
            "FROM `user` " +
            "WHERE `login` = ?;";
    private static final String SQL_FOR_GET_USER_ID_BY_LOGIN = "SELECT `user_id` " +
            "FROM `user` " +
            "WHERE `login` = ?;";
    private static final String SQL_FOR_FILL_UP_BALANCE_FOR_USER = "UPDATE `user` " +
            "SET `balance` = `balance` + ? " +
            "WHERE `user_id` = ?;";
    private static final String SQL_FOR_WITHDRAW_MONEY_FROM_USER = "UPDATE `user` " +
            "SET `balance` = `balance` - ? " +
            "WHERE `user_id` = ?;";
    private static final String SQL_FOR_HAVE_MONEY = "SELECT `balance` >= ? AS `flag` " +
            "FROM `user` " +
            "WHERE `user_id` = ?;";
    private static final String SQL_FOR_BAN_USERS = "UPDATE `user` " +
            "SET `banned` = true " +
            "WHERE `user_id` in (?);";
    private static final String SQL_FOR_UNBAN_USERS = "UPDATE `user` " +
            "SET `banned` = false " +
            "WHERE `user_id` in (?);";
    private static final String SQL_FOR_CHANGE_ROLE_FOR_USERS = "UPDATE `user` " +
            "SET `role` = ? " +
            "WHERE `user_id` in (&);";
    private static final String SQL_FOR_DELETE_USERS = "DELETE FROM `user` " +
            " WHERE `user_id`=(?)";
    private static final String SQL_FOR_MARK_USER_AS_DEBTOR = "UPDATE `user` " +
            "SET `is_debtor` = true " +
            "WHERE `user_id` = (?);";
    private static final String SQL_FOR_REMOVE_DEBTOR_MARK = "UPDATE `user` " +
            "SET `is_debtor` = false " +
            "WHERE `user_id` = (?);";
    private static final String SQL_FOR_CHECK_IS_DEBTOR = "SELECT `is_debtor`" +
            "FROM `user` " +
            "WHERE `user_id` = ?;";


    private UserDAOImpl() {}

    public static UserDAOImpl getInstance(){
        return instance;
    }

    public List<User> getAllUsers() throws DAOException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<User> result = new ArrayList<User>();
        try {
            connection = pool.getConnection();
            statement = connection.createStatement();
            statement.execute(SQL_FOR_GET_ALL_USERS);
            resultSet = statement.getResultSet();
            User user;
            while(resultSet.next()){
                user = new User();
                user.setUserId(resultSet.getInt("user_id"));
                user.setLogin(resultSet.getString("login"));
                user.setPassHash(resultSet.getString("pass_hash"));
                user.setEmail(resultSet.getString("email"));
                user.setRole(User.Role.valueOf(resultSet.getString("role").toUpperCase()));
                user.setBalance(resultSet.getBigDecimal("balance"));
                user.setBanned(resultSet.getBoolean("banned"));

                result.add(user);
            }
        }
        catch (SQLException exc){
            LOG.error(exc);
        }
        finally {
            pool.returnConnectionToPool(connection);
            try {
                resultSet.close();
            }
            catch (SQLException exc){
                LOG.error(exc);
            }
            try {
                statement.close();
            }
            catch (SQLException exc){
                LOG.error(exc);
            }
        }
        return result;
    }

    @Override
    public User createUser(User user) throws DAOException, UserException {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = pool.getConnection();
            connection.setAutoCommit(false);
            Savepoint savepoint = connection.setSavepoint();
            try {
                statement = connection.prepareStatement(SQL_FOR_CREATE_USER);
                statement.setString(1, user.getLogin());
                statement.setString(2, user.getPassHash());
                statement.setString(3, user.getEmail());
                statement.executeUpdate();
            } catch (SQLException exc) {
                connection.rollback(savepoint);
                LOG.error(exc);
                if (exc.getErrorCode() == 1062) {
                    throw new UserException("err.user-exists", user);
                } else {
                    throw new DAOException(exc);
                }
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
        return user;
    }

    @Override
    public User getUserByLogin(String login) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;
        try{
            connection = pool.getConnection();
            try{
                statement = connection.prepareStatement(SQL_FOR_GET_USER_BY_LOGIN);
                statement.setString(1, login);
                resultSet = statement.executeQuery();
                try{
                    while (resultSet.next()){
                        user = new User();
                        user.setPassHash(resultSet.getString("pass_hash"));
                        user.setLogin(resultSet.getString("login"));
                        user.setRole(User.Role.valueOf(resultSet.getString("role").toUpperCase()));
                        user.setBanned(resultSet.getBoolean("banned"));
                    }
                } catch (SQLException exc){
                    LOG.error(exc);
                    throw new DAOException(exc);
                } finally {
                    if(resultSet != null){
                        resultSet.close();
                    }
                }
            } catch (SQLException exc){
                LOG.error(exc);
                throw new DAOException(exc);
            } finally {
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
        return user;
    }

    @Override
    public User getFullUserInformationByLogin(String login) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;
        try{
            connection = pool.getConnection();
            try{
                statement = connection.prepareStatement(SQL_FOR_GET_FULL_USER_INFORMATION_BY_LOGIN);
                statement.setString(1, login);
                resultSet = statement.executeQuery();
                try{
                    while (resultSet.next()){
                        user = new User();
                        user.setLogin(resultSet.getString("login"));
                        user.setBalance(resultSet.getBigDecimal("balance"));
                        user.setUserId(resultSet.getInt("user_id"));
                        user.setEmail(resultSet.getString("email"));
                        user.setBanned(resultSet.getBoolean("banned"));
                        user.setRole(User.Role.valueOf(resultSet.getString("role").toUpperCase()));
                    }
                } catch (SQLException exc){
                    LOG.error(exc);
                    throw new DAOException(exc);
                } finally {
                    if(resultSet != null){
                        resultSet.close();
                    }
                }
            } catch (SQLException exc){
                LOG.error(exc);
                throw new DAOException(exc);
            } finally {
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
        return user;
    }

    @Override
    public int getUserIdByLogin(String login) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int userId = 0;
        try{
            connection = pool.getConnection();
            try{
                statement = connection.prepareStatement(SQL_FOR_GET_USER_ID_BY_LOGIN);
                statement.setString(1, login);
                resultSet = statement.executeQuery();
                try{
                    while (resultSet.next()){
                        userId = resultSet.getInt("user_id");
                    }
                } catch (SQLException exc){
                    LOG.error(exc);
                    throw new DAOException(exc);
                } finally {
                    if(resultSet != null){
                        resultSet.close();
                    }
                }
            } catch (SQLException exc){
                LOG.error(exc);
                throw new DAOException(exc);
            } finally {
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
        return userId;
    }

    @Override
    public void fillUpBalanceForUser(int userId, BigDecimal money) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = pool.getConnection();
            connection.setAutoCommit(false);
            Savepoint savepoint = connection.setSavepoint();
            try {
                statement = connection.prepareStatement(SQL_FOR_FILL_UP_BALANCE_FOR_USER);
                statement.setBigDecimal(1, money);
                statement.setInt(2, userId);
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
    }

    @Override
    public void withdrawMoneyFromUser(int userId, BigDecimal money) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = pool.getConnection();
            connection.setAutoCommit(false);
            Savepoint savepoint = connection.setSavepoint();
            try {
                statement = connection.prepareStatement(SQL_FOR_WITHDRAW_MONEY_FROM_USER);
                statement.setBigDecimal(1, money);
                statement.setInt(2, userId);
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
    }

    private String insertListToQuery(String sql, List<Integer> idList){
        StringBuilder sb = new StringBuilder();
        for(Integer i : idList){
            sb.append("'" + i + "',");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        return sql.replace("?", sb.toString());
    }

    @Override
    public boolean haveMoney(int userId, BigDecimal money) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean result = false;
        try{
            connection = pool.getConnection();
            try{
                statement = connection.prepareStatement(SQL_FOR_HAVE_MONEY);
                statement.setBigDecimal(1, money);
                statement.setInt(2, userId);
                statement.execute();
                try {
                    resultSet = statement.getResultSet();
                    while(resultSet.next()){
                        result = resultSet.getBoolean("flag");
                    }
                } catch (SQLException exc){
                    LOG.error(exc);
                    throw new DAOException(exc);
                } finally {
                    if(resultSet != null){
                        resultSet.close();
                    }
                }
            } catch (SQLException exc){
                LOG.error(exc);
                throw new DAOException(exc);
            } finally {
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
        return result;
    }

    @Override
    public void banUsers(List<Integer> idList) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = pool.getConnection();
            try {
                statement = connection.prepareStatement(insertListToQuery(SQL_FOR_BAN_USERS, idList));
                statement.executeUpdate();
            } catch (SQLException exc) {
                LOG.error(exc);
                throw new DAOException(exc);
            } finally {
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
    }

    @Override
    public void unbanUsers(List<Integer> idList) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = pool.getConnection();
            try {

                statement = connection.prepareStatement(insertListToQuery(SQL_FOR_UNBAN_USERS, idList));
                statement.executeUpdate();
            } catch (SQLException exc) {
                LOG.error(exc);
                throw new DAOException(exc);
            } finally {
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
    }

    private String prepareSql(String sql, List<Integer> idList){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < idList.size(); i++){
            sb.append("?,");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        return sql.replace("&", sb.toString());
    }

    @Override
    public void changeRoleForUsers(List<Integer> idList, String role) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = pool.getConnection();
            try {
                statement = connection.prepareStatement(prepareSql(SQL_FOR_CHANGE_ROLE_FOR_USERS, idList));
                statement.setString(1, role);
                for(int i = 0; i < idList.size(); i++){
                    statement.setInt(i+2, idList.get(i));
                }
                statement.executeUpdate();
            } catch (SQLException exc) {
                LOG.error(exc);
                throw new DAOException(exc);
            } finally {
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
    }

    @Override
    public void deleteUsers(List<Integer> idList) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = pool.getConnection();
            try {
                statement = connection.prepareStatement(insertListToQuery(SQL_FOR_DELETE_USERS, idList));
                statement.executeUpdate();
            } catch (SQLException exc) {
                LOG.error(exc);
                throw new DAOException(exc);
            } finally {
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
    }

    @Override
    public void markUserAsDebtor(int userId) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = pool.getConnection();
            try {
                statement = connection.prepareStatement(SQL_FOR_MARK_USER_AS_DEBTOR, userId);
                statement.setInt(1, userId);
                statement.executeUpdate();
            } catch (SQLException exc) {
                LOG.error(exc);
                throw new DAOException(exc);
            } finally {
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
    }

    @Override
    public void removeDebtorMark(int userId) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = pool.getConnection();
            try {
                statement = connection.prepareStatement(SQL_FOR_REMOVE_DEBTOR_MARK);
                statement.setInt(1, userId);
                statement.executeUpdate();
            } catch (SQLException exc) {
                LOG.error(exc);
                throw new DAOException(exc);
            } finally {
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
    }

    @Override
    public boolean checkIsDebtor(int userId) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean isDebtor = false;

        try{
            connection = pool.getConnection();
            try {
                statement = connection.prepareStatement(SQL_FOR_CHECK_IS_DEBTOR);
                statement.setInt(1, userId);
                resultSet = statement.executeQuery();
                while (resultSet.next()){
                    isDebtor = resultSet.getBoolean("is_debtor");
                }
            } catch (SQLException exc) {
                LOG.error(exc);
                throw new DAOException(exc);
            } finally {
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
        return isDebtor;
    }
}
