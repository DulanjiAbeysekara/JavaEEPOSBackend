package bo.custom.impl;

import bo.custom.OrderBO;
import dao.DAOFactory;
import dao.custom.ItemDAO;
import dao.custom.OrderDAo;
import dao.custom.OrderDetailsDAO;
import dto.CustomerDTO;
import dto.OrderDT0;
import dto.OrderDetailsDTO;
import entity.CustomerEntity;
//import entity.OrderDeatilsEntity;
import entity.OrderDetailsEntity;
import entity.OrderEntity;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderBOImpl implements OrderBO {

    OrderDAo orderDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDAO);
    OrderDetailsDAO orderDetailsDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAILSDAO);

    @Override
    public List<OrderDT0> getAllOrder(Connection connection) throws SQLException {
        List<OrderEntity> orderEntityList = orderDAO.getAll(connection);
        List<OrderDT0> orderDTOList = new ArrayList<>();
        OrderDT0 orderDTO;

        for (OrderEntity order : orderEntityList){
//            orderDTO = new OrderDTO(order.getOid(),order.getDate(),order.getCustomerId());
//            orderDTOList.add(orderDTO);
        }
        return orderDTOList;
}

    @Override
    public boolean saveOrder(OrderDT0 orderDTO , Connection connection) throws SQLException {

        try{
            connection.setAutoCommit(false);
            OrderEntity orderEntity = new OrderEntity(orderDTO.getOid(), orderDTO.getDate(),orderDTO.getCustomerID());
            boolean isOrderSave = orderDAO.save(orderEntity, connection);

            if (isOrderSave){
                List<OrderDetailsEntity > orderDetailsEntities = new ArrayList<>();
                for (OrderDetailsDTO orderDetailsDTO: orderDTO.getOrderDetails()) {

                    OrderDetailsEntity orderDetailsEntity = new OrderDetailsEntity(orderDetailsDTO.getOid(),orderDetailsDTO.getItemCode(),orderDetailsDTO.getQty(),orderDetailsDTO.getUnitPrice());
                    orderDetailsEntities.add(orderDetailsEntity);
                }
                boolean isOrderDetailSave = orderDetailsDAO.save(orderDetailsEntities, connection);

                if (isOrderDetailSave){
                    connection.setAutoCommit(true);
                    return true;
                }
            }
        }catch (Exception e){
            connection.rollback();
            return false;
        }
        return false;
    }

    @Override
    public boolean updateOrder(OrderDT0 orderDT0,Connection connection) throws SQLException {
        OrderEntity orderEntity = new OrderEntity(orderDT0.getOid(), orderDT0.getDate(), orderDT0.getCustomerID());
        return orderDAO.Update(orderEntity,connection);

    }

    @Override
    public boolean DeleteOrder(String id,Connection connection) throws SQLException {
        return orderDAO.delete(id,connection);
    }
}
