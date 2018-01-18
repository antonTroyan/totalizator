package by.troyan.web.command.implementation;

import by.troyan.web.command.CommandEnum;
import by.troyan.web.command.ICommand;
import by.troyan.web.command.exception.CommandException;
import by.troyan.web.command.factory.CommandFactory;
import by.troyan.web.entity.User;
import by.troyan.web.exception.MemberException;
import by.troyan.web.exception.UnauthorizedException;
import by.troyan.web.service.MemberService;
import by.troyan.web.service.exception.ServiceException;
import by.troyan.web.service.factory.ServiceFactory;
import by.troyan.web.support.MessageLocalizer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddMemberCommand implements ICommand {
    private final static Logger LOG = LogManager.getLogger(AddMemberCommand.class);
    private final MemberService memberService = ServiceFactory.getInstance().getMemberService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, CommandException, UnauthorizedException {
        checkRoots(req, new User.Role[]{User.Role.ADMINISTRATOR});
        try {
            memberService.addMember(req.getParameter("name"),
                    req.getParameter("category-id"), req.getParameter("league-id"));
        }
        catch(ServiceException exc){
            LOG.error(exc);
            throw new CommandException(exc);
        }
        catch (MemberException exc){
            LOG.error(exc);
            req.setAttribute("error", MessageLocalizer.getLocalizedForCurrentLocaleMessage(exc.getErrorMessageList(), req));
            req.setAttribute("member", exc.getMember());
            CommandFactory.getFactory().createCommand(CommandEnum.SHOW_ADD_MEMBER_PAGE).execute(req, resp);
        }
        req.setAttribute("success", MessageLocalizer.getLocalizedForCurrentLocaleMessage("success.member-is-added", req));
        CommandFactory.getFactory().createCommand(CommandEnum.SHOW_ADD_MEMBER_PAGE).execute(req, resp);
    }
}
