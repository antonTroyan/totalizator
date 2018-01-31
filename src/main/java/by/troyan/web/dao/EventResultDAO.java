package by.troyan.web.dao;

import by.troyan.web.dao.exception.DAOException;
import by.troyan.web.entity.EventResult;

/**
 * EventResultDAO interface. Contains methods to set and receive
 * results of competitions.
 */

public interface EventResultDAO {

    /**
     * Set event result in database.
     * @param eventResult
     * @return eventResult object
     */
    EventResult addEventResult(EventResult eventResult) throws DAOException;

    /**
     * Allow to receive EventResult object from database by eventId.
     * @param eventId
     * @return eventResult object
     */
    EventResult getEventResultByEvent(int eventId) throws DAOException;
}
