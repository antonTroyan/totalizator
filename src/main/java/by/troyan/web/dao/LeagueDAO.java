package by.troyan.web.dao;

import by.troyan.web.dao.exception.DAOException;
import by.troyan.web.entity.League;

import java.util.List;

/**
 * LeagueDAO interface. Allows you to create and receive leagues for members
 * of competitions.
 */

public interface LeagueDAO {

    /**
     * Allow to receive List of Leagues from database by categoryId.
     * @param categoryId
     * @return List of Leagues
     */
    List<League> getLeaguesByCategory(int categoryId) throws DAOException;

    /**
     * Allow add League to database.
     * @param league
     * @return League object
     */
    League addLeague(League league) throws DAOException;
}
