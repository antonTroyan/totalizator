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
    PaginationObject<Event> getAllNotEndedEvents(int page) throws ServiceException;

    PaginationObject<Event> getAllNotEndedEventsSortedByDate(int page) throws ServiceException;

    PaginationObject<Event> getAllNotEndedEventsByCategoryId(String categoryId, int page) throws ServiceException;

    PaginationObject<Event> getAllEndedEvents(int page) throws ServiceException;

    Event getEventById(int eventId) throws ServiceException;

    Event addEvent(String name, String leagueId, String rateTypes, String liveTranslationLink,
                          String date, List<Integer> memberIds)
            throws ServiceException, EventException;

    void setCoefficientToEvent(String eventId, String coefficient) throws ServiceException;
}
