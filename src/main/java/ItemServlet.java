import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

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

        List<ItemEntity> itemEntityList = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT  * FROM item");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String code = resultSet.getString(1);
                String description = resultSet.getString(2);
                int qtyOnHand = resultSet.getInt(3);
                double unitPrice = resultSet.getDouble(4);

                ItemEntity item = new ItemEntity(code, description, qtyOnHand, unitPrice);
                itemEntityList.add(item);

            }

            Jsonb jsonb = JsonbBuilder.create();
            jsonb.toJson(itemEntityList, response.getWriter());

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Jsonb jsonb = JsonbBuilder.create();
        ItemEntity item = jsonb.fromJson(req.getReader(), ItemEntity.class);
        System.out.println(item);

        String code = item.getCode();
        String description= item.getDescription();
        int qtyOnHand = item.getQtyOnHand();
        double unitPrice=item.getUnitPrice();


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO item( code, description,qtyOnHand,unitPrice) VALUES (?,?,?,?)");
            preparedStatement.setString(1, code);
            preparedStatement.setString(2, description);
            preparedStatement.setInt(3, qtyOnHand);
            preparedStatement.setDouble(4,unitPrice);

            boolean isSaved = preparedStatement.executeUpdate() > 0;
            if (isSaved) {
                resp.getWriter().println("Item Saved");
            } else {
                resp.getWriter().println("Item Saved Fail");
            }


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);

            PreparedStatement preparedStatement = connection.prepareStatement("DELETE  FROM item WHERE  code=?");
            preparedStatement.setString(1, code);

            boolean isDeleted = preparedStatement.executeUpdate() > 0;
            if (isDeleted){
                resp.getWriter().println("Item Deleted");
            }else {
                resp.getWriter().println("Item Deleted Fail");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

}
