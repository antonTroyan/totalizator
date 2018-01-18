package by.troyan.web.service;

import by.troyan.web.entity.League;
import by.troyan.web.exception.LeagueException;
import by.troyan.web.service.exception.ServiceException;

import java.util.List;

/**
 * LeagueService interface. Describe methods for working with League.
 */


public interface LeagueService {
    List<League> getLeaguesByCategory(int categoryId) throws ServiceException;

    League addLeague(String name, String categoryId) throws ServiceException, LeagueException;
}
