package by.troyan.web.dao;

import by.troyan.web.dao.exception.DAOException;
import by.troyan.web.entity.League;

import java.util.List;

/**
 * LeagueDAO interface. Allows you to create and receive leagues for members
 * of competitions.
 */

public interface LeagueDAO {
    List<League> getLeaguesByCategory(int categoryId) throws DAOException;

    League addLeague(League league) throws DAOException;
}
