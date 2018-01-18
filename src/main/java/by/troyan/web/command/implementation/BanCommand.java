package by.troyan.web.command.implementation;

import by.troyan.web.command.ICommand;
import by.troyan.web.command.exception.CommandException;
import by.troyan.web.entity.User;
import by.troyan.web.exception.UnauthorizedException;
import by.troyan.web.service.UserService;
import by.troyan.web.service.exception.ServiceException;
import by.troyan.web.service.factory.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BanCommand implements ICommand {
    private final static Logger LOG = LogManager.getLogger(BanCommand.class);
    private UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, CommandException, UnauthorizedException {
        checkRoots(req, new User.Role[]{User.Role.ADMINISTRATOR});
        String[] stringIdList = req.getParameter("id-list").split(",");
        List<Integer> idList = new ArrayList<>();
        for(String str : stringIdList){
            idList.add(Integer.parseInt(str));
        }
        try{
            userService.banUsers(idList);
        } catch (ServiceException exc){
            LOG.error(exc);
            throw new CommandException(exc);
        }
    }
}
