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
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginCommand implements ICommand {
    private final static Logger LOG = LogManager.getLogger(LoginCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException,
            CommandException, UnauthorizedException {
        UserService userService = ServiceFactory.getInstance().getUserService();
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        try {
            User user = userService.login(login, password);
            if(user != null){
                HttpSession session = req.getSession();
                session.setAttribute("username", login);
                session.setAttribute("role", user.getRole().getValue());
            }
        } catch (ServiceException exc){
            LOG.error(exc);
        } catch (UserException exc){
            req.setAttribute("user", exc.getUser());
            req.setAttribute("error", MessageLocalizer.getLocalizedForCurrentLocaleMessage(exc.getMessage(), req));
            CommandFactory.getFactory().createCommand(CommandEnum.SHOW_LOGIN_PAGE).execute(req, resp);
        }

        CommandFactory.getFactory().createCommand(CommandEnum.SHOW_MAIN_PAGE).execute(req, resp);
    }
}
