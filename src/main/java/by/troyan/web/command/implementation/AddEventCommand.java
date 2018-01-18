package by.troyan.web.command.implementation;


import by.troyan.web.command.CommandEnum;
import by.troyan.web.command.ICommand;
import by.troyan.web.command.exception.CommandException;
import by.troyan.web.command.factory.CommandFactory;
import by.troyan.web.entity.User;
import by.troyan.web.exception.EventException;
import by.troyan.web.exception.UnauthorizedException;
import by.troyan.web.service.EventService;
import by.troyan.web.service.exception.ServiceException;
import by.troyan.web.service.factory.ServiceFactory;
import by.troyan.web.support.MessageLocalizer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddEventCommand implements ICommand {
    private final static Logger LOG = LogManager.getLogger(AddEventCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException,
            CommandException, UnauthorizedException {
        checkRoots(req, new User.Role[]{User.Role.ADMINISTRATOR});
        EventService eventService = ServiceFactory.getInstance().getEventService();
        String name = req.getParameter("name");
        String leagueId = req.getParameter("league-id");
        String liveTranslationLink = req.getParameter("liveTranslation");
        String date = req.getParameter("date");
        String rateTypes = getRateTypes(req);
        List<Integer> memberIds = getMemberIds(req);
        try {
            eventService.addEvent(name, leagueId, rateTypes, liveTranslationLink, date, memberIds);
        }
        catch(ServiceException exc){
            LOG.error(exc);
            throw new CommandException(exc);
        }
        catch (EventException exc){
            LOG.error(exc);
            req.setAttribute("error", MessageLocalizer.getLocalizedForCurrentLocaleMessage(exc.getErrorMessageList(), req));
            req.setAttribute("event", exc.getEvent());
            CommandFactory.getFactory().createCommand(CommandEnum.SHOW_ADD_EVENT_PAGE).execute(req, resp);
        }
        req.setAttribute("success", MessageLocalizer.getLocalizedForCurrentLocaleMessage("success.add-event", req));
        CommandFactory.getFactory().createCommand(CommandEnum.SHOW_ADD_EVENT_PAGE).execute(req, resp);
    }

    private String getRateTypes(HttpServletRequest req){
        StringBuilder sb = new StringBuilder();
        List<String> rateTypes = new ArrayList<>();
        if(req.getParameter("winRate") != null){
            rateTypes.add("WIN");
        }
        if(req.getParameter("drawRate") != null){
            rateTypes.add("DRAW");
        }
        if(req.getParameter("firstGoalRate") != null){
            rateTypes.add("FIRST_GOAL");
        }
        if(req.getParameter("exactScoreRate") != null){
            rateTypes.add("EXACT_SCORE");
        }
        if(!rateTypes.isEmpty()) {
            sb.append(rateTypes.get(0));
            for (int i = 1; i < rateTypes.size(); i++) {
                sb.append(',');
                sb.append(rateTypes.get(i));
            }
        }
        return sb.toString();
    }

    private List<Integer> getMemberIds(HttpServletRequest req){
        List<Integer> memberIds = new ArrayList<>();
        String memberSelectName = "member-select-";
        int i = 1;
        String memberId;
        try {
            while ((memberId = req.getParameter(memberSelectName + i)) != null) {
                memberIds.add(Integer.parseInt(memberId));
                i++;
            }
        } catch (NumberFormatException exc){
            LOG.error(exc);
        }
        return memberIds;
    }
}
