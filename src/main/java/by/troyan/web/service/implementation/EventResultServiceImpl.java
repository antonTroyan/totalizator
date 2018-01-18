package by.troyan.web.service.implementation;

import by.troyan.web.dao.*;
import by.troyan.web.dao.exception.DAOException;
import by.troyan.web.dao.factory.DAOFactory;
import by.troyan.web.entity.Event;
import by.troyan.web.entity.EventResult;
import by.troyan.web.entity.Member;
import by.troyan.web.entity.Rate;
import by.troyan.web.exception.EventResultException;
import by.troyan.web.service.EventResultService;
import by.troyan.web.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * EventResultService implementation. Describe method works with EventResults.
 */

public class EventResultServiceImpl implements EventResultService {
    private final static Logger LOG = LogManager.getLogger(EventResultServiceImpl.class);
    private static final EventResultServiceImpl instance = new EventResultServiceImpl();
    private static final int STATISTIC_MAX_SCORE = 10;

    private EventResultDAO eventResultDAO;
    private EventDAO eventDAO;
    private RateDAO rateDAO;
    private UserDAO userDAO;
    private MemberDAO memberDAO;

    public static EventResultService getInstance(){
        return instance;
    }

    private EventResultServiceImpl(){
        eventResultDAO = DAOFactory.getFactory().getEventResultDAO();
        eventDAO = DAOFactory.getFactory().getEventDAO();
        rateDAO = DAOFactory.getFactory().getRateDAO();
        userDAO = DAOFactory.getFactory().getUserDAO();
        memberDAO = DAOFactory.getFactory().getMemberDAO();
    }

    private int checkInt(String stringValue, EventResultException eventResultException, String errorMessage){
        int intValue;
        try{
            intValue = Integer.parseInt(stringValue);
            return intValue;
        } catch (NumberFormatException exc){
            eventResultException.addErrorMessage(errorMessage);
            return 0;
        }
    }


    @Override
    public EventResult addRandomResultToEvent(String eventId) throws ServiceException, EventResultException {
        EventResult eventResult = new EventResult();
        EventResultException eventResultException = new EventResultException(eventResult);
        int eventIdInteger = checkInt(eventId, eventResultException, "err.event-id-is-invalid");
        eventResult.setEventId(eventIdInteger);
        eventResult = determineWinnerAndLooser(eventIdInteger, eventResult);

        if(eventResultException.getErrorMessageList().size() > 0){
            throw eventResultException;
        }
        try{
            eventResultDAO.addEventResult(eventResult);
            eventDAO.finishEvent(eventResult.getEventId());
            distributePrize(eventResult);
        } catch (DAOException exc){
            LOG.error(exc);
            throw new ServiceException(exc);
        }

        return eventResult;
    }


    private int randomDetermineScore(){
        Random randomScore = new Random();
        int result;
        result = randomScore.nextInt(STATISTIC_MAX_SCORE);
        return result;
    }

    private EventResult determineWinnerAndLooser(int eventId, EventResult eventResult) throws ServiceException {

        try {
            List <Member> members = memberDAO.getMembersByEvent(eventId);

            int firstResult = randomDetermineScore();
            int secondResult = randomDetermineScore();

            if (firstResult > secondResult) {
                eventResult.setWinnerScore(firstResult);
                eventResult.setLoserScore(secondResult);
                eventResult.setWinnerId(members.get(0).getId());
                eventResult.setLoserId(members.get(1).getId());
            } else {
                eventResult.setWinnerScore(secondResult);
                eventResult.setLoserScore(firstResult);
                eventResult.setWinnerId(members.get(1).getId());
                eventResult.setLoserId(members.get(0).getId());
            }

        } catch (DAOException exc) {
            LOG.error(exc);
            throw new ServiceException(exc);
        }

        return eventResult;
    }

//    private void distributePrize(EventResult eventResult){
//        (new Thread(() -> {
//            try {
//                BigDecimal fullMoneyAmount = rateDAO.getFullMoneyAmountForEvent(eventResult.getEventId());
//                List<Rate> allRateList = rateDAO.getRatesForEvent(eventResult.getEventId());
//                List<Rate> winRateList = new ArrayList<Rate>();
//                for (Rate rate : allRateList) {
//                    if(checkWin(rate, eventResult)){
//                        winRateList.add(rate);
//                    }
//                }
//                BigDecimal moneyPerPerson = fullMoneyAmount
//                        .divide(BigDecimal.valueOf(winRateList.size()), 2, BigDecimal.ROUND_FLOOR);
//
//                for(Rate rate : allRateList){
//                    rate.setWin(BigDecimal.valueOf(0.0));
//                }
//                for(Rate rate : winRateList){
//                    rate.setWin(moneyPerPerson);
//                }
//                for(Rate rate : allRateList){
//                    rateDAO.setWinForRate(rate);
//                    userDAO.fillUpBalanceForUser(rate.getUserId(), rate.getWin());
//                }
//            } catch (Exception exc){
//                LOG.error(exc);
//            }
//        })).run();
//    }


    private void distributePrize(EventResult eventResult){

        (new Thread(() -> {
            try {
                List<Rate> allRateList = rateDAO.getRatesForEvent(eventResult.getEventId());
                List<Rate> winRateList = new ArrayList<>();

                int eventId = eventResult.getEventId();
                Event event = eventDAO.getEventById(eventId);
                double coefficient = event.getCoefficient();

                for (Rate rate : allRateList) {
                    if(checkWin(rate, eventResult)){
                        winRateList.add(rate);
                    }
                }
                for(Rate rate : allRateList){
                    rate.setWin(BigDecimal.valueOf(0.0));
                }
                for(Rate rate : winRateList){
                    BigDecimal rateSum = rate.getSum();
                    BigDecimal prizeSum =rateSum.multiply(BigDecimal.valueOf(coefficient));
                    rate.setWin(prizeSum);
                }
                for(Rate rate : allRateList){
                    rateDAO.setWinForRate(rate);
                    userDAO.fillUpBalanceForUser(rate.getUserId(), rate.getWin());
                }
            } catch (Exception exc){
                LOG.error(exc);
            }
        })).run();
    }


    private boolean checkWin(Rate rate, EventResult eventResult){
        switch (rate.getType()){
            case Rate.WIN:
                return eventResult.getWinnerId() == rate.getMember1Id();
            case Rate.DRAW:
                return eventResult.getLoserScore() == eventResult.getWinnerScore();
            case Rate.EXACT_SCORE:
                if(eventResult.getWinnerId() == rate.getMember1Id()){
                    return (eventResult.getWinnerScore() == rate.getMember1Score()) &&
                            (eventResult.getLoserScore() == rate.getMember2Score());
                } else{
                    return (eventResult.getWinnerScore() == rate.getMember2Score()) &&
                            (eventResult.getLoserScore() == rate.getMember1Score());
                }
        }
        return false;
    }
}
