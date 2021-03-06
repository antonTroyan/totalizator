package by.troyan.web.dao.implementation;


import by.troyan.web.dao.EventDAO;
import by.troyan.web.dao.exception.DAOException;
import by.troyan.web.database.ConnectionPool;
import by.troyan.web.entity.Event;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Event DAO implementation. Contains methods to work with events.
 */

public class EventDAOImpl implements EventDAO {
    private static final String SQL_FOR_FINISH_EVENT = "UPDATE `event` " +
            "SET `event_status` = 'FINISHED' " +
            "WHERE `event_id` = ?;";
    private static final String SQL_FOR_GET_ALL_NOT_ENDED_EVENTS_SORTED_BY_DATE = "SELECT `event`.`event_id` " +
            "AS `id`, `event_name`, `league_name`,  `event_status`, " +
            "`event_start_date` AS `date` " +
            "FROM `event` " +
            "LEFT JOIN `league` " +
            "ON `event`.`league_id` = `league`.`league_id` " +
            "WHERE `event_status` = 'POSTED' " +
            "AND `event_start_date` > now() " +
            "ORDER BY `date`;";
    private static final String SQL_FOR_GET_ALL_NOT_ENDED_EVENTS_BY_CATEGORY_ID = "SELECT `event`.`event_id` AS `id`, " +
            "`event_name`, `event_category_id`, `league_name`, `event_status`, " +
            "`event_start_date` AS `date` " +
            "FROM `event` " +
            "LEFT JOIN `league` " +
            "ON `event`.`league_id` = `league`.`league_id` " +
            "WHERE `event_status` = 'POSTED' " +
            "AND `event_start_date` > now()" +
            "AND `event_category_id` = ?;";
    private static final String SQL_FOR_GET_ALL_ENDED_EVENTS = "SELECT `event`.`event_id` " +
            "AS `id`, `event_name`, `event_category_id`, `league_name`, " +
            "`event_status`, `event_start_date` AS `date` " +
            "FROM `event` " +
            "LEFT JOIN `league` " +
            "ON `event`.`league_id` = `league`.`league_id` " +
            "WHERE `event_status` = 'FINISHED' " +
            "OR `event_start_date` < now();";
    private static final String SQL_GOR_GET_ALL_NOT_ENDED_EVENTS = "SELECT `event`.`event_id` AS `id`, `event_name`, " +
            "`league_name`, `event_status`, `event_start_date` AS `date` " +
            "FROM `event` " +
            "LEFT JOIN `league` " +
            "ON `event`.`league_id` = `league`.`league_id` " +
            "WHERE `event_status` = 'POSTED' " +
            "AND `event_start_date` > now();";
    private static final String SQL_FOR_GET_EVENT_BY_ID = "SELECT `event_id` AS `id`, `event_name`, `rate_types`, " +
            "`event_status`,`coefficient`, `live_translation_reference` AS `link`, `event_start_date` AS `date`, " +
            "`league_name` " +
            "FROM `event` " +
            "LEFT JOIN `league` " +
            "ON `event`.`league_id` = `league`.`league_id` " +
            "WHERE `event_id` = ?;";
    private static final String SQL_FOR_ADD_EVENT = "INSERT INTO `event`(`event_name`, `league_id`, `rate_types`, " +
            "`live_translation_reference`, `event_start_date`) " +
            "VALUES (?, ?, ?, ?, ?);";

    private static final  String SQL_FOR_SET_COEFFICIENT = "UPDATE `event` SET `coefficient`= ? WHERE `event_id`= ?";

    private final static Logger LOG = LogManager.getLogger("EventDAOImpl");
    private static final EventDAOImpl INSTANCE = new EventDAOImpl();
    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.getConnectionPool();

    private EventDAOImpl(){}

    public static EventDAOImpl getInstance(){
        return INSTANCE;
    }

    private List<Event> getEventsBySql(String sql, Object... params) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Event> result = new ArrayList<>();

        try {
            connection = CONNECTION_POOL.getConnection();
            try {
                statement = connection.prepareStatement(sql);
                insertParamsIntoPreparedStatement(statement, params);
                statement.execute();
                try {
                    resultSet = statement.getResultSet();
                    Event event;
                    while (resultSet.next()) {
                        event = new Event();
                        event.setEventName(resultSet.getString("event_name"));
                        event.setEventDate(resultSet.getDate("date"));
                        event.setEventTime(resultSet.getTime("date"));
                        event.setEventId(resultSet.getInt("id"));
                        event.setEventLeague(resultSet.getString("league_name"));
                        result.add(event);
                    }
                } catch (SQLException exc){
                    LOG.error(exc);
                    throw new DAOException(exc.getMessage());
                } finally {
                    if(resultSet != null){
                        resultSet.close();
                    }
                }
            } catch (SQLException exc){
                LOG.error(exc);
                throw new DAOException(exc.getMessage());
            } finally {
                if(statement != null){
                    statement.close();
                }
            }
        } catch (SQLException exc){
            LOG.error(exc);
            throw new DAOException(exc.getMessage());
        } finally {
            CONNECTION_POOL.returnConnectionToPool(connection);
        }
        return result;
    }

    /**
     * Used to get all not ended events by category.
     * @param categoryId
     * @return  List of Events.
     */
    public List<Event> getAllNotEndedEventsByCategoryId(int categoryId) throws DAOException {
        return getEventsBySql(SQL_FOR_GET_ALL_NOT_ENDED_EVENTS_BY_CATEGORY_ID, categoryId);
    }

    /**
     * Used to get all ended events.
     * @return  List of Events.
     */
    public List<Event> getAllEndedEvents() throws DAOException {
        return getEventsBySql(SQL_FOR_GET_ALL_ENDED_EVENTS);
    }

    private void insertParamsIntoPreparedStatement(PreparedStatement statement, Object[] params) throws SQLException{
        Object param;
        for(int i = 0; i < params.length; i++){
            param = params[i];
            if(param.getClass() == Integer.class) {
                statement.setInt(i + 1, (Integer) param);
            }
            else if (param.getClass() == String.class){
                statement.setString(i + 1, (String) param);
            }
        }
    }


    /**
     * Used to get all not ended events from database.
     * @return  List of Events.
     */
    @Override
    public List<Event> getAllNotEndedEvents() throws DAOException {
        return getEventsBySql(SQL_GOR_GET_ALL_NOT_ENDED_EVENTS);
    }

    /**
     * Used to get all not ended events from database sorted by date.
     * @return  List of Events.
     */
    @Override
    public List<Event> getAllNotEndedEventsSortedByDate() throws DAOException {
        return getEventsBySql(SQL_FOR_GET_ALL_NOT_ENDED_EVENTS_SORTED_BY_DATE);
    }

    /**
     * Used to get all ended events.
     * @param eventId
     * @return Event object.
     */
    @Override
    public Event getEventById(int eventId) throws DAOException{
        Event event = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = CONNECTION_POOL.getConnection();
            try{
                statement = connection.prepareStatement(SQL_FOR_GET_EVENT_BY_ID);
                statement.setInt(1, eventId);
                statement.execute();
                try {
                    resultSet = statement.getResultSet();
                    if(resultSet.next()){
                        event = new Event();
                        event.setEventName(resultSet.getString("event_name"));
                        event.setEventLeague(resultSet.getString("league_name"));
                        event.setEventId(resultSet.getInt("id"));
                        event.setEventDate(resultSet.getDate("date"));
                        event.setEventTime(resultSet.getTime("date"));
                        event.setLiveTranslationLink(resultSet.getString("link"));
                        event.setStatus(resultSet.getString("event_status"));
                        event.setCoefficient(resultSet.getDouble("coefficient"));
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
                CONNECTION_POOL.returnConnectionToPool(connection);
            }
        }
        return event;
    }

    /**
     * Used to add event to database.
     * @param event
     * @return Event object.
     */
    @Override
    public Event addEvent(Event event) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = CONNECTION_POOL.getConnection();
            try {
                statement = connection.prepareStatement(SQL_FOR_ADD_EVENT, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, event.getEventName());
                statement.setInt(2, event.getLeagueId());
                statement.setString(3, event.getRateTypes());
                statement.setString(4, event.getLiveTranslationLink());
                statement.setTimestamp(5, new Timestamp(event.getEventDate().getTime()));
                int result = statement.executeUpdate();
                try{
                    resultSet = statement.getGeneratedKeys();
                    resultSet.next();
                    event.setEventId(resultSet.getInt(1));
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
                CONNECTION_POOL.returnConnectionToPool(connection);
            }
        }
        return event;
    }

    /**
     * Used to change status of event in database.
     * @param eventId
     */
    @Override
    public void finishEvent(int eventId) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = CONNECTION_POOL.getConnection();
            try {
                statement = connection.prepareStatement(SQL_FOR_FINISH_EVENT);
                statement.setInt(1, eventId);
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
                CONNECTION_POOL.returnConnectionToPool(connection);
            }
        }
    }

    /**
     * Used to set event coefficient in database.
     * @param eventId
     */
    @Override
    public void setEventCoefficient(int eventId, double coefficient) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = CONNECTION_POOL.getConnection();
            try {
                statement = connection.prepareStatement(SQL_FOR_SET_COEFFICIENT);
                statement.setDouble(1, coefficient);
                statement.setInt(2, eventId);
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
                CONNECTION_POOL.returnConnectionToPool(connection);
            }
        }
    }

}
