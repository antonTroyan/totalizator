package by.troyan.web.service;

import by.troyan.web.entity.User;
import by.troyan.web.exception.UserException;
import by.troyan.web.service.exception.ServiceException;

import java.util.List;

/**
 * RateService interface. Describe methods for working with User.
 */


public interface UserService {
    User registerUser(String login, String password, String confirmPassword, String email) throws ServiceException, UserException;

    User login(String login, String password) throws ServiceException, UserException;

    User getFullUserInformationByLogin(String login) throws  ServiceException;

    List<User> getAllUsers() throws ServiceException;

    void banUsers(List<Integer> idList) throws ServiceException;

    void unbanUsers(List<Integer> idList) throws ServiceException;

    void changeRoleForUsers(List<Integer> idList, String role) throws ServiceException;

    void deleteUsers(List<Integer> idList) throws ServiceException;

    boolean checkIsDebtor(String login) throws  ServiceException;
}
