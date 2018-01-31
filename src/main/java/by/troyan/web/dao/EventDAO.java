package by.troyan.web.dao;

import by.troyan.web.dao.exception.DAOException;
import by.troyan.web.entity.Event;

import java.util.List;

/**
 * EventDAO interface. Contains methods to work with events.
 */

public interface EventDAO {

    /**
     * Used to get all not ended events from database.
     * @return  List of Events.
     */
    List<Event> getAllNotEndedEvents() throws DAOException;

    /**
     * Used to get all not ended events from database sorted by date.
     * @return  List of Events.
     */
    List<Event> getAllNotEndedEventsSortedByDate() throws DAOException;

    /**
     * Used to get all not ended events by category.
     * @param categoryId
     * @return  List of Events.
     */
    List<Event> getAllNotEndedEventsByCategoryId(int categoryId) throws DAOException;

    /**
     * Used to get all ended events.
     * @return  List of Events.
     */
    List<Event> getAllEndedEvents() throws DAOException;

    /**
     * Used to get all ended events.
     * @param eventId
     * @return Event object.
     */
    Event getEventById(int eventId) throws DAOException;

    /**
     * Used to add event to database.
     * @param event
     * @return Event object.
     */
    Event addEvent(Event event) throws DAOException;

    /**
     * Used to change status of event in database.
     * @param eventId
     */
    void finishEvent(int eventId) throws DAOException;

    /**
     * Used to set event coefficient in database.
     * @param eventId
     */
    void setEventCoefficient(int eventId, double coefficient) throws DAOException;
}
