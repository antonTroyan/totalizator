package by.troyan.web.service.implementation;

import by.troyan.web.dao.CategoryDAO;
import by.troyan.web.dao.exception.DAOException;
import by.troyan.web.dao.factory.DAOFactory;
import by.troyan.web.entity.Category;
import by.troyan.web.exception.CategoryException;
import by.troyan.web.service.CategoryService;
import by.troyan.web.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * CategoryService implementation. Describe method works with Categories.
 */

public class CategoryServiceImpl implements CategoryService {
    private final static Logger LOG = LogManager.getLogger(CategoryServiceImpl.class);
    private static final CategoryServiceImpl instance = new CategoryServiceImpl();

    private CategoryDAO categoryDAO;

    public static CategoryServiceImpl getInstance(){
        return instance;
    }

    private CategoryServiceImpl(){
        categoryDAO = DAOFactory.getFactory().getCategoryDAO();
    }

    @Override
    public Category addCategory(String name) throws ServiceException, CategoryException {
        Category category = new Category();
        CategoryException categoryException = new CategoryException(category);
        if((name == null) || name.isEmpty()){
            categoryException.addMessage("err.name-is-invalid");
        }
        category.setName(name);
        if(categoryException.getErrorMessageList().size() > 0){
            throw categoryException;
        }
        try {
            return categoryDAO.addCategory(category);
        } catch (DAOException exc){
            LOG.error(exc);
            throw new ServiceException(exc);
        }
    }
}
