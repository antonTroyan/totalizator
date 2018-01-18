package by.troyan.web.service.implementation;

import by.troyan.web.dao.LeagueDAO;
import by.troyan.web.dao.exception.DAOException;
import by.troyan.web.dao.factory.DAOFactory;
import by.troyan.web.entity.League;
import by.troyan.web.exception.LeagueException;
import by.troyan.web.service.LeagueService;
import by.troyan.web.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * LeagueService implementation. Describe method works with Leagues.
 */

public class LeagueServiceImpl implements LeagueService {
    private final static Logger LOG = LogManager.getLogger(LeagueServiceImpl.class);
    private static final LeagueServiceImpl instance = new LeagueServiceImpl();
    private LeagueDAO leagueDAO;

    public static LeagueServiceImpl getInstance(){
        return instance;
    }

    private LeagueServiceImpl(){
        leagueDAO = DAOFactory.getFactory().getLeagueDAO();
    }

    @Override
    public List<League> getLeaguesByCategory(int categoryId) throws ServiceException {
        try {
            return leagueDAO.getLeaguesByCategory(categoryId);
        } catch (DAOException exc){
            LOG.error(exc);
            throw new ServiceException(exc);
        }
    }

    @Override
    public League addLeague(String name, String categoryId) throws ServiceException, LeagueException {
        League league = new League();
        LeagueException leagueException = new LeagueException(league);
        if((name == null) || name.isEmpty()  ){
            leagueException.addMessage("err.name-is-invalid");
        }
        league.setName(name);
        int intCategoryId = 0;
        try{
            intCategoryId = Integer.parseInt(categoryId);
        } catch (NumberFormatException exc){
            LOG.error(exc);
        }
        league.setCategoryId(intCategoryId);
        if(leagueException.getErrorMessageList().size() > 0){
            throw leagueException;
        }
        try {
            return leagueDAO.addLeague(league);
        } catch (DAOException exc){
            LOG.error(exc);
            throw new ServiceException(exc);
        }
    }
}
