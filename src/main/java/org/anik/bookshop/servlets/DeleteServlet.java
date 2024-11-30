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

@WebServlet(name = "delete", value = "/delete")
public class DeleteServlet extends HttpServlet {

    public static final String query = "delete from book_data where id=?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter printWriter = resp.getWriter();

        try(
                Connection connection = DatabaseUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ){

            int id = Integer.parseInt(req.getParameter("id"));
            preparedStatement.setInt(1, id);
            int count = preparedStatement.executeUpdate();
            if(count == 1){
                printWriter.println("<h2>Record Deleted Successfully!!</h2>");
            }else{
                printWriter.println("<h2>Failed To Record Delete.</h2>");
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
