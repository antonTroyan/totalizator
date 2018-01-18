package by.troyan.web.dao;

import by.troyan.web.dao.exception.DAOException;
import by.troyan.web.entity.Member;

import java.util.List;

/**
 * MemberDAO interface. Methods work with members of competitions.
 */

public interface MemberDAO {
    List<Member> getMembersByLeague(int leagueId) throws DAOException;

    List<Member> getMembersByEvent(int eventId) throws DAOException;

    void attachMembersToEvent(List<Integer> memberIds, int eventId) throws DAOException;

    String getMemberNameById(int memberId) throws DAOException;

    Member addMember(Member member) throws DAOException;
}
