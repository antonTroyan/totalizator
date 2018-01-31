package by.troyan.web.service;

import by.troyan.web.entity.Member;
import by.troyan.web.exception.MemberException;
import by.troyan.web.service.exception.ServiceException;

import java.util.List;

/**
 * MemberService interface. Describe methods for working with Members.
 */

public interface MemberService {

    /**
     * Used get list of members with one league.
     * @param leagueId league id
     * @return  list of Members
     */
    List<Member> getMembersByLeague(int leagueId) throws ServiceException;

    /**
     * Used get list of members by eventId.
     * @param eventId event id
     * @return  list of Members
     */
    List<Member> getMembersByEvent(int eventId) throws ServiceException;

    /**
     * Used add member.
     * @param name member name
     * @param categoryId id of category
     * @param leagueId id of league
     * @return  Member object
     */
    Member addMember(String name, String categoryId, String leagueId) throws ServiceException, MemberException;
}
