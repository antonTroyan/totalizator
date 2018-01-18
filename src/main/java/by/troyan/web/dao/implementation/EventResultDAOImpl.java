package by.troyan.web.dao.implementation;

import by.troyan.web.dao.EventResultDAO;
import by.troyan.web.dao.exception.DAOException;
import by.troyan.web.database.ConnectionPool;
import by.troyan.web.entity.EventResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Event result DAO implementation. Contains methods to set and receive
 * results of competitions.
 */

public class EventResultDAOImpl implements EventResultDAO {
    private final static Logger LOG = LogManager.getLogger(EventResultDAOImpl.class);
    private static final String SQL_FOR_GET_EVENT_RESULT_BY_EVENT = "SELECT `event_id`, `winner_id`, `loser_id`, " +
            "`winner_score`, `loser_score` " +
            "FROM `eventresult` " +
            "WHERE `event_id` = ?;";
    private static final String SQL_FOR_ADD_EVENT_RESULT = "INSERT INTO `eventresult`(`event_id`, `winner_id`, " +
            "`winner_score`, `loser_id`, `loser_score`) " +
            "VALUES(?, ?, ?, ?, ?)";



    private static final EventResultDAOImpl instance = new EventResultDAOImpl();
    private static final ConnectionPool pool = ConnectionPool.getConnectionPool();

    private EventResultDAOImpl(){}

    public static EventResultDAOImpl getInstance(){
        return instance;
    }

    @Override
    public EventResult addEventResult(EventResult eventResult) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = pool.getConnection();
            try {
                statement = connection.prepareStatement(SQL_FOR_ADD_EVENT_RESULT);
                statement.setInt(1, eventResult.getEventId());
                statement.setInt(2, eventResult.getWinnerId());
                statement.setInt(3, eventResult.getWinnerScore());
                statement.setInt(4, eventResult.getLoserId());
                statement.setInt(5, eventResult.getLoserScore());
                statement.executeUpdate();
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
        return eventResult;
    }

    @Override
    public EventResult getEventResultByEvent(int eventId) throws DAOException {
        EventResult eventResult = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = pool.getConnection();
            try{
                statement = connection.prepareStatement(SQL_FOR_GET_EVENT_RESULT_BY_EVENT);
                statement.setInt(1, eventId);
                statement.execute();
                try {
                    resultSet = statement.getResultSet();
                    if(resultSet.next()){
                        eventResult = new EventResult();
                        eventResult.setEventId(resultSet.getInt("event_id"));
                        eventResult.setWinnerScore(resultSet.getInt("winner_score"));
                        eventResult.setLoserScore(resultSet.getInt("loser_score"));
                        eventResult.setWinnerId(resultSet.getInt("winner_id"));
                        eventResult.setLoserId(resultSet.getInt("loser_id"));
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
        return eventResult;
    }



}
