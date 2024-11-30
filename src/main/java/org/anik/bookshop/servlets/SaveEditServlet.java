package org.anik.bookshop.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.anik.bookshop.databaseUtil.DatabaseUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet(name = "update", value = "/update")
public class SaveEditServlet extends HttpServlet {

    public static final String query = "update book_data set book_name=?, book_edition=?, book_price=? where id=?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter printWriter = resp.getWriter();

        try(
                Connection connection = DatabaseUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ){

            int id = Integer.parseInt(req.getParameter("id"));

            String bookName = req.getParameter("bookName");
            String bookEdition = req.getParameter("bookEdition");
            String bookPriceStr = req.getParameter("bookPrice");


            if(bookName == null || bookName.isEmpty() || bookEdition == null || bookEdition.isEmpty() || !bookPriceStr.matches("\\d+(\\.\\d+)?")){
                printWriter.println("<h2>Invalid Input Data. Please, fill the form correctly.</h2>");
                return;
            }

            double bookPrice = Double.parseDouble(bookPriceStr);

            preparedStatement.setString(1, bookName);
            preparedStatement.setString(2, bookEdition);
            preparedStatement.setDouble(3, bookPrice);;
            preparedStatement.setInt(4, id);

            int count = preparedStatement.executeUpdate();

            if(count == 1){
                printWriter.println("<h2>Update Record Successfully!!</h2>");
            }else{
                printWriter.println("<h2>Record is not updated.</h2>");
            }

            printWriter.println("<br>");
            printWriter.println("<a href='home.html'>Home Page</a>");
            printWriter.println("<br>");
            printWriter.println("<a href='book-list'>Book List</a>");


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
