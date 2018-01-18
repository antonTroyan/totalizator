package by.troyan.web.dao;

import by.troyan.web.dao.exception.DAOException;
import by.troyan.web.entity.User;
import by.troyan.web.exception.UserException;

import java.math.BigDecimal;
import java.util.List;

/**
 * UserDAO interface. Interface for working with users.
 */


public interface UserDAO {
    List<User> getAllUsers() throws DAOException;

    User createUser(User user) throws DAOException, UserException;

    User getUserByLogin(String login) throws DAOException;

    User getFullUserInformationByLogin(String login) throws DAOException;

    int getUserIdByLogin(String login) throws DAOException;

    void fillUpBalanceForUser(int userId, BigDecimal money) throws DAOException;

    void withdrawMoneyFromUser(int userId, BigDecimal money) throws DAOException;

    boolean haveMoney(int userId, BigDecimal money) throws DAOException;

    void banUsers(List<Integer> idList) throws DAOException;

    void unbanUsers(List<Integer> idList) throws DAOException;

    void changeRoleForUsers(List<Integer> idList, String role) throws DAOException;

    void deleteUsers(List<Integer> idList) throws DAOException;

    void markUserAsDebtor(int userId) throws DAOException;

    void removeDebtorMark(int userId) throws DAOException;

    boolean checkIsDebtor (int userId) throws DAOException;
}
