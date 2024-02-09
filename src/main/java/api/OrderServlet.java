package api;

import bo.BOFactory;
import bo.custom.OrderBO;
import dto.CustomerDTO;
import dto.OrderDT0;
import entity.CustomerEntity;
import entity.OrderEntity;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet( urlPatterns = "/order",initParams = {
        @WebInitParam(name = "url", value = "jdbc:mysql://localhost:3306/company"),
        @WebInitParam(name = "username", value = "root"),
        @WebInitParam(name = "password", value = "12345")
})


public class OrderServlet extends HttpServlet {
    String url;
    String username;
    String password;

    OrderBO orderBO= BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDERBO);
    DataSource source;

    @Override
    public void init() throws ServletException {
        url = getServletConfig().getInitParameter("url");
        username = getServletConfig().getInitParameter("username");
        password = getServletConfig().getInitParameter("password");
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        //            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection connection = DriverManager.getConnection(url, username, password);
//
//            PreparedStatement preparedStatement = connection.prepareStatement("SELECT  * FROM orders");
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            while (resultSet.next()) {
//                String oid = resultSet.getString(1);
//                String date = resultSet.getString(2);
//                String customerID= resultSet.getString(3);
//
//                OrderEntity order=new OrderEntity(oid,date,customerID);
//                orderEntityList.add(order);
//            }
//            Jsonb jsonb= JsonbBuilder.create();
//            jsonb.toJson(orderEntityList,resp.getWriter());

        resp.setContentType("application/json");
        List<OrderEntity> orderEntityList=new ArrayList<>();

        try (Connection connection= source.getConnection()){

            List<OrderDT0> allOrder = orderBO.getAllOrder(connection);
            Jsonb jsonb = JsonbBuilder.create();
            jsonb.toJson(allOrder, resp.getWriter());

//            InitialContext initCtx = new InitialContext();
//            source = (DataSource) initCtx.lookup("java:comp/env/jdbc/POS");
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Jsonb jsonb = JsonbBuilder.create();
        OrderDT0 order = jsonb.fromJson(req.getReader(), OrderDT0.class);

        String oid = order.getOid();
        String date = order.getDate();
        String customerID = order.getCustomerID();


        try(Connection connection=source.getConnection()) {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection connection = DriverManager.getConnection(url, username, password);

//            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO orders(oid , date , customerID) VALUES (?,?,?)");
//            preparedStatement.setString(1, oid);
//            preparedStatement.setString(2, date);
//            preparedStatement.setString(3, customerID);
//
//            boolean isSaved = preparedStatement.executeUpdate() > 0;

            boolean isSaved= orderBO.saveOrder(order,connection);
            if (isSaved) {
                resp.getWriter().println("Order Saved");
            } else {
                resp.getWriter().println("Order Saved Fail");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Jsonb jsonb = JsonbBuilder.create();
        OrderDT0 order = jsonb.fromJson(req.getReader(), OrderDT0.class);

        String oid = order.getOid();
        String date = order.getDate();
        String customerID = order.getCustomerID();


        try(Connection connection= source.getConnection()) {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection connection = DriverManager.getConnection(url, username, password);
//
//            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE orders SET date=?, customerID=? WHERE oid=? ");
//            preparedStatement.setString(3, oid);
//            preparedStatement.setString(1, date);
//            preparedStatement.setString(2, customerID);
//
//
//            boolean isUpdate = preparedStatement.executeUpdate() > 0;
            boolean isUpdate=orderBO.updateOrder(order,connection);
            if (isUpdate) {
                resp.getWriter().println("Order Update");
            } else {
                resp.getWriter().println("Order Update Fail");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String oid = req.getParameter("id");

        try (Connection connection= source.getConnection()){

            boolean isDeleted= orderBO.DeleteOrder(oid,connection);
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection connection = DriverManager.getConnection(url, username, password);
//
////            PreparedStatement preparedStatement = connection.prepareStatement("DELETE orders FROM  WHERE oid =?");
////            preparedStatement.setString(1, oid);
////
////            boolean isDeleted = preparedStatement.executeUpdate() > 0;
            if (isDeleted){
                resp.getWriter().println("Order Deleted");
            }else {
                resp.getWriter().println("Order Deleted Fail");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
