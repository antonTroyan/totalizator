package by.troyan.web.dao;

import by.troyan.web.dao.exception.DAOException;
import by.troyan.web.entity.Member;

import java.util.List;

/**
 * MemberDAO interface. Methods work with members of competitions.
 */

public interface MemberDAO {

    /**
     * Used to get List of Members from database by leagueId.
     * @param leagueId
     * @return List of Members
     */
    List<Member> getMembersByLeague(int leagueId) throws DAOException;

    /**
     * Used to get List of Members from database by evenId.
     * @param eventId
     * @return List of Members
     */
    List<Member> getMembersByEvent(int eventId) throws DAOException;

    /**
     * Used to add Members to special event in database by evenId.
     * @param eventId, memberIds
     */
    void attachMembersToEvent(List<Integer> memberIds, int eventId) throws DAOException;

    /**
     * Used to get member name by it`s memberId.
     * @param memberId - id of member
     * @return String of member name
     */
    String getMemberNameById(int memberId) throws DAOException;

    /**
     * Used to add some Member in the database.
     * @param member - object Member
     * @return Member object
     */
    Member addMember(Member member) throws DAOException;
}
