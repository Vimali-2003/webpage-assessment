import java.io.*;
import java.util.*;
import java.sql.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.JSONArray;
import org.json.*;
@WebServlet("/user")
public class User extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        PrintWriter out = response.getWriter();
       
        try {
            Class.forName("org.postgresql.Driver");
            conn = DBManager.getConnection();
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String dob = request.getParameter("dob");
            String address = request.getParameter("address");
            String gender = request.getParameter("gender");
            String phone = request.getParameter("phone");
            String location = request.getParameter("location");
            StringBuilder sql = new StringBuilder("SELECT * FROM users WHERE 1=1");
            List<Object> params = new ArrayList<>();

            if (name != null && !name.isEmpty()) {
                sql.append(" AND name = ?");
                params.add(name);
            }
            if (email != null && !email.isEmpty()) {
                sql.append(" AND email = ?");
                params.add(email);
            }
            if (dob != null && !dob.isEmpty()) {
                sql.append(" AND dob = ?");
                params.add(dob);
            }
            if (address != null && !address.isEmpty()) {
                sql.append(" AND address = ?");
                params.add(address);
            }
            if (gender != null && !gender.isEmpty()) {
                sql.append(" AND gender = ?");
                params.add(gender);
            }
            if (phone != null && !phone.isEmpty()) {
                sql.append(" AND phone = ?");
                params.add(phone);
            }
            if (location != null && !location.isEmpty()) {
                sql.append(" AND location = ?");
                params.add(location);
            }
            stmt = conn.prepareStatement(sql.toString());
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            rs = stmt.executeQuery();

            JSONArray usersArray = new JSONArray();
            while (rs.next()) {
                int id = rs.getInt("id");
                String retrievedName = rs.getString("name");
                String retrievedEmail = rs.getString("email");
                String retrievedPhone = rs.getString("phone");
                String retrievedDob = rs.getString("dob");
                String retrievedLocation = rs.getString("location");
                String retrievedGender = rs.getString("gender");
                String retrievedAddress = rs.getString("address");
                JSONObject userObject = new JSONObject();
                userObject.put("id", id);
                userObject.put("name", retrievedName);
                userObject.put("email", retrievedEmail);
                userObject.put("phone", retrievedPhone);
                userObject.put("dob", retrievedDob);
                userObject.put("location", retrievedLocation);
                userObject.put("gender", retrievedGender);
                userObject.put("address", retrievedAddress);
                usersArray.put(userObject);
                }
                out.print(usersArray);
        } catch (Exception e) {
            System.out.print(e);
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
        PreparedStatement stmt = null;
        ResultSet rs = null;
        PrintWriter out = response.getWriter();
       
        try {
            Class.forName("org.postgresql.Driver");
            conn = DBManager.getConnection();
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String dob = request.getParameter("dob");
            String address = request.getParameter("address");
            String gender = request.getParameter("gender");
            String phone = request.getParameter("phone");
            String location = request.getParameter("location");
            StringBuilder sql = new StringBuilder("DELETE FROM users WHERE 1=1");
            List<Object> params = new ArrayList<>();
            if (name != null && !name.isEmpty()) {
                sql.append(" AND name = ?");
                params.add(name);
            }
            if (email != null && !email.isEmpty()) {
                sql.append(" AND email = ?");
                params.add(email);
            }
            if (dob != null && !dob.isEmpty()) {
                sql.append(" AND dob = ?");
                params.add(dob);
            }
            if (address != null && !address.isEmpty()) {
                sql.append(" AND address = ?");
                params.add(address);
            }
            if (gender != null && !gender.isEmpty()) {
                sql.append(" AND gender = ?");
                params.add(gender);
            }
            if (phone != null && !phone.isEmpty()) {
                sql.append(" AND phone = ?");
                params.add(phone);
            }
            if (location != null && !location.isEmpty()) {
                sql.append(" AND location = ?");
                params.add(location);
            }
            stmt = conn.prepareStatement(sql.toString());
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            stmt.executeUpdate();
            out.print("Successfully deleted particular user");
        } catch (Exception e) {
            System.out.print(e);
            out.println("Error Occured");
        } finally {
             try {
            DBManager.closeConnection();
        } catch (SQLException e) {
            out.println("Error Occured at DB connection close");
        }
        }
    }
    public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		Connection conn = null;
        PreparedStatement stmt = null;
        String name="",email="",phone=null,dob=null,location=null,gender=null,address=null;
		PrintWriter out=response.getWriter();
		try {
            Class.forName("org.postgresql.Driver");
            conn = DBManager.getConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            StringBuilder jsonPayload = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonPayload.append(line);
            }
            String sql = "INSERT INTO users (name, email, dob, phone, address, location, gender) VALUES (?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            JSONObject jsonObject = new JSONObject(jsonPayload.toString());
            if (jsonObject.has("name")) {
                name=jsonObject.getString("name");
            }
            if (jsonObject.has("email")) {
                email=jsonObject.getString("email");
            }
            if (jsonObject.has("dob")) {
                dob=jsonObject.getString("dob");
            }
            if (jsonObject.has("phone")) {
                phone=jsonObject.getString("phone");
            }
            if (jsonObject.has("address")) {
                address=jsonObject.getString("address");
            }
            if (jsonObject.has("location")) {
                location=jsonObject.getString("location");
            }
            if (jsonObject.has("gender")) {
                gender=jsonObject.getString("gender");
                
            }
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, dob);
            stmt.setString(4, phone);
            stmt.setString(5, address);
            stmt.setString(6, location);
            stmt.setString(7, gender);
            stmt.executeUpdate();

			out.println("User is created");
		 }
		 
		catch(Exception e)
		{
			System.out.println(e);
			out.println("Error Occured");
		}
		finally {
			  try {
            DBManager.closeConnection();
        } catch (SQLException e) {
            out.println("Error Occured at DB connection close");
        }
        }
   }

   public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        PrintWriter out = response.getWriter();
        try {
            Class.forName("org.postgresql.Driver");
            conn = DBManager.getConnection();
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String dob = request.getParameter("dob");
            String address = request.getParameter("address");
            String gender = request.getParameter("gender");
            String phone = request.getParameter("phone");
            String location = request.getParameter("location");
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            StringBuilder jsonPayload = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonPayload.append(line);
            }
            StringBuilder sql = new StringBuilder("UPDATE users SET ");
            JSONObject jsonObject = new JSONObject(jsonPayload.toString());
            List<Object> parameterValues = new ArrayList<>();

            if (jsonObject.has("name")) {
                sql.append("name = ?, ");
                parameterValues.add(jsonObject.getString("name"));
            }
            if (jsonObject.has("email")) {
                sql.append("email = ?, ");
                parameterValues.add(jsonObject.getString("email"));
            }
            if (jsonObject.has("dob")) {
                sql.append("dob = ?, ");
                parameterValues.add(jsonObject.getString("dob"));
            }
            if (jsonObject.has("phone")) {
                sql.append("phone = ?, ");
                parameterValues.add(jsonObject.getString("phone"));
            }
            if (jsonObject.has("address")) {
                sql.append("address = ?, ");
                parameterValues.add(jsonObject.getString("address"));
            }
            if (jsonObject.has("location")) {
                sql.append("location = ?, ");
                parameterValues.add(jsonObject.getString("location"));
            }
            if (jsonObject.has("gender")) {
                sql.append("gender = ?, ");
                parameterValues.add(jsonObject.getString("gender"));
            }
            sql.setLength(sql.length() - 2);
            sql.append(" WHERE 1=1");
            if (name != null && !name.isEmpty()) {
                sql.append(" AND name = ?");
                parameterValues.add(name);
            }
            if (email != null && !email.isEmpty()) {
                sql.append(" AND email = ?");
                parameterValues.add(email);
            }
            if (dob != null && !dob.isEmpty()) {
                sql.append(" AND dob = ?");
                parameterValues.add(dob);
            }
            if (address != null && !address.isEmpty()) {
                sql.append(" AND address = ?");
                parameterValues.add(address);
            }
            if (gender != null && !gender.isEmpty()) {
                sql.append(" AND gender = ?");
                parameterValues.add(gender);
            }
            if (phone != null && !phone.isEmpty()) {
                sql.append(" AND phone = ?");
                parameterValues.add(phone);
            }
            if (location != null && !location.isEmpty()) {
                sql.append(" AND location = ?");
                parameterValues.add(location);
            }
            System.out.println(sql);
            System.out.println(parameterValues);
            stmt = conn.prepareStatement(sql.toString());
            for (int i = 0; i < parameterValues.size(); i++) {
                stmt.setObject(i + 1, parameterValues.get(i));
            }
            stmt.executeUpdate();
            out.println("Updated Successfully");
        } catch (Exception e) {
            System.out.print(e);
            out.println("Error Occured");
        }finally {
			  try {
            DBManager.closeConnection();
        } catch (SQLException e) {
            out.println("Error Occured at DB connection close");
        }
        }
    }
}