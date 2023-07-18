import java.io.*;
import java.util.*;
import java.sql.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.JSONArray;
import org.json.*;
@WebServlet("/alluser")
public class AllUser extends HttpServlet{

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
		PrintWriter out = response.getWriter();
        
        try {
            Class.forName("org.postgresql.Driver");
            conn = DBManager.getConnection();
            String sql = "SELECT * FROM users";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
			JSONArray usersArray = new JSONArray();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String dob = rs.getString("dob");
                String location = rs.getString("location");
                String gender = rs.getString("gender");
                String address = rs.getString("address");

				JSONObject userObject = new JSONObject();
                userObject.put("id", id);
                userObject.put("name", name);
                userObject.put("email", email);
                userObject.put("phone", phone);
                userObject.put("dob", dob);
                userObject.put("location", location);
                userObject.put("gender", gender);
                userObject.put("address", address);

                // Add the user object to the array
                usersArray.put(userObject);
            }
			out.print(usersArray);


        } catch (Exception e) {
			System.out.println(e);
            out.println("Error Occured");
        } finally {
          try {
            DBManager.closeConnection();
        } catch (SQLException e) {
            out.println("Error Occured at DB connection close");
        }
        }
    }
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Connection conn = null;
        Statement stmt = null;
        PrintWriter out = response.getWriter();
        try {
            conn = DBManager.getConnection();
            String sql = "DELETE FROM users";
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            out.println("Successfully Deleted");
            
        } catch (Exception e) {
            out.println("Error Occured");
        } finally {
            try {
                    DBManager.closeConnection();
                }
             catch (SQLException e) {
               out.println("Error Occured at DB Connection close");
            }
        }
    }
}