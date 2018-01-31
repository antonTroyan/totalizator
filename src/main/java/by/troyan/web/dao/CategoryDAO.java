package by.troyan.web.dao;

import by.troyan.web.dao.exception.DAOException;
import by.troyan.web.entity.Category;

import java.util.List;

/**
 * CategoryDAO interface. Contains methods to work with different categories of sport.
 */

public interface CategoryDAO {

    /**
     * Used to get all categories from database.
     * @return  List of Categories.
     */
    List<Category> getAllCategories() throws DAOException;

    /**
     * Used to add category to database.
     * @return  List of Categories.
     */
    Category addCategory(Category category) throws DAOException;
}
