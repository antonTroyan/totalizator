package by.troyan.web.service.implementation;

import by.troyan.web.dao.MemberDAO;
import by.troyan.web.dao.exception.DAOException;
import by.troyan.web.dao.factory.DAOFactory;
import by.troyan.web.entity.Member;
import by.troyan.web.exception.MemberException;
import by.troyan.web.service.MemberService;
import by.troyan.web.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * MemberService implementation. Describe method works with Members.
 */

public class MemberServiceImpl implements MemberService {

    private final static Logger LOG = LogManager.getLogger(MemberServiceImpl.class);
    private static final MemberServiceImpl INSTANCE = new MemberServiceImpl();
    private MemberDAO memberDAO;

    public static MemberServiceImpl getInstance(){
        return INSTANCE;
    }

    private MemberServiceImpl(){
        memberDAO = DAOFactory.getFactory().getMemberDAO();
    }

    /**
     * Used get list of members with one league.
     * @param leagueId league id
     * @return  list of Members
     */
    @Override
    public List<Member> getMembersByLeague(int leagueId) throws ServiceException {
        try{
            return memberDAO.getMembersByLeague(leagueId);
        } catch (DAOException exc){
            LOG.error(exc);
            throw new ServiceException(exc);
        }
    }

    /**
     * Used get list of members by eventId.
     * @param eventId event id
     * @return  list of Members
     */
    @Override
    public List<Member> getMembersByEvent(int eventId) throws ServiceException {
        try{
            return memberDAO.getMembersByEvent(eventId);
        } catch (DAOException exc){
            LOG.error(exc);
            throw new ServiceException(exc);
        }
    }

    /**
     * Used add member.
     * @param name member name
     * @param categoryId id of category
     * @param leagueId id of league
     * @return  Member object
     */
    @Override
    public Member addMember(String name, String categoryId, String leagueId) throws ServiceException, MemberException {
        Member member = new Member();
        MemberException memberException = new MemberException(member);
        if((name == null) || name.isEmpty()){
            memberException.addMessage("err.name-is-invalid");
        }
        member.setName(name);
        int intCategoryId = 0;
        try{
            intCategoryId = Integer.parseInt(categoryId);
        } catch (NumberFormatException exc){
            LOG.error(exc);
        }
        member.setCategoryId(intCategoryId);
        int intLeagueId = 0;
        try{
            intLeagueId = Integer.parseInt(leagueId);
        } catch (NumberFormatException exc){
            LOG.error(exc);
        }
        member.setLeagueId(intLeagueId);
        if(memberException.getErrorMessageList().size() > 0){
            throw memberException;
        }
        try {
            return memberDAO.addMember(member);
        } catch (DAOException exc){
            LOG.error(exc);
            throw new ServiceException(exc);
        }
    }
}
