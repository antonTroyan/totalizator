package by.troyan.web.command.implementation;

import by.troyan.web.command.CommandEnum;
import by.troyan.web.command.ICommand;
import by.troyan.web.command.exception.CommandException;
import by.troyan.web.command.factory.CommandFactory;
import by.troyan.web.entity.Rate;
import by.troyan.web.entity.User;
import by.troyan.web.exception.RateException;
import by.troyan.web.exception.UnauthorizedException;
import by.troyan.web.service.RateService;
import by.troyan.web.service.exception.ServiceException;
import by.troyan.web.service.factory.ServiceFactory;
import by.troyan.web.support.MessageLocalizer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MakeRateCommand implements ICommand {
    private final static Logger LOG = LogManager.getLogger(MakeRateCommand.class);
    private RateService rateService = ServiceFactory.getInstance().getRateService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, CommandException, UnauthorizedException {
        checkRoots(req, new User.Role[]{User.Role.ADMINISTRATOR, User.Role.BOOKMAKER, User.Role.USER});
        String type = req.getParameter("rate-type");
        String money = req.getParameter("money");
        String eventId = req.getParameter("event-id");
        String member1Id = req.getParameter("member-1");
        String member2Id = req.getParameter("member-2");
        String member1Score = req.getParameter("member-1-score");
        String member2Score = req.getParameter("member-2-score");
        String username = (String)req.getSession().getAttribute("username");
        Rate rate = null;
        try {
            rate = rateService.makeRate(type, eventId, username, money, member1Id, member1Score, member2Id, member2Score);
        }
        catch(ServiceException exc){
            LOG.error(exc);
            throw new CommandException(exc);
        }
        catch (RateException exc){
            LOG.error(exc);
            req.setAttribute("error", MessageLocalizer.getLocalizedForCurrentLocaleMessage(exc.getErrorMessageList(), req));
            req.setAttribute("rate", exc.getRate());
            CommandFactory.getFactory().createCommand(CommandEnum.SHOW_MAKE_RATE_PAGE).execute(req, resp);
        }
        req.setAttribute("rate", eventId);
        req.setAttribute("success", MessageLocalizer.getLocalizedForCurrentLocaleMessage("success.rate-maked", req));
        req.setAttribute("rate", rate);
        CommandFactory.getFactory().createCommand(CommandEnum.SHOW_MAKE_RATE_PAGE).execute(req, resp);
    }
}
