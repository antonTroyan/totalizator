package by.troyan.web.service.implementation;

import by.troyan.web.dao.EventDAO;
import by.troyan.web.dao.EventResultDAO;
import by.troyan.web.dao.MemberDAO;
import by.troyan.web.dao.exception.DAOException;
import by.troyan.web.dao.factory.DAOFactory;
import by.troyan.web.entity.Event;
import by.troyan.web.entity.EventResult;
import by.troyan.web.exception.EventException;
import by.troyan.web.service.EventService;
import by.troyan.web.service.exception.ServiceException;
import by.troyan.web.support.DateParser;
import by.troyan.web.support.PaginationObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

/**
 * EventService implementation. Describe method works with Events.
 */

public class EventServiceImpl implements EventService {
    private final static Logger LOG = LogManager.getLogger(EventServiceImpl.class);
    private static final EventServiceImpl instance = new EventServiceImpl();

    private EventDAO eventDAO;
    private MemberDAO memberDAO;
    private EventResultDAO eventResultDAO;

    public static EventServiceImpl getInstance(){
        return instance;
    }

    private EventServiceImpl(){
        eventDAO = DAOFactory.getFactory().getEventDAO();
        memberDAO = DAOFactory.getFactory().getMemberDAO();
        eventResultDAO = DAOFactory.getFactory().getEventResultDAO();
    }

    @Override
    public PaginationObject<Event> getAllNotEndedEvents(int page) throws ServiceException {
        try {
            PaginationObject<Event> paginationObject = new PaginationObject<>();
            List<Event> events = eventDAO.getAllNotEndedEvents();
            paginationObject.setPageCount((int)Math.ceil(events.size() / PaginationObject.PER_PAGE));
            paginationObject.setPage(page);
            int start = (paginationObject.getPage()-1) * PaginationObject.PER_PAGE;
            int end = start + PaginationObject.PER_PAGE > events.size() ? events.size() : start + PaginationObject.PER_PAGE;
            paginationObject.setElementList(events.subList(start, end));
            return paginationObject;
        } catch (DAOException exc){
            LOG.error(exc);
            throw new ServiceException(exc);
        }
    }

    @Override
    public PaginationObject<Event> getAllNotEndedEventsSortedByDate(int page) throws ServiceException {
        try {
            PaginationObject<Event> paginationObject = new PaginationObject<>();
            List<Event> events = eventDAO.getAllNotEndedEventsSortedByDate();
            paginationObject.setPageCount((int)Math.ceil(events.size() / PaginationObject.PER_PAGE));
            paginationObject.setPage(page);
            int start = (paginationObject.getPage()-1) * PaginationObject.PER_PAGE;
            int end = start + PaginationObject.PER_PAGE > events.size() ? events.size() : start + PaginationObject.PER_PAGE;
            paginationObject.setElementList(events.subList(start, end));
            return paginationObject;
        } catch (DAOException exc){
            LOG.error(exc);
            throw new ServiceException(exc);
        }
    }

    @Override
    public PaginationObject<Event> getAllNotEndedEventsByCategoryId(String categoryId, int page) throws ServiceException {
        try {
            int intLeagueId = Integer.parseInt(categoryId);
            PaginationObject<Event> paginationObject = new PaginationObject<>();
            List<Event> events = eventDAO.getAllNotEndedEventsByCategoryId(intLeagueId);
            paginationObject.setPageCount((int)Math.ceil(events.size() / PaginationObject.PER_PAGE));
            paginationObject.setPage(page);
            int start = (paginationObject.getPage()-1) * PaginationObject.PER_PAGE;
            int end = start + PaginationObject.PER_PAGE > events.size() ? events.size() : start + PaginationObject.PER_PAGE;
            paginationObject.setElementList(events.subList(start, end));
            return paginationObject;
        } catch (DAOException | NumberFormatException exc){
            LOG.error(exc);
            throw new ServiceException(exc);
        }
    }

    @Override
    public PaginationObject<Event> getAllEndedEvents(int page) throws ServiceException {
        try {
            PaginationObject<Event> paginationObject = new PaginationObject<>();
            List<Event> events = eventDAO.getAllEndedEvents();
            paginationObject.setPageCount((int)Math.ceil((double)events.size() / PaginationObject.PER_PAGE));
            paginationObject.setPage(page);
            int start = (paginationObject.getPage()-1) * PaginationObject.PER_PAGE;
            int end = start + PaginationObject.PER_PAGE > events.size() ? events.size() : start + PaginationObject.PER_PAGE;
            paginationObject.setElementList(events.subList(start, end));
            return paginationObject;
        } catch (DAOException exc){
            LOG.error(exc);
            throw new ServiceException(exc);
        }
    }

    @Override
    public Event getEventById(int eventId) throws ServiceException{
        try{
            Event event = eventDAO.getEventById(eventId);
            event.setMembers(memberDAO.getMembersByEvent(event.getEventId()));
            EventResult eventResult = eventResultDAO.getEventResultByEvent(eventId);
            if(eventResult != null) {
                eventResult.setWinnerName(memberDAO.getMemberNameById(eventResult.getWinnerId()));
                eventResult.setLoserName(memberDAO.getMemberNameById(eventResult.getLoserId()));
            }
            event.setResult(eventResult);
            long eventTime = event.getEventTime().getTime() + event.getEventDate().getTime();
            long nowTime = new java.util.Date().getTime();
            if(!(event.getStatus().equals("FINISHED")) && (eventTime < nowTime)){
                event.setCanAddResult(true);
            }
            if((eventTime > nowTime) && !(event.getStatus().equals("FINISHED"))){
                event.setCanMakeRate(true);
            }
            return event;
        } catch (DAOException exc){
            LOG.error(exc);
            throw new ServiceException(exc);
        }
    }

    @Override
    public Event addEvent(String name, String leagueId, String rateTypes, String liveTranslationLink,
                          String date, List<Integer> memberIds)
            throws ServiceException, EventException{
        try {
            Event event = new Event();
            EventException eventException = new EventException(event);
            if(name.isEmpty() || (name == null)){
                eventException.addErrorMessage("err.event-empty-name");
            }
            event.setEventName(name);
            int intLeagueId;
            try {
                intLeagueId = Integer.parseInt(leagueId);
            }
            catch (NumberFormatException exc){
                LOG.error(exc);
                intLeagueId = 0;
            }
            event.setLeagueId(intLeagueId);
            Date sqlDate = DateParser.parse(date);
            if(sqlDate.before(Date.valueOf(LocalDate.now()))){
                eventException.addErrorMessage("err.event-old-date");
            }
            event.setEventDate(sqlDate);
            event.setLiveTranslationLink(liveTranslationLink);
            event.setRateTypes(rateTypes);
            if(!eventException.getErrorMessageList().isEmpty()){
                throw eventException;
            }
            event =  eventDAO.addEvent(event);
            memberDAO.attachMembersToEvent(memberIds, event.getEventId());
            return event;
        } catch (DAOException | ParseException exc){
            LOG.error(exc);
            throw new ServiceException(exc);
        }
    }

    @Override
    public void setCoefficientToEvent(String eventId, String coefficient) throws ServiceException {
        Event event = new Event();

        try{
            int eventIdInteger = Integer.parseInt(eventId);
            eventDAO.getEventById(eventIdInteger);
            event.setCoefficient(eventIdInteger);
            eventDAO.setEventCoefficient(eventIdInteger, Double.parseDouble(coefficient));
        } catch (DAOException exc){
            LOG.error(exc);
            throw new ServiceException(exc);
        } catch (Exception exc){
            LOG.error(exc);
        }
    }
}
