import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.mysql.cj.protocol.Resultset;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String myemail = req.getParameter("name");
		String mypass = req.getParameter("Password");

		PrintWriter out = resp.getWriter();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/traveloop?useSSL=false","root",
					"Root");
			PreparedStatement ps = c.prepareStatement("Insert into users(name,email,Password,phone,gender)values(?,?,?,?,?)");
			ps.setString(1, myemail);
			ps.setString(2, mypass);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				
				req.setAttribute("Name", rs.getString(1));
				req.setAttribute("Email", rs.getString(2));
				req.setAttribute("Password", rs.getString(3));
				req.setAttribute("Phone", rs.getString(4));
				req.setAttribute("Gender", rs.getString(5));
				
				out.print("<h1>" + "Login Successfully...." + "</h1>");
		//		RequestDispatcher rd = req.getRequestDispatcher("/Profile.jsp");
	   //		rd.include(req, resp);

			} 
			else {
				System.out.println("Invalid Credentials....");
				out.print("Invalid Credentials........");
				RequestDispatcher rd = req.getRequestDispatcher("/submit_registration");
				rd.include(req, resp);
			}

		   } 
		   catch (Exception e) {
			e.getMessage();
		}

	}

}