package entities.form;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.tomcat.dbcp.dbcp2.cpdsadapter.PStmtKeyCPDS;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/submit_registration")
public class RegisterServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String pass = req.getParameter("Password");
		String confirmPass = req.getParameter("ConfirmPass");
		String Phone = req.getParameter("Phone");
		String Gender = req.getParameter("Gender");

		PrintWriter out = resp.getWriter();
		if (!pass.equals(confirmPass)) {
			out.print("<h1>Password is incorrect</h1>");
			return;
		}
		long phn = Long.parseLong(Phone);

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/traveloop?useSSL=false","root",
					"Root");
			PreparedStatement ps = c
					.prepareStatement("Insert into users(name,email,Password,phone,gender)values(?,?,?,?,?)");

			ps.setString(1, name);
			ps.setString(2, email);
			ps.setString(3, pass);
			ps.setLong(4, phn);
			ps.setString(5, Gender);

			int rows = ps.executeUpdate();

			if (rows > 0) {
				out.println("<h1 style='color:green'>" + " Data inserted successfully!" + "</h1>");
			} else {
				out.println(" Insert failed.");

				ps.close();
				c.close();
			}
		} catch (Exception e) {
			e.printStackTrace(); // ADD THIS
			out.println("<h1 style='color:red'>Error: " + e.getMessage() + "</h1>");
		}

	}

}
