package by.troyan.web.service;

import by.troyan.web.entity.Event;
import by.troyan.web.exception.EventException;
import by.troyan.web.service.exception.ServiceException;
import by.troyan.web.support.PaginationObject;

import java.util.List;

/**
 * EventResultService interface. Describe methods for working with event.
 */

public interface EventService {

    /**
     * Used to show not ended events on special pages. Not more results than
     * fixed by pagination parameters.
     * @param page page number
     * @return  PaginationObject
     */
    PaginationObject<Event> getAllNotEndedEvents(int page) throws ServiceException;

    /**
     * Used to show not ended events sorted by date on special pages. Not more results than
     * fixed by pagination parameters.
     * @param page page number
     * @return  PaginationObject
     */
    PaginationObject<Event> getAllNotEndedEventsSortedByDate(int page) throws ServiceException;

    /**
     * Used to show not ended events on special pages. Not more results than
     * fixed by pagination parameters.
     * @param categoryId category id
     * @return  PaginationObject
     */
    PaginationObject<Event> getAllNotEndedEventsByCategoryId(String categoryId, int page) throws ServiceException;

    /**
     * Used to show ended events on special pages. Not more results than
     * fixed by pagination parameters.
     * @param page page number
     * @return PaginationObject
     */
    PaginationObject<Event> getAllEndedEvents(int page) throws ServiceException;

    /**
     * Used to get event by event id.
     * @param eventId page number
     * @return Event
     */
    Event getEventById(int eventId) throws ServiceException;

    /**
     * Used to create Event.
     * @param name event name
     * @param leagueId league id
     * @param rateTypes type of rates
     * @param liveTranslationLink live translation link
     * @param date date of event
     * @param memberIds list of members
     * @return Event object
     */
    Event addEvent(String name, String leagueId, String rateTypes, String liveTranslationLink,
                          String date, List<Integer> memberIds)
            throws ServiceException, EventException;

    /**
     * Used to set coefficient to event by event id.
     * @param eventId event id
     * @param coefficient coefficient calculated by broker
     */
    void setCoefficientToEvent(String eventId, String coefficient) throws ServiceException;
}
