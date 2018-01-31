package by.troyan.web.service;

import by.troyan.web.entity.League;
import by.troyan.web.exception.LeagueException;
import by.troyan.web.service.exception.ServiceException;

import java.util.List;

/**
 * LeagueService interface. Describe methods for working with League.
 */


public interface LeagueService {

    /**
     * Used get special league by its categoryId.
     * @param categoryId event id
     * @return  list of Leagues
     */
    List<League> getLeaguesByCategory(int categoryId) throws ServiceException;

    /**
     * Used add league.
     * @param categoryId event id
     * @param  name name of league
     * @return League object
     */
    League addLeague(String name, String categoryId) throws ServiceException, LeagueException;
}
