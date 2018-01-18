package by.troyan.web.dao;

import by.troyan.web.dao.exception.DAOException;
import by.troyan.web.entity.Operation;
import by.troyan.web.exception.OperationException;

/**
 * OperationDAO interface. Interface supporting all money operations.
 */

public interface OperationDAO {
    Operation addOperation(Operation operation) throws DAOException, OperationException;

}
