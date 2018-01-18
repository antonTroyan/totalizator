package by.troyan.web.command.implementation;

import by.troyan.web.command.CommandEnum;
import by.troyan.web.command.ICommand;
import by.troyan.web.command.exception.CommandException;
import by.troyan.web.command.factory.CommandFactory;
import by.troyan.web.exception.UnauthorizedException;
import by.troyan.web.service.EventService;
import by.troyan.web.service.exception.ServiceException;
import by.troyan.web.service.factory.ServiceFactory;
import by.troyan.web.support.PaginationObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowCategoryPageCommand implements ICommand {
    private final static Logger LOG = LogManager.getLogger(ShowCategoryPageCommand.class);

    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, CommandException, UnauthorizedException {
        CommandFactory.getFactory().createCommand(CommandEnum.ADD_CATEGORIES_TO_REQUEST).execute(req, resp);
        EventService eventService = ServiceFactory.getInstance().getEventService();
        int page;
        try{
            page = Integer.parseInt(req.getParameter("page"));
        } catch (NumberFormatException exc){
            page = PaginationObject.DEFAULT_PAGE;
        }
        try {
            req.setAttribute("events", eventService.getAllNotEndedEventsByCategoryId(req.getParameter("categoryId"), page));
        }
        catch (ServiceException exc){
            LOG.error(exc);
        }
        req.setAttribute("command", CommandEnum.SHOW_CATEGORY_PAGE.getValue());
        req.getRequestDispatcher("main_page.jsp").forward(req, resp);
    }
}
