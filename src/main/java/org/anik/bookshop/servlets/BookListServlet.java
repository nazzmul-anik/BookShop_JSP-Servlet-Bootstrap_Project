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
import java.sql.SQLException;

@WebServlet(name = "BookList", value = "/book-list")
public class BookListServlet extends HttpServlet {

    public static final String query = "SELECT id, book_name, book_edition, book_price FROM book_data";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter printWriter = resp.getWriter();

        Connection connection = null; // Initialize the connection variable

        try {
            connection = DatabaseUtil.getConnection(); // Attempt to get the connection

            // Check if the connection is valid and reinitialize if necessary
            if (connection == null || connection.isClosed()) {
                connection = DatabaseUtil.getConnection();
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                // Execute the query
                ResultSet resultSet = preparedStatement.executeQuery();

                // Generate the HTML table
                printWriter.println("<table border='1' align='center'>");
                printWriter.println("<tr>");
                printWriter.println("<th>Book ID</th>");
                printWriter.println("<th>Book Name</th>");
                printWriter.println("<th>Book Edition</th>");
                printWriter.println("<th>Book Price</th>");
                printWriter.println("<th>Edit</th>");
                printWriter.println("<th>Delete</th>");
                printWriter.println("</tr>");

                // Populate table rows with data
                while (resultSet.next()) {
                    printWriter.println("<tr>");
                    printWriter.println("<td>" + resultSet.getInt(1) + "</td>");
                    printWriter.println("<td>" + resultSet.getString(2) + "</td>");
                    printWriter.println("<td>" + resultSet.getString(3) + "</td>");
                    printWriter.println("<td>" + resultSet.getDouble(4) + "</td>");
                    printWriter.println("<td><a href='edit?id="+resultSet.getInt(1)+"'>Edit</a></td>");
                    printWriter.println("<td><a href='delete?id="+resultSet.getInt(1)+"'>Delete</a></td>");
                    printWriter.println("</tr>");
                }
                printWriter.println("</table>");

                // Add a link back to the home page
                printWriter.println("<br>");
                printWriter.println("<a href='home.html'>Home Page</a>");
            }

        } catch (SQLException e) {
            // Handle SQL exceptions
            printWriter.println("<p>Error: Unable to fetch book data.</p>");
            printWriter.println("<a href='home.html'>Home Page</a>");
        } catch (Exception e) {
            // Handle other exceptions
            printWriter.println("<p>An unexpected error occurred.</p>");
            e.printStackTrace();
        } finally {
            // Ensure the connection is properly closed
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
