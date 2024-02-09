package dao.custom.impl;

import dao.custom.OrderDetailsDAO;
import entity.OrderDetailsEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {

    @Override
    public boolean save(List<OrderDetailsEntity> dto, Connection connection) throws SQLException {
        int count = 0;

        for (OrderDetailsEntity orderListEntity: dto) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO orderdetails(oid , itemCode , qty,unitPrice) VALUES (?,?,?,?)");
            preparedStatement.setString(1,orderListEntity.getOid());
            preparedStatement.setString(2, orderListEntity.getItemCode());
            preparedStatement.setInt(3, orderListEntity.getQty());
            preparedStatement.setDouble(4,orderListEntity.getUnitPrice());

            boolean isOrderDetailSaved = preparedStatement.executeUpdate() > 0;
            if (isOrderDetailSaved){
                count++;
            }


        }
        if (count == dto.size()){
            return true;

        }else {
            return false;
        }

    }

    @Override
    public boolean Update(List<OrderDetailsEntity> dto, Connection connection) throws SQLException {
        int count = 0;

        for (OrderDetailsEntity orderListEntity: dto) {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE orderdetails SET itemCode =?,qty=?,unitPrice=? WHERE oid=? ");
            preparedStatement.setString(1,orderListEntity.getItemCode());
            preparedStatement.setInt(2, orderListEntity.getQty());
            preparedStatement.setDouble(3, orderListEntity.getUnitPrice());
            preparedStatement.setString(4, orderListEntity.getOid());



            boolean isOrderDetailSaved = preparedStatement.executeUpdate() > 0;
            if (isOrderDetailSaved){
                count++;
            }


        }
        if (count == dto.size()){
            return true;

        }else {
            return false;
        }

    }

    @Override
    public boolean delete(String id, Connection connection) throws SQLException {
        return false;
    }

    @Override
    public List<OrderDetailsEntity> getAll(Connection connection) throws SQLException {
        return null;
    }
}
