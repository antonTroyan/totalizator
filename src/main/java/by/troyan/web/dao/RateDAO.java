package by.troyan.web.dao;

import by.troyan.web.dao.exception.DAOException;
import by.troyan.web.entity.Rate;

import java.math.BigDecimal;
import java.util.List;

/**
 * RateDAO interface. Interface for working with rates.
 */

public interface RateDAO {

    /**
     * Used to get active Rates for special user.
     * @param userId - int user id
     * @return List of Rates
     */
    List<Rate> getActiveRatesForUser(int userId) throws DAOException;

    /**
     * Used to get finished Rates for special user.
     * @param userId - int user id
     * @return List of Rates
     */
    List<Rate> getFinishedRatesForUser(int userId) throws DAOException;

    /**
     * Used add Rate to database.
     * @param rate - Rate object
     * @return Rate
     */
    Rate addRate(Rate rate) throws DAOException;

    /**
     * Used to get all Rates for event by eventId.
     * @param eventId - int eventId
     * @return List of Rates
     */
    List<Rate> getRatesForEvent(int eventId) throws DAOException;

    /**
     * Used set win rates.
     * @param rate - int eventId
     */
    void setWinForRate(Rate rate) throws DAOException;
}
