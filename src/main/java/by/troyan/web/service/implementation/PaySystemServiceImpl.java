package by.troyan.web.service.implementation;

import by.troyan.web.dao.OperationDAO;
import by.troyan.web.dao.UserDAO;
import by.troyan.web.dao.exception.DAOException;
import by.troyan.web.dao.factory.DAOFactory;
import by.troyan.web.dao.implementation.OperationDAOImpl;
import by.troyan.web.entity.Operation;
import by.troyan.web.exception.OperationException;
import by.troyan.web.service.PaySystemService;
import by.troyan.web.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

/**
 * PaySystemService implementation. Describe method works with PaySystems.
 */

public class PaySystemServiceImpl implements PaySystemService {
    private final static Logger LOG = LogManager.getLogger(PaySystemServiceImpl.class);
    private static final PaySystemServiceImpl instance = new PaySystemServiceImpl();

    private OperationDAO operationDAO;
    private UserDAO userDAO;

    public static PaySystemServiceImpl getInstance(){
        return instance;
    }

    private PaySystemServiceImpl(){
        operationDAO = DAOFactory.getFactory().getOperationDAO();
        userDAO = DAOFactory.getFactory().getUserDAO();
    }

    @Override
    public Operation fillUpBalance(String username, String cardNumber, String validityDate, String cardCode, String amount) throws ServiceException, OperationException {
        Operation operation = new Operation();
        OperationException operationException = new OperationException(operation);
        if((cardNumber == null) || (cardNumber.isEmpty()) || (cardNumber.length() != 16)){
            operationException.addErrorMessage("err.card-number-is-invalid");
        }
        operation.setCardNumber(cardNumber);
        if((validityDate == null) || (validityDate.isEmpty()) || (validityDate.length() != 5)){
            operationException.addErrorMessage("err.validity-date-is-invalid");
        }
        operation.setValidityDate(validityDate);
        if((cardCode == null) || (cardCode.isEmpty()) || (cardCode.length() != 3)){
            operationException.addErrorMessage("err.card-code-is-invalid");
        }
        operation.setCardCode(cardCode);
        BigDecimal bigDecimalAmount;
        try{
            bigDecimalAmount = BigDecimal.valueOf(Double.parseDouble(amount));
            operation.setAmount(bigDecimalAmount);
        } catch (NumberFormatException exc){
            operationException.addErrorMessage("err.amount-is-invalid");
        }
        if(operationException.getErrorMessageList().size() != 0){
            throw operationException;
        }
        operation.setOperationType(OperationDAOImpl.INPUT);
        try{
            operation.setUserId(userDAO.getUserIdByLogin(username));
            userDAO.fillUpBalanceForUser(operation.getUserId(), operation.getAmount());
            operationDAO.addOperation(operation);
        } catch (DAOException exc){
            LOG.error(exc);
            throw new ServiceException(exc);
        }
        return operation;
    }

    @Override
    public Operation withdrawMoney(String username, String cardNumber, String validityDate, String amount) throws ServiceException, OperationException {
        Operation operation = new Operation();
        OperationException operationException = new OperationException(operation);
        if((cardNumber == null) || (cardNumber.isEmpty()) || (cardNumber.length() != 16)){
            operationException.addErrorMessage("err.card-number-is-invalid");
        }
        operation.setCardNumber(cardNumber);
        if((validityDate == null) || (validityDate.isEmpty()) || (validityDate.length() != 5)){
            operationException.addErrorMessage("err.validity-date-is-invalid");
        }
        operation.setValidityDate(validityDate);
        BigDecimal bigDecimalAmount;
        try{
            bigDecimalAmount = BigDecimal.valueOf(Double.parseDouble(amount));
            operation.setAmount(bigDecimalAmount);
        } catch (NumberFormatException exc){
            operationException.addErrorMessage("err.amount-is-invalid");
        }
        if(operationException.getErrorMessageList().size() != 0){
            throw operationException;
        }
        operation.setOperationType(OperationDAOImpl.OUTPUT);
        try{
            operation.setUserId(userDAO.getUserIdByLogin(username));
            if(!userDAO.haveMoney(operation.getUserId(), operation.getAmount())){
                operationException.addErrorMessage("err.can-not-withdraw-money-because-not-enough");
                throw operationException;
            }
            userDAO.withdrawMoneyFromUser(operation.getUserId(), operation.getAmount());
            operationDAO.addOperation(operation);
        } catch (DAOException exc){
            LOG.error(exc);
            throw new ServiceException(exc);
        }
        return operation;
    }

    @Override
    public void takeLoan(String username) throws ServiceException {
        try{
            userDAO.markUserAsDebtor(userDAO.getUserIdByLogin(username));
        } catch (DAOException exc){
            LOG.error(exc);
            throw new ServiceException(exc);
        }
    }

    @Override
    public void repayLoan(String username) throws ServiceException {
        try{
            userDAO.removeDebtorMark(userDAO.getUserIdByLogin(username));
        } catch (DAOException exc){
            LOG.error(exc);
            throw new ServiceException(exc);
        }
    }
}
