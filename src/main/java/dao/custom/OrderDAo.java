package dao.custom;

import dao.CrudDAO;
import entity.ItemEntity;
import entity.OrderEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrderDAo extends CrudDAO<OrderEntity> {
    @Override
    boolean save(OrderEntity dto, Connection connection) throws SQLException;

    @Override
    boolean Update(OrderEntity dto, Connection connection) throws SQLException;

    @Override
    boolean delete(String id, Connection connection) throws SQLException;

    @Override
    List<OrderEntity> getAll( Connection connection) throws SQLException;
}
