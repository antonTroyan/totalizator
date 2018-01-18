package by.troyan.web.command.implementation;

import by.troyan.web.command.CommandEnum;
import by.troyan.web.command.ICommand;
import by.troyan.web.command.exception.CommandException;
import by.troyan.web.command.factory.CommandFactory;
import by.troyan.web.entity.Event;
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


public class ShowEventPageCommand implements ICommand {
    private final static Logger LOG = LogManager.getLogger(ShowEventPageCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, CommandException, UnauthorizedException {
        CommandFactory.getFactory().createCommand(CommandEnum.ADD_CATEGORIES_TO_REQUEST).execute(req, resp);
        EventService eventService = ServiceFactory.getInstance().getEventService();
        String eventId = req.getParameter("eventId");
        if(eventId == null){
            eventId = (String)req.getAttribute("eventId");
        }
        int intEventId = Integer.parseInt(eventId);
        try {
            Event event = eventService.getEventById(intEventId);
            req.setAttribute("event", event);
        } catch (ServiceException exc){
            LOG.error(exc);
            throw new CommandException(exc);
        }
        req.getRequestDispatcher("event_page.jsp").forward(req, resp);
    }
}
