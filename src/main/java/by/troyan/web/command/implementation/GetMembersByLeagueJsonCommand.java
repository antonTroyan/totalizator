package by.troyan.web.command.implementation;

import by.troyan.web.command.ICommand;
import by.troyan.web.command.exception.CommandException;
import by.troyan.web.entity.Member;
import by.troyan.web.service.MemberService;
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

public class GetMembersByLeagueJsonCommand implements ICommand {
    private final static Logger LOG = LogManager.getLogger(GetMembersByLeagueJsonCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, CommandException {
        MemberService memberService = ServiceFactory.getInstance().getMemberService();
        int leagueId = Integer.parseInt(req.getParameter("leagueId"));
        List<Member> members;
        try {
            members = memberService.getMembersByLeague(leagueId);
        } catch (ServiceException exc){
            LOG.error(exc);
            throw new CommandException(exc);
        }
        resp.setHeader("Content-type", "json");
        req.setAttribute("json", JsonSerializer.json(members));
        req.getRequestDispatcher("json.jsp").forward(req, resp);
    }

}
