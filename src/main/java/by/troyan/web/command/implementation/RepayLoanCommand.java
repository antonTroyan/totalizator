package by.troyan.web.command.implementation;

import by.troyan.web.command.CommandEnum;
import by.troyan.web.command.ICommand;
import by.troyan.web.command.exception.CommandException;
import by.troyan.web.command.factory.CommandFactory;
import by.troyan.web.database.TotalizatorPropertiesReader;
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
import java.util.Properties;


public class RepayLoanCommand implements ICommand {
    private final static Logger LOG = LogManager.getLogger(TakeLoanCommand.class);
    private PaySystemService paySystemService = ServiceFactory.getInstance().getPaySystemService();


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException,
            CommandException, UnauthorizedException {
        checkRoots(req, new User.Role[]{User.Role.ADMINISTRATOR, User.Role.BOOKMAKER, User.Role.USER});
        String username;

        try {
            username = (String) req.getSession().getAttribute("username");

            Properties properties = TotalizatorPropertiesReader.getTotalizatorProperties();
            String enterpriseCardNumber = properties.getProperty("enterpriseCardNumber");
            String enterpriseCardValidityDate = properties.getProperty("enterpriseCardValidityDate");
            String standardCreditAmount = properties.getProperty("standardCreditAmount");

            paySystemService.withdrawMoney(username, enterpriseCardNumber, enterpriseCardValidityDate, standardCreditAmount);
            paySystemService.repayLoan(username);

        } catch (ServiceException exc) {
            LOG.error(exc);
            throw new CommandException(exc);
        }
        catch (OperationException exc){
            LOG.error(exc);
            req.setAttribute("error", MessageLocalizer.getLocalizedForCurrentLocaleMessage(exc.getErrorMessageList(), req));
            req.setAttribute("operation", exc.getOperation());
            CommandFactory.getFactory().createCommand(CommandEnum.SHOW_FILL_UP_BALANCE_PAGE).execute(req, resp);
        }
        resp.sendRedirect("main?command=showPersonalPage");
    }
}
