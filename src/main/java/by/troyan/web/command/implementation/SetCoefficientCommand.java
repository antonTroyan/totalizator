package by.troyan.web.command.implementation;

import by.troyan.web.command.CommandEnum;
import by.troyan.web.command.ICommand;
import by.troyan.web.command.exception.CommandException;
import by.troyan.web.command.factory.CommandFactory;
import by.troyan.web.entity.User;
import by.troyan.web.exception.UnauthorizedException;
import by.troyan.web.service.EventService;
import by.troyan.web.service.exception.ServiceException;
import by.troyan.web.service.factory.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SetCoefficientCommand implements ICommand {
    private final static Logger LOG = LogManager.getLogger(SetCoefficientCommand.class);
    private EventService eventService = ServiceFactory.getInstance().getEventService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, CommandException, UnauthorizedException {
        checkRoots(req, new User.Role[]{User.Role.BOOKMAKER});
        String eventId = req.getParameter("eventId");
        String coefficientValue = req.getParameter("coefficient");

        try{
            eventService.setCoefficientToEvent(eventId, coefficientValue);
        } catch (ServiceException exc){
            LOG.error(exc);
            throw new CommandException(exc);
        }
        req.setAttribute("eventId", eventId);
        CommandFactory.getFactory().createCommand(CommandEnum.SHOW_EVENT_PAGE).execute(req, resp);
    }
}
