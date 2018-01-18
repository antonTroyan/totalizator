package by.troyan.web.command.implementation;

import by.troyan.web.command.CommandEnum;
import by.troyan.web.command.ICommand;
import by.troyan.web.command.exception.CommandException;
import by.troyan.web.command.factory.CommandFactory;
import by.troyan.web.entity.User;
import by.troyan.web.exception.UnauthorizedException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowMakeRatePageCommand implements ICommand {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, CommandException, UnauthorizedException {
        checkRoots(req, new User.Role[]{User.Role.USER, User.Role.ADMINISTRATOR, User.Role.BOOKMAKER});
        CommandFactory.getFactory().createCommand(CommandEnum.ADD_CATEGORIES_TO_REQUEST).execute(req, resp);
        req.setAttribute("eventId", req.getParameter("eventId"));
        req.getRequestDispatcher("make_rate_page.jsp").forward(req, resp);
    }
}
