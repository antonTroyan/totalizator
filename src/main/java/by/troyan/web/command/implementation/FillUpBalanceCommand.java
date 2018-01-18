package by.troyan.web.command.implementation;

import by.troyan.web.command.CommandEnum;
import by.troyan.web.command.ICommand;
import by.troyan.web.command.exception.CommandException;
import by.troyan.web.command.factory.CommandFactory;
import by.troyan.web.entity.User;
import by.troyan.web.exception.OperationException;
import by.troyan.web.exception.UnauthorizedException;
import by.troyan.web.service.PaySystemService;
import by.troyan.web.service.exception.ServiceException;
import by.troyan.web.service.factory.ServiceFactory;
import by.troyan.web.support.MessageLocalizer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FillUpBalanceCommand implements ICommand {
    private final static Logger LOG = LogManager.getLogger(FillUpBalanceCommand.class);
    private PaySystemService paySystemService = ServiceFactory.getInstance().getPaySystemService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, CommandException, UnauthorizedException {
        checkRoots(req, new User.Role[]{User.Role.ADMINISTRATOR, User.Role.BOOKMAKER, User.Role.USER});
        String cardNumber = req.getParameter("card-number");
        String cardCode = req.getParameter("card-code");
        String validityDate = req.getParameter("validity-date");
        String amount = req.getParameter("amount");
        try {
            paySystemService.fillUpBalance((String)req.getSession().getAttribute("username"), cardNumber, validityDate, cardCode, amount);
        }
        catch(ServiceException exc){
            LOG.error(exc);
            throw new CommandException(exc);
        }
        catch (OperationException exc){
            LOG.error(exc);
            req.setAttribute("error", MessageLocalizer.getLocalizedForCurrentLocaleMessage(exc.getErrorMessageList(), req));
            req.setAttribute("operation", exc.getOperation());
            CommandFactory.getFactory().createCommand(CommandEnum.SHOW_FILL_UP_BALANCE_PAGE).execute(req, resp);
        }
        req.setAttribute("success", MessageLocalizer.getLocalizedForCurrentLocaleMessage("success.fill-up", req));
        CommandFactory.getFactory().createCommand(CommandEnum.SHOW_FILL_UP_BALANCE_PAGE).execute(req, resp);
    }
}
