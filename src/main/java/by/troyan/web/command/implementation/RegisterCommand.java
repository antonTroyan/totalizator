package by.troyan.web.command.implementation;

import by.troyan.web.command.CommandEnum;
import by.troyan.web.command.ICommand;
import by.troyan.web.command.exception.CommandException;
import by.troyan.web.command.factory.CommandFactory;
import by.troyan.web.entity.User;
import by.troyan.web.exception.UnauthorizedException;
import by.troyan.web.exception.UserException;
import by.troyan.web.service.UserService;
import by.troyan.web.service.exception.ServiceException;
import by.troyan.web.service.factory.ServiceFactory;
import by.troyan.web.support.MessageLocalizer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterCommand implements ICommand {
    private final static Logger LOG = LogManager.getLogger(RegisterCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException,
            CommandException, UnauthorizedException {
        UserService userService = ServiceFactory.getInstance().getUserService();
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("password-confirm");
        String email = req.getParameter("email");
        User user;
        try {
            user = userService.registerUser(login, password, confirmPassword, email);
        } catch (ServiceException exc){
            LOG.error(exc);
            throw new CommandException(exc);
        } catch (UserException exc){
            req.setAttribute("error", MessageLocalizer.getLocalizedForCurrentLocaleMessage(exc.getMessage(), req));
            req.setAttribute("user", exc.getUser());
            CommandFactory.getFactory().createCommand(CommandEnum.SHOW_REGISTRATION_PAGE).execute(req, resp);
        }
        req.setAttribute("success", MessageLocalizer.getLocalizedForCurrentLocaleMessage("success.register", req));
        CommandFactory.getFactory().createCommand(CommandEnum.SHOW_REGISTRATION_PAGE).execute(req, resp);
    }
}
