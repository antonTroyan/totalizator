package by.troyan.web.command.implementation;

import by.troyan.web.command.CommandEnum;
import by.troyan.web.command.ICommand;
import by.troyan.web.command.exception.CommandException;
import by.troyan.web.command.factory.CommandFactory;
import by.troyan.web.entity.User;
import by.troyan.web.exception.LeagueException;
import by.troyan.web.exception.UnauthorizedException;
import by.troyan.web.service.LeagueService;
import by.troyan.web.service.exception.ServiceException;
import by.troyan.web.service.factory.ServiceFactory;
import by.troyan.web.support.MessageLocalizer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddLeagueCommand implements ICommand {
    private final static Logger LOG = LogManager.getLogger(AddLeagueCommand.class);
    private final LeagueService leagueService = ServiceFactory.getInstance().getLeagueService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, CommandException, UnauthorizedException {
        checkRoots(req, new User.Role[]{User.Role.ADMINISTRATOR});
        try {
            leagueService.addLeague(req.getParameter("name"), req.getParameter("category-id"));
        }
        catch(ServiceException exc){
            LOG.error(exc);
            throw new CommandException(exc);
        }
        catch (LeagueException exc){
            LOG.error(exc);
            req.setAttribute("error", MessageLocalizer.getLocalizedForCurrentLocaleMessage(exc.getErrorMessageList(), req));
            req.setAttribute("league", exc.getLeague());
            CommandFactory.getFactory().createCommand(CommandEnum.SHOW_ADD_LEAGUE_PAGE).execute(req, resp);
        }
        req.setAttribute("success", MessageLocalizer.getLocalizedForCurrentLocaleMessage("success.league-is-added", req));
        CommandFactory.getFactory().createCommand(CommandEnum.SHOW_ADD_LEAGUE_PAGE).execute(req, resp);
    }
}
