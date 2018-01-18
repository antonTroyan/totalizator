package by.troyan.web.service;

import by.troyan.web.entity.Member;
import by.troyan.web.exception.MemberException;
import by.troyan.web.service.exception.ServiceException;

import java.util.List;

/**
 * MemberService interface. Describe methods for working with Members.
 */

public interface MemberService {
    List<Member> getMembersByLeague(int leagueId) throws ServiceException;

    List<Member> getMembersByEvent(int eventId) throws ServiceException;

    Member addMember(String name, String categoryId, String leagueId) throws ServiceException, MemberException;
}
