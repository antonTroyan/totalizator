package by.troyan.web.controller;

import by.troyan.web.command.CommandEnum;
import by.troyan.web.command.ICommand;
import by.troyan.web.command.factory.CommandFactory;
import by.troyan.web.exception.UnauthorizedException;
import by.troyan.web.support.MessageLocalizer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Main controller servlet. Using method processRequest(HttpServletRequest req, HttpServletResponse resp)
 * it receives command from attribute, creates it like object and command to run it.
 */

public class MainServlet extends HttpServlet {
    private static final CommandFactory COMMAND_FACTORY = CommandFactory.getFactory();
    private final static Logger LOG = LogManager.getLogger(MainServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    /**
     * Used to run command. Receives attribute command, use method getEnum(commandName) to receive
     * command Object, then start it using method execute(req, resp).
     *
     * @param  req, resp
     *        1 req. Request from user to some command
     *        2 resp. Response to user or another command
     */
    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ICommand command;
        String commandName = req.getParameter("command");

        try {
            CommandEnum commandEnum = CommandEnum.getEnum(commandName);
            command = COMMAND_FACTORY.createCommand(commandEnum);
            command.execute(req, resp);
        } catch (UnauthorizedException exc){
            LOG.error(exc);
            req.setAttribute("message", exc.getMessage());
            req.getRequestDispatcher("error_page.jsp").forward(req, resp);
        } catch (Exception exc){
            exc.printStackTrace();
            LOG.error(exc);
            req.setAttribute("message", MessageLocalizer.getLocalizedForCurrentLocaleMessage(exc.getMessage(), req));
            req.getRequestDispatcher("error_page.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
}
