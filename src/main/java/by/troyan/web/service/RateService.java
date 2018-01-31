package by.troyan.web.service;

import by.troyan.web.entity.Rate;
import by.troyan.web.exception.RateException;
import by.troyan.web.service.exception.ServiceException;

/**
 * RateService interface. Describe method for working with Rates.
 */


public interface RateService {

    /**
     * Operation to make rate.
     * @param type type of rate
     * @param eventId id of event
     * @param username name of user
     * @param money amount of money
     * @param member1Id id of 1 member
     * @param member1Score score of 1 member (if necessary)
     * @param member2Id id of 2 member
     * @param member2Score score of 2 member (if necessary)
     * @return rate object
     */
    Rate makeRate(String type, String eventId, String username, String money, String member1Id, String member1Score,
                  String member2Id, String member2Score) throws ServiceException, RateException;
}
