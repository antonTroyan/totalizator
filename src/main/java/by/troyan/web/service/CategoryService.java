package by.troyan.web.service;

import by.troyan.web.entity.Category;
import by.troyan.web.exception.CategoryException;
import by.troyan.web.service.exception.ServiceException;

/**
 * CategoryService interface. Describe method works with categories.
 */

public interface CategoryService {

    /**
     * Used to add category.
     * @param name Category object
     * @return  Category object.
     */
    Category addCategory(String name) throws ServiceException, CategoryException;
}
