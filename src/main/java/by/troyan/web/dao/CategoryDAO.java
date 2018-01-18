package by.troyan.web.dao;

import by.troyan.web.dao.exception.DAOException;
import by.troyan.web.entity.Category;

import java.util.List;

/**
 * CategoryDAO interface. Contains methods to work with different categories of sport.
 */

public interface CategoryDAO {

    List<Category> getAllCategories() throws DAOException;

    Category addCategory(Category category) throws DAOException;
}
