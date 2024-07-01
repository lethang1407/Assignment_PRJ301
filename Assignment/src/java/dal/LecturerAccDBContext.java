package dal;

import model.Lecturer_Account;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Lecturer;

public class LecturerAccDBContext extends DBContext<Lecturer_Account> {

    public Lecturer_Account getUserByUsernamePassword(String username, String password) {
        PreparedStatement stm = null;
        Lecturer_Account userLecturer = null;
        try {
            String sql = """
                         SELECT la.username,la.displayname,l.lid,l.lname
                            FROM lecturers_account la
                                LEFT JOIN lecturers_users lu ON lu.username = la.username
                                LEFT JOIN lecturers l ON lu.lid = l.lid
                            WHERE la.username = ? AND la.[password] = ?""";
            stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                userLecturer = new Lecturer_Account();
                userLecturer.setDisplayname(rs.getString("displayname"));
                userLecturer.setUsername(username);
                int lid = rs.getInt("lid");
                if (lid != 0) {
                    Lecturer lecturer = new Lecturer();
                    lecturer.setId(lid);
                    lecturer.setName(rs.getString("lname"));
                    userLecturer.setLecturer(lecturer);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(LecturerAccDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(LecturerAccDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return userLecturer;
    }

}
