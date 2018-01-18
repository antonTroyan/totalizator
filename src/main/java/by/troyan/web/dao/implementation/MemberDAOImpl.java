package by.troyan.web.dao.implementation;

import by.troyan.web.dao.MemberDAO;
import by.troyan.web.dao.exception.DAOException;
import by.troyan.web.database.ConnectionPool;
import by.troyan.web.entity.Member;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Member DAO implementation. Here you can find methods work with
 * members of competitions.
 */

public class MemberDAOImpl implements MemberDAO {
    private final static Logger LOG = LogManager.getLogger(MemberDAOImpl.class);
    private static final String SQL_FOR_GET_MEMBER_NAME_BY_ID = "SELECT `member_name` " +
            "FROM `eventmember` " +
            "WHERE `member_id` = ?;";
    private static final String SQL_FOR_ADD_MEMBER = "INSERT INTO `eventmember`(`league_id`, `member_name`) " +
            "VALUES(?, ?);";
    private static final String SQL_FOR_GET_MEMBERS_BY_LEAGUE = "SELECT `member_id` AS `id`, `member_name` AS `name` " +
            "FROM `eventmember` " +
            "WHERE `league_id` = ?;";
    private static final String SQL_FOR_ATTACH_MEMBER_TO_EVENT = "INSERT INTO `event_m2m_eventmember`(`event_id`, `member_id`) " +
            "VALUES (?, ?);";
    private static final String SQL_FOR_GET_MEMBERS_BY_EVENT = "SELECT `eventmember`.`member_id`, `member_name` AS `name` " +
            "FROM `eventmember` " +
            "JOIN `event_m2m_eventmember` " +
            "ON `eventmember`.`member_id` = `event_m2m_eventmember`.`member_id` " +
            "WHERE `event_m2m_eventmember`.`event_id` = ?;";

    private static final MemberDAOImpl instance = new MemberDAOImpl();
    private static final ConnectionPool pool = ConnectionPool.getConnectionPool();

    public static MemberDAOImpl getInstance(){
        return instance;
    }

    private MemberDAOImpl(){}

    @Override
    public List<Member> getMembersByLeague(int leagueId) throws DAOException {
        PreparedStatement statement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        List<Member> result = new ArrayList<>();

        try{
            connection = pool.getConnection();
            try{
                statement = connection.prepareStatement(SQL_FOR_GET_MEMBERS_BY_LEAGUE);
                statement.setInt(1, leagueId);
                statement.execute();
                try{
                    resultSet = statement.getResultSet();
                    Member member;
                    while(resultSet.next()){
                        member = new Member();
                        member.setName(resultSet.getString("name"));
                        member.setId(resultSet.getInt("id"));
                        result.add(member);
                    }
                } catch (SQLException exc){
                    LOG.error(exc);
                    throw new DAOException(exc);
                } finally {
                    if(resultSet != null){
                        resultSet.close();
                    }
                }
            } catch (SQLException exc){
                LOG.error(exc);
                throw new DAOException(exc);
            } finally {
                if(statement != null){
                    statement.close();
                }
            }
        } catch (SQLException exc){
            LOG.error(exc);
            throw new DAOException(exc);
        } finally {
            if(connection != null){
                pool.returnConnectionToPool(connection);
            }
        }

        return result;
    }


    @Override
    public void attachMembersToEvent(List<Integer> memberIds, int eventId) throws DAOException {
        for(Integer memberId : memberIds){
            attachMemberToEvent(memberId, eventId);
        }
    }

    private void attachMemberToEvent(int memberId, int eventId) throws DAOException{
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = pool.getConnection();
            try {
                statement = connection.prepareStatement(SQL_FOR_ATTACH_MEMBER_TO_EVENT);
                statement.setInt(1, eventId);
                statement.setInt(2, memberId);
                statement.executeUpdate();
            } catch (SQLException exc){
                LOG.error(exc);
                throw new DAOException(exc);
            } finally {
                if(statement != null){
                    statement.close();
                }
            }
        } catch (SQLException exc){
            LOG.error(exc);
            throw new DAOException(exc);
        } finally {
            if(connection != null){
                pool.returnConnectionToPool(connection);
            }
        }
    }

    @Override
    public List<Member> getMembersByEvent(int eventId) throws DAOException {
        PreparedStatement statement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        List<Member> result = new ArrayList<>();
        try{
            connection = pool.getConnection();
            try{
                statement = connection.prepareStatement(SQL_FOR_GET_MEMBERS_BY_EVENT);
                statement.setInt(1, eventId);
                statement.execute();
                try{
                    resultSet = statement.getResultSet();
                    Member member;
                    while(resultSet.next()){
                        member = new Member();
                        member.setName(resultSet.getString("name"));
                        member.setId(resultSet.getInt("member_id"));
                        result.add(member);
                    }
                } catch (SQLException exc){
                    LOG.error(exc);
                    throw new DAOException(exc);
                } finally {
                    if(resultSet != null){
                        resultSet.close();
                    }
                }
            } catch (SQLException exc){
                LOG.error(exc);
                throw new DAOException(exc);
            } finally {
                if(statement != null){
                    statement.close();
                }
            }
        } catch (SQLException exc){
            LOG.error(exc);
            throw new DAOException(exc);
        } finally {
            if(connection != null){
                pool.returnConnectionToPool(connection);
            }
        }

        return result;
    }

    @Override
    public String getMemberNameById(int memberId) throws DAOException {
        PreparedStatement statement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        String result = null;
        try{
            connection = pool.getConnection();
            try{
                statement = connection.prepareStatement(SQL_FOR_GET_MEMBER_NAME_BY_ID);
                statement.setInt(1, memberId);
                statement.execute();
                try{
                    resultSet = statement.getResultSet();
                    if(resultSet.next()){
                        result = resultSet.getString("member_name");
                    }
                } catch (SQLException exc){
                    LOG.error(exc);
                    throw new DAOException(exc);
                } finally {
                    if(resultSet != null){
                        resultSet.close();
                    }
                }
            } catch (SQLException exc){
                LOG.error(exc);
                throw new DAOException(exc);
            } finally {
                if(statement != null){
                    statement.close();
                }
            }
        } catch (SQLException exc){
            LOG.error(exc);
            throw new DAOException(exc);
        } finally {
            if(connection != null){
                pool.returnConnectionToPool(connection);
            }
        }

        return result;
    }

    @Override
    public Member addMember(Member member) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = pool.getConnection();
            try {
                statement = connection.prepareStatement(SQL_FOR_ADD_MEMBER);
                statement.setInt(1, member.getLeagueId());
                statement.setString(2, member.getName());
                statement.executeUpdate();
            } catch (SQLException exc){
                LOG.error(exc);
                throw new DAOException(exc);
            } finally {
                if(statement != null){
                    statement.close();
                }
            }
        } catch (SQLException exc){
            LOG.error(exc);
            throw new DAOException(exc);
        } finally {
            if(connection != null){
                pool.returnConnectionToPool(connection);
            }
        }
        return member;
    }
}
