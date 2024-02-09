package bo.custom;

import bo.SuperBO;
import dto.ItemDTO;
import dto.OrderDT0;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrderBO extends SuperBO {
    List<OrderDT0> getAllOrder(Connection connection) throws SQLException;

    boolean saveOrder(OrderDT0 orderDT0,Connection connection) throws SQLException;

    boolean updateOrder(OrderDT0 orderDT0,Connection connection) throws SQLException;

    boolean DeleteOrder(String id,Connection connection) throws SQLException;

}


