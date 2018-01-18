package by.troyan.web.dao;

import by.troyan.web.dao.exception.DAOException;
import by.troyan.web.entity.Rate;

import java.math.BigDecimal;
import java.util.List;

/**
 * RateDAO interface. Interface for working with rates.
 */

public interface RateDAO {
    List<Rate> getActiveRatesForUser(int userId) throws DAOException;

    List<Rate> getFinishedRatesForUser(int userId) throws DAOException;

    Rate addRate(Rate rate) throws DAOException;

    BigDecimal getFullMoneyAmountForEvent(int eventId) throws DAOException;

    List<Rate> getRatesForEvent(int eventId) throws DAOException;

    void setWinForRate(Rate rate) throws DAOException;
}
