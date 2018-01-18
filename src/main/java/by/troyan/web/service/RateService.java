package by.troyan.web.service;

import by.troyan.web.entity.Rate;
import by.troyan.web.exception.RateException;
import by.troyan.web.service.exception.ServiceException;

/**
 * RateService interface. Describe method for working with Rates.
 */


public interface RateService {
    Rate makeRate(String type, String eventId, String username, String money,
                  String member1Id, String member1Score, String member2Id, String member2Score) throws ServiceException, RateException;
}
