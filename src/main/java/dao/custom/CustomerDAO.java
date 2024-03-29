package dao.custom;

import dao.CrudDAO;
import entity.CustomerEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO extends CrudDAO<CustomerEntity> {
    @Override
    boolean save(CustomerEntity dto, Connection connection) throws SQLException;

    @Override
    boolean Update(CustomerEntity dto,Connection connection) throws SQLException;

    @Override
    boolean delete(String id,Connection connection) throws SQLException;

    @Override
    List<CustomerEntity> getAll(Connection connection) throws SQLException;
}
