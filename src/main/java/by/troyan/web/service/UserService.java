package by.troyan.web.service;

import by.troyan.web.entity.User;
import by.troyan.web.exception.UserException;
import by.troyan.web.service.exception.ServiceException;

import java.util.List;

/**
 * RateService interface. Describe methods for working with User.
 */


public interface UserService {

    /**
     * Operation to register user.
     * @param login user login
     * @param password user password
     * @param confirmPassword confirmation password
     * @param email user email
     * @return User object
     */
    User registerUser(String login, String password, String confirmPassword, String email) throws ServiceException, UserException;

    /**
     * Operation to login user.
     * @param login user login
     * @param password user password
     * @return User object
     */
    User login(String login, String password) throws ServiceException, UserException;

    /**
     * Operation to get full information about user.
     * @param login user login
     * @return User object
     */
    User getFullUserInformationByLogin(String login) throws  ServiceException;

    /**
     * Operation to get list of Users.
     * @return list of Users
     */
    List<User> getAllUsers() throws ServiceException;

    /**
     * Operation to ban list of Users.
     * @param  idList list of ids of users
     */
    void banUsers(List<Integer> idList) throws ServiceException;

    /**
     * Operation to unban list of Users.
     * @param  idList list of ids of users
     */
    void unbanUsers(List<Integer> idList) throws ServiceException;

    /**
     * Operation to change roles to list of Users.
     * @param  idList list of ids of users
     */
    void changeRoleForUsers(List<Integer> idList, String role) throws ServiceException;

    /**
     * Operation to delete list of Users.
     * @param  idList list of ids of users
     */
    void deleteUsers(List<Integer> idList) throws ServiceException;

    /**
     * Used to check is user a debtor.
     * @param  login user login
     */
    boolean checkIsDebtor(String login) throws  ServiceException;

}
