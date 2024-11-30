package org.anik.bookshop.servlets;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.anik.bookshop.databaseUtil.DatabaseUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class RegisterServlet extends HttpServlet {

    private static final String query = "insert into book_data(book_name, book_edition, book_price) values(?, ?, ?)";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("text/html");

        String bookName = req.getParameter("bookName");
        String bookEdition = req.getParameter("bookEdition");
        String bookPriceStr = req.getParameter("bookPrice");
        
        try(
                PrintWriter printWriter = resp.getWriter()
                ){

            double bookPrice = Double.parseDouble(bookPriceStr);

            try(
                    Connection connection = DatabaseUtil.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    ){

                preparedStatement.setString(1, bookName);
                preparedStatement.setString(2, bookEdition);
                preparedStatement.setDouble(3, bookPrice);

                int count = preparedStatement.executeUpdate();

                if(count == 1){
                    printWriter.println("<h2>Book Registration Successfully !!</h2>");
                }else{
                    printWriter.println("<h2>Failed To Book Registration.</h2>");
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }
}
