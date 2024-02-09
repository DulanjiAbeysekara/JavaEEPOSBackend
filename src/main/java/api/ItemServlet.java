package api;

import bo.BOFactory;
import bo.custom.CustomerBO;
import bo.custom.ItemBO;
import bo.custom.OrderBO;
import dto.CustomerDTO;
import dto.ItemDTO;
import entity.ItemEntity;
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

@WebServlet(urlPatterns = "/item",initParams = {
        @WebInitParam(name = "url", value = "jdbc:mysql://localhost:3306/company"),
        @WebInitParam(name = "username", value = "root"),
        @WebInitParam(name = "password", value = "12345")
})
public class ItemServlet extends HttpServlet {
    ItemBO itemBO= BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEMBO);
    DataSource source;

    String url;
    String username;
    String password;

    @Override
    public void init() throws ServletException {
        url = getServletConfig().getInitParameter("url");
        username = getServletConfig().getInitParameter("username");
        password = getServletConfig().getInitParameter("password");
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        try(Connection connection=source.getConnection()) {

            List<ItemDTO> allCustomer = itemBO.getAllItem(connection);
            Jsonb jsonb = JsonbBuilder.create();
            jsonb.toJson(allCustomer, response.getWriter());
//        List<ItemEntity> itemEntityList = new ArrayList<>();

//            InitialContext initCtx = new InitialContext();
//            source = (DataSource)initCtx.lookup("java:comp/env/jdbc/POS");
//
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection connection = DriverManager.getConnection(url, username, password);
//
//            PreparedStatement preparedStatement = connection.prepareStatement("SELECT  * FROM item");
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            while (resultSet.next()) {
//                String code = resultSet.getString(1);
//                String description = resultSet.getString(2);
//                int qtyOnHand = resultSet.getInt(3);
//                double unitPrice = resultSet.getDouble(4);
//
//                ItemEntity item = new ItemEntity(code, description, qtyOnHand, unitPrice);
//                itemEntityList.add(item);
//
//            }
//
//            Jsonb jsonb = JsonbBuilder.create();
//            jsonb.toJson(itemEntityList, response.getWriter());

        } catch ( SQLException e ) {
            e.printStackTrace();
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Jsonb jsonb = JsonbBuilder.create();
        ItemDTO itemDTO = jsonb.fromJson(req.getReader(), ItemDTO.class);


        String code = itemDTO.getCode();
        String description= itemDTO.getDescription();
        int qtyOnHand = itemDTO.getQtyOnHand();
        double unitPrice=itemDTO.getUnitPrice();


        try (Connection connection=source.getConnection()){
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection connection = DriverManager.getConnection(url, username, password);

//            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO item( code, description,qtyOnHand,unitPrice) VALUES (?,?,?,?)");
//            preparedStatement.setString(1, code);
//            preparedStatement.setString(2, description);
//            preparedStatement.setInt(3, qtyOnHand);
//            preparedStatement.setDouble(4,unitPrice);
//
//            boolean isSaved = preparedStatement.executeUpdate() > 0;
            boolean isSaved=itemBO.saveItem(itemDTO,connection);
            if (isSaved) {
                resp.getWriter().println("Item Saved");
            } else {
                resp.getWriter().println("Item Saved Fail");
            }


        } catch ( SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Jsonb jsonb = JsonbBuilder.create();
        ItemDTO item = jsonb.fromJson(req.getReader(), ItemDTO.class);
        String id = item.getCode();
        String description = item.getDescription();
        int qty = item.getQtyOnHand();
        double unitPrice=item.getUnitPrice();


        try(Connection connection= source.getConnection()) {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection connection = DriverManager.getConnection(url, username, password);

//            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE customer SET name=?, address=? WHERE id=? ");
//            preparedStatement.setString(3, id);
//            preparedStatement.setString(1, name);
//            preparedStatement.setString(2, address);
//
//
//            boolean isUpdate = preparedStatement.executeUpdate() > 0;
            boolean isUpdate=itemBO.updateItem(item,connection);
            if (isUpdate) {
                resp.getWriter().println("Item Update");
            } else {
                resp.getWriter().println("Item Update Fail");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");

        try(Connection connection=source.getConnection()) {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection connection = DriverManager.getConnection(url, username, password);
//
//            PreparedStatement preparedStatement = connection.prepareStatement("DELETE  FROM item WHERE  code=?");
//            preparedStatement.setString(1, code);
//
//            boolean isDeleted = preparedStatement.executeUpdate() > 0;
            boolean isDeleted=itemBO.DeleteItem(code,connection);
            if (isDeleted){
                resp.getWriter().println("Item Deleted");
            }else {
                resp.getWriter().println("Item Deleted Fail");
            }

        } catch ( SQLException e) {
            e.printStackTrace();
        }
    }

}
