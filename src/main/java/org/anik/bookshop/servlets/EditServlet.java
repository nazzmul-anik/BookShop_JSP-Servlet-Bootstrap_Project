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

@WebServlet(name="edit", value="/edit")
public class EditServlet extends HttpServlet {

    public static final String query = "Select book_name, book_edition, book_price from book_data where id=?";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter printWriter = resp.getWriter();

        int id = Integer.parseInt(req.getParameter("id"));

        try(
                Connection connection = DatabaseUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ){

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();


            if(resultSet.next()){
                printWriter.println("<form action='update?id="+id+"' method='post'>");
                printWriter.println("<table align='center'>");
                printWriter.println("<tr>");
                printWriter.println("<td>Book Name</td>");
                printWriter.println("<td><input type='text' name='bookName' value='"+ resultSet.getString(1)+"'/></td>");
                printWriter.println("</tr>");
                printWriter.println("<tr>");
                printWriter.println("<td>Book Edition</td>");
                printWriter.println("<td><input type='text' name='bookEdition' value='"+ resultSet.getString(2)+"'/></td>");
                printWriter.println("</tr>");
                printWriter.println("<tr>");
                printWriter.println("<td>Book Price</td>");
                printWriter.println("<td><input type='number' name='bookPrice' value='"+ resultSet.getDouble(3)+"'/></td>");
                printWriter.println("</tr>");
                printWriter.println("<tr>");
                printWriter.println("<td><input type='submit' value='Update'/></td>");
                printWriter.println("<td><input type='reset' value='Cancel'/></td>");
                printWriter.println("</tr>");
                printWriter.println("<tr>");
                printWriter.println("<td><a href='book-list'>Back</a></td>");
                printWriter.println("<td><a href='home.html'>Home</a></td>");
                printWriter.println("</tr>");
                printWriter.println("</table>");
                printWriter.println("</form>");


            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
