package by.troyan.web.command.implementation;

import by.troyan.web.command.ICommand;
import by.troyan.web.command.exception.CommandException;
import by.troyan.web.entity.League;
import by.troyan.web.service.LeagueService;
import by.troyan.web.service.exception.ServiceException;
import by.troyan.web.service.factory.ServiceFactory;
import by.troyan.web.support.JsonSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetLeaguesByCategoryJsonCommand implements ICommand {
    private final static Logger LOG = LogManager.getLogger(GetLeaguesByCategoryJsonCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, CommandException {
        LeagueService leagueService = ServiceFactory.getInstance().getLeagueService();
        int categoryId = Integer.parseInt(req.getParameter("categoryId"));
        List<League> leagues;

        try {
            leagues = leagueService.getLeaguesByCategory(categoryId);
        } catch (ServiceException exc){
            LOG.error(exc);
            throw new CommandException(exc);
        }
        resp.setHeader("Content-type", "json");

        req.setAttribute("json", JsonSerializer.json(leagues));
        req.getRequestDispatcher("json.jsp").forward(req, resp);

    }
}
