package by.troyan.web.command;

import by.troyan.web.command.exception.CommandException;
import by.troyan.web.entity.User;
import by.troyan.web.exception.UnauthorizedException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Interface, realized by all commands. <p>
 * 1 Default method, check access rights. Need enum and request.
 * Gets attribute from the request, if something is wrong it throws exception. <p>
 * 2 Abstract method. Describe main method for all commands.
 */

public interface ICommand {
    default void checkRoots(HttpServletRequest req, User.Role[] needLevels)
            throws  UnauthorizedException {
        if((needLevels == null) || (needLevels.length == 0)  ){
            return;
        }
        String currentLevel = (String) req.getSession().getAttribute("role");
        for (User.Role needLevel : needLevels) {
            if(needLevel.getValue().equals(currentLevel)){
                return;
            }
        }
        throw new UnauthorizedException("Not enough permissions for this operation");
    }

    void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException,
            CommandException, UnauthorizedException;
}
