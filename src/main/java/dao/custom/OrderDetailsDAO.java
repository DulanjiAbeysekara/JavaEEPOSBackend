package dao.custom;

import dao.CrudDAO;
import entity.OrderDetailsEntity;
import entity.OrderEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrderDetailsDAO  {

    boolean save(List<OrderDetailsEntity> dto, Connection connection) throws SQLException;


    boolean Update(List<OrderDetailsEntity> dto, Connection connection) throws SQLException;


    boolean delete(String id, Connection connection) throws SQLException;


    List<OrderDetailsEntity> getAll(Connection connection) throws SQLException;
}



