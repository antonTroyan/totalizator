package by.troyan.web.service;

import by.troyan.web.entity.Operation;
import by.troyan.web.exception.OperationException;
import by.troyan.web.service.exception.ServiceException;

/**
 * PaySystemService interface. Describe methods for working with PaySystem.
 */

public interface PaySystemService {
    Operation fillUpBalance(String username, String cardNumber, String validityDate, String cardCode, String amount) throws ServiceException, OperationException;

    Operation withdrawMoney(String username, String cardNumber, String validityDate, String amount) throws ServiceException, OperationException;

    void takeLoan(String username) throws ServiceException, OperationException;

    void repayLoan(String username) throws ServiceException, OperationException;
}
