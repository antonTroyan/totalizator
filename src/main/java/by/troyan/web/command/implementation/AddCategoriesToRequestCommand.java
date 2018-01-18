package by.troyan.web.command.implementation;

import by.troyan.web.command.ICommand;
import by.troyan.web.command.exception.CommandException;
import by.troyan.web.dao.exception.DAOException;
import by.troyan.web.dao.implementation.CategoryDAOImpl;
import by.troyan.web.exception.UnauthorizedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddCategoriesToRequestCommand implements ICommand {

    private final static Logger LOG = LogManager.getLogger(AddCategoriesToRequestCommand.class);
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException,
            CommandException, UnauthorizedException {
        CategoryDAOImpl categoryDAO = CategoryDAOImpl.getInstance();
        try{
            req.setAttribute("categories", categoryDAO.getAllCategories());
        } catch (DAOException exc){
            LOG.error(exc);
            throw new CommandException(exc);
        }
    }
}
