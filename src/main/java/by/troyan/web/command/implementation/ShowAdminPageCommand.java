package by.troyan.web.command.implementation;

import by.troyan.web.command.CommandEnum;
import by.troyan.web.command.ICommand;
import by.troyan.web.command.exception.CommandException;
import by.troyan.web.command.factory.CommandFactory;
import by.troyan.web.entity.User;
import by.troyan.web.exception.UnauthorizedException;
import by.troyan.web.service.UserService;
import by.troyan.web.service.exception.ServiceException;
import by.troyan.web.service.factory.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowAdminPageCommand implements ICommand {
    private final static Logger LOG = LogManager.getLogger(ShowAdminPageCommand.class);
    private UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, CommandException, UnauthorizedException {
        checkRoots(req, new User.Role[]{User.Role.ADMINISTRATOR});
        req.setAttribute("tab_classes", new String[] {"", "", "", "active"});
        CommandFactory.getFactory().createCommand(CommandEnum.ADD_CATEGORIES_TO_REQUEST).execute(req, resp);
        try{
            req.setAttribute("users", userService.getAllUsers());
        } catch (ServiceException exc){
            LOG.error(exc);
            throw new CommandException(exc);
        }
        req.getRequestDispatcher("admin_page.jsp").forward(req, resp);
    }
}
