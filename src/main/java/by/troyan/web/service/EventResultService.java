package by.troyan.web.service;

import by.troyan.web.entity.EventResult;
import by.troyan.web.exception.EventResultException;
import by.troyan.web.service.exception.ServiceException;

/**
 * EventResultService interface. Describe method works with EventResult.
 */

public interface EventResultService {

    /**
     * Used to add random result to event.
     * @param eventId event id
     * @return  Event result object.
     */
    EventResult addRandomResultToEvent (String eventId) throws ServiceException, EventResultException;

}
