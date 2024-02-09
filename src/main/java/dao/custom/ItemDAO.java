package dao.custom;

import dao.CrudDAO;
import entity.ItemEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ItemDAO extends CrudDAO<ItemEntity> {
    @Override
    boolean save(ItemEntity dto, Connection connection) throws SQLException;

    @Override
    boolean Update(ItemEntity dto, Connection connection) throws SQLException;

    @Override
    boolean delete(String id, Connection connection) throws SQLException;

    @Override
    List<ItemEntity> getAll( Connection connection) throws SQLException;
}

