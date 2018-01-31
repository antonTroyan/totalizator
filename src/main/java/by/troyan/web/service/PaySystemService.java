package by.troyan.web.service;

import by.troyan.web.entity.Operation;
import by.troyan.web.exception.OperationException;
import by.troyan.web.service.exception.ServiceException;

/**
 * PaySystemService interface. Describe methods for working with PaySystem.
 */

public interface PaySystemService {

    /**
     * Operation to fill up balance.
     * @param username username
     * @param cardNumber number of card
     * @param validityDate validity date
     * @param cardCode code of card
     * @param amount amount to fill up
     * @return  operation object
     */
    Operation fillUpBalance(String username, String cardNumber, String validityDate, String cardCode, String amount)
            throws ServiceException, OperationException;

    /**
     * Operation to withdraw money.
     * @param username username
     * @param cardNumber number of card
     * @param validityDate validity date
     * @param amount amount to withdraw
     * @return  operation object
     */
    Operation withdrawMoney(String username, String cardNumber, String validityDate, String amount)
            throws ServiceException, OperationException;

    /**
     * Operation to take loan.
     * @param username username
     */
    void takeLoan(String username) throws ServiceException, OperationException;

    /**
     * Operation to repay loan.
     * @param username username
     */
    void repayLoan(String username) throws ServiceException, OperationException;
}
