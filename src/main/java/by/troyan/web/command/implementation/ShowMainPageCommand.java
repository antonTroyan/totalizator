package by.troyan.web.command.implementation;

import by.troyan.web.command.CommandEnum;
import by.troyan.web.command.ICommand;
import by.troyan.web.command.exception.CommandException;
import by.troyan.web.command.factory.CommandFactory;
import by.troyan.web.exception.UnauthorizedException;
import by.troyan.web.service.EventService;
import by.troyan.web.service.exception.ServiceException;
import by.troyan.web.service.factory.ServiceFactory;
import by.troyan.web.support.JspPathes;
import by.troyan.web.support.PaginationObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowMainPageCommand implements ICommand {
    private EventService eventService = ServiceFactory.getInstance().getEventService();

    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, CommandException, UnauthorizedException {
        CommandFactory.getFactory().createCommand(CommandEnum.ADD_CATEGORIES_TO_REQUEST).execute(req, resp);
        req.setAttribute("tab_classes", new String[] {"active", "", "", ""});
        req.setAttribute("command", CommandEnum.SHOW_MAIN_PAGE.getValue());
        int page;
        try{
            page = Integer.parseInt(req.getParameter("page"));
        } catch (NumberFormatException exc){
            page = PaginationObject.DEFAULT_PAGE;
        }
        try {
            req.setAttribute("events", eventService.getAllNotEndedEvents(page));
        }
        catch (ServiceException exc){
            throw new CommandException(exc);
        }
        req.getRequestDispatcher(JspPathes.MAIN_PAGE).forward(req, resp);
    }
}
