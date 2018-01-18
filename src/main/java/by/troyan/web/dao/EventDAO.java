package by.troyan.web.dao;

import by.troyan.web.dao.exception.DAOException;
import by.troyan.web.entity.Event;

import java.util.List;

/**
 * EventDAO interface. Contains methods to work with events.
 */

public interface EventDAO {
    List<Event> getAllNotEndedEvents() throws DAOException;

    List<Event> getAllNotEndedEventsSortedByDate() throws DAOException;

    List<Event> getAllNotEndedEventsByCategoryId(int categoryId) throws DAOException;

    List<Event> getAllEndedEvents() throws DAOException;

    Event getEventById(int eventId) throws DAOException;

    Event addEvent(Event event) throws DAOException;

    void finishEvent(int eventId) throws DAOException;

    void setEventCoefficient(int eventId, double coefficient) throws DAOException;
}
