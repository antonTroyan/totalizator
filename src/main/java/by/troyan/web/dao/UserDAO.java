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

    /**
     * Used to get List of all Users
     * @return List of Users
     */
    List<User> getAllUsers() throws DAOException;

    /**
     * Used to create user.
     * @param user - User object
     * @return User object
     */
    User createUser(User user) throws DAOException;

    /**
     * Used to get user by login.
     * @param login - user login
     * @return User object
     */
    User getUserByLogin(String login) throws DAOException;

    /**
     * Used to get full user information by login.
     * @param login - user login
     * @return User object
     */
    User getFullUserInformationByLogin(String login) throws DAOException;

    /**
     * Used to get user id by login.
     * @param login - user login
     * @return user id int
     */
    int getUserIdByLogin(String login) throws DAOException;

    /**
     * Used to fill up balance by userId.
     * @param userId - user id,
     * @param money - amount of money to fill up
     */
    void fillUpBalanceForUser(int userId, BigDecimal money) throws DAOException;

    /**
     * Used to withdraw money from balance by userId.
     * @param userId - user id,
     * @param money - amount of money to withdraw
     */
    void withdrawMoneyFromUser(int userId, BigDecimal money) throws DAOException;

    /**
     * Used to check money amount on balance by userId.
     * @param userId - user id,
     * @param money - amount of money to check
     * @return true - id user has enough money, false in other case
     */
    boolean haveMoney(int userId, BigDecimal money) throws DAOException;

    /**
     * Used to ban List of users by userId.
     * @param idList - user id List
     */
    void banUsers(List<Integer> idList) throws DAOException;

    /**
     * Used to unban List of users by userId.
     * @param idList - user id List
     */
    void unbanUsers(List<Integer> idList) throws DAOException;

    /**
     * Used to change role of List of users by userId.
     * @param idList - user id List
     * @param role - role that should be set for all users in list
     */
    void changeRoleForUsers(List<Integer> idList, String role) throws DAOException;

    /**
     * Used to delete List of users by userId.
     * @param idList - user id List
     */
    void deleteUsers(List<Integer> idList) throws DAOException;

    /**
     * Used to mark user as debtor. It prevent him of taking
     * new loan until he repay old.
     * @param userId - user id
     */
    void markUserAsDebtor(int userId) throws DAOException;

    /**
     * Used to remove user debtor mark. After this he could take a new loan.
     * @param userId - user id
     */
    void removeDebtorMark(int userId) throws DAOException;

    /**
     * Used to check user debtor mark. If he is debtor he could not  take a new loan.
     * @param userId - user id
     */
    boolean checkIsDebtor (int userId) throws DAOException;

    /**
     * Prevent register user with the same login.
     * @param login - user login
     */
    boolean checkIsLoginFree (String login) throws DAOException;

    /**
     * Prevent register user with the same email.
     * @param email - user email
     */
    boolean checkIsEmailFree (String email) throws DAOException;
}
