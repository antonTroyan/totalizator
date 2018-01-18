package by.troyan.web.command.implementation;

import by.troyan.web.command.CommandEnum;
import by.troyan.web.command.ICommand;
import by.troyan.web.command.exception.CommandException;
import by.troyan.web.command.factory.CommandFactory;
import by.troyan.web.exception.UnauthorizedException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeLocaleCommand implements ICommand {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException, CommandException, UnauthorizedException {
        req.getSession().setAttribute("locale", req.getParameter("locale"));
        CommandFactory.getFactory().createCommand(CommandEnum.SHOW_MAIN_PAGE).execute(req, resp);
    }
}
