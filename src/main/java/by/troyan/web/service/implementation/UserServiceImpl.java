package by.troyan.web.service.implementation;

import by.troyan.web.dao.RateDAO;
import by.troyan.web.dao.UserDAO;
import by.troyan.web.dao.exception.DAOException;
import by.troyan.web.dao.factory.DAOFactory;
import by.troyan.web.entity.User;
import by.troyan.web.exception.UserException;
import by.troyan.web.service.UserService;
import by.troyan.web.service.exception.ServiceException;
import by.troyan.web.support.MD5Converter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * UserService implementation. Describe method works with Users.
 */

public class UserServiceImpl implements UserService {
    private final static Logger LOG = LogManager.getLogger(UserServiceImpl.class);
    private static final UserServiceImpl instance = new UserServiceImpl();
    private UserDAO userDAO;
    private RateDAO rateDAO;

    public static UserServiceImpl getInstance(){
        return instance;
    }

    private UserServiceImpl(){
        userDAO = DAOFactory.getFactory().getUserDAO();
        rateDAO = DAOFactory.getFactory().getRateDAO();
    }

    @Override
    public User registerUser(String login, String password, String confirmPassword, String email)
            throws ServiceException, UserException {
        User user = new User();
        if((email == null) || (email.isEmpty()))
            throw new ServiceException("email is empty or null");
        user.setEmail(email);
        if((login == null) || (login.isEmpty())){
            throw new ServiceException("login is empty or null");
        }
        user.setLogin(login);
        if((password == null) || (password.isEmpty()) || (confirmPassword == null)
                || (confirmPassword.isEmpty()) || (!password.equals(confirmPassword))){
            throw new ServiceException("password or password confirmation is invalid");
        }
        user.setPassHash(MD5Converter.getHash(password));
        try {
            user = userDAO.createUser(user);
        }
        catch (DAOException exc){
            LOG.error(exc);
            throw new ServiceException(exc);
        }
        return user;
    }

    @Override
    public User login(String login, String password) throws ServiceException, UserException {
        try {
            User user = userDAO.getUserByLogin(login);
            if((user == null) || (!user.getPassHash().equals(MD5Converter.getHash(password)))){
                User errorUser = new User();
                errorUser.setLogin(login);
                throw new UserException("err.password-or-login-incorrect", errorUser);
            }
            if(user.isBanned()){
                User errorUser = new User();
                errorUser.setLogin(login);
                throw new UserException("err.you-are-banned", errorUser);
            }
            return user;
        } catch (DAOException exc){
            LOG.error(exc);
            throw new ServiceException(exc);
        }
    }

    @Override
    public User getFullUserInformationByLogin(String login) throws ServiceException {
        try {
            User user = userDAO.getFullUserInformationByLogin(login);
            user.setActiveRates(rateDAO.getActiveRatesForUser(user.getUserId()));
            user.setFinishedRates(rateDAO.getFinishedRatesForUser(user.getUserId()));
            return user;
        } catch (DAOException exc){
            LOG.error(exc);
            throw new ServiceException(exc);
        }
    }

    @Override
    public List<User> getAllUsers() throws ServiceException {
        try{
            return userDAO.getAllUsers();
        } catch (DAOException exc){
            LOG.error(exc);
            throw new ServiceException(exc);
        }
    }

    @Override
    public void banUsers(List<Integer> idList) throws ServiceException {
        try{
            userDAO.banUsers(idList);
        } catch (DAOException exc){
            LOG.error(exc);
            throw new ServiceException(exc);
        }
    }

    @Override
    public void unbanUsers(List<Integer> idList) throws ServiceException {
        try{
            userDAO.unbanUsers(idList);
        } catch (DAOException exc){
            LOG.error(exc);
            throw new ServiceException(exc);
        }
    }

    @Override
    public void changeRoleForUsers(List<Integer> idList, String role) throws ServiceException {
        try{
            userDAO.changeRoleForUsers(idList, role);
        } catch (DAOException exc){
            LOG.error(exc);
            throw new ServiceException(exc);
        }
    }

    @Override
    public void deleteUsers(List<Integer> idList) throws ServiceException {
        try{
            userDAO.deleteUsers(idList);
        } catch (DAOException exc){
            LOG.error(exc);
            throw new ServiceException(exc);
        }
    }

    @Override
    public boolean checkIsDebtor(String login) throws ServiceException {
        boolean isDebtor = false;
        try {
            isDebtor = userDAO.checkIsDebtor(userDAO.getUserIdByLogin(login));
        } catch (DAOException exc) {
            LOG.error(exc);
            throw new ServiceException(exc);
        }
        return isDebtor;
    }
}
