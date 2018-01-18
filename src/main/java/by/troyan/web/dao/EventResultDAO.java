package by.troyan.web.dao;

import by.troyan.web.dao.exception.DAOException;
import by.troyan.web.entity.EventResult;

/**
 * EventResultDAO interface. Contains methods to set and receive
 * results of competitions.
 */

public interface EventResultDAO {
    EventResult addEventResult(EventResult eventResult) throws DAOException;

    EventResult getEventResultByEvent(int eventId) throws DAOException;

}
