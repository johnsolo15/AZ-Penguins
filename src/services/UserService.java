package services;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import domain.User;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserService implements Service<User> {

    Connection connection;

    public UserService(Connection connection) {
        super();
        this.connection = connection;
    }

    public boolean add(User user) {
        try {

            String userId = user.getUserId();
            String firstName = user.getFirstName();
            String lastName = user.getLastName();
            String phone = user.getPhone();
            String email = user.getEmail();
            String password = user.getPassword();
            String userStatusId = user.getUserStatusId();

            CallableStatement oCSF = connection.prepareCall("{call sp_insert_user(?,?,?,?,?,?,?)}");
            oCSF.setString(1, userId);
            oCSF.setString(2, firstName);
            oCSF.setString(3, lastName);
            oCSF.setString(4, phone);
            oCSF.setString(5, email);
            oCSF.setString(6, password);
            oCSF.setString(7, userStatusId);

            oCSF.execute();
            oCSF.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void deleteById(String id) {
        try {
            Statement usersSt = connection.createStatement();
            usersSt.executeQuery("Delete from users where user_id = " + id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<User> getAll() {

        ArrayList<User> users = new ArrayList<User>();

        try {
            Statement usersSt = connection.createStatement();
            ResultSet usersRs = usersSt.executeQuery("Select * from Users");

            while (usersRs.next()) {
                User user = new User(
                        usersRs.getString(1),
                        usersRs.getString(2),
                        usersRs.getString(3),
                        usersRs.getString(4),
                        usersRs.getString(5),
                        usersRs.getString(6),
                        usersRs.getString(7),
                        usersRs.getInt(8)
                );
                users.add(user);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    public User getById(String id) {
        User user = null;

        try {
            Statement usersSt = connection.createStatement();
            ResultSet usersRs = usersSt.executeQuery("Select * from Users where user_id = " + id);

            usersRs.next();
            user = new User(
                    usersRs.getString(1),
                    usersRs.getString(2),
                    usersRs.getString(3),
                    usersRs.getString(4),
                    usersRs.getString(5),
                    usersRs.getString(6),
                    usersRs.getString(7),
                    usersRs.getInt(8)
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return user;
    }

    public User getByEmail(String email) {
        User user = null;

        try {

            PreparedStatement pstmt = connection.prepareStatement("select * from users "
                    + "where email = ?");
            pstmt.setString(1, email);

            ResultSet usersRs = pstmt.executeQuery();

            usersRs.next();
            user = new User(
                    usersRs.getString(1),
                    usersRs.getString(2),
                    usersRs.getString(3),
                    usersRs.getString(4),
                    usersRs.getString(5),
                    usersRs.getString(6),
                    usersRs.getString(7),
                    usersRs.getInt(8)
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return user;
    }

    public void update(User user) {
        try {
            String userId = user.getUserId();
            String firstName = user.getFirstName();
            String lastName = user.getLastName();
            String phone = user.getPhone();
            String email = user.getEmail();
            String password = user.getPassword();
            String userStatusId = user.getUserStatusId();

            CallableStatement oCSF = connection.prepareCall("{call sp_update_user(?,?,?,?,?,?,?)}");
            oCSF.setString(1, userId);
            oCSF.setString(2, firstName);
            oCSF.setString(3, lastName);
            oCSF.setString(4, phone);
            oCSF.setString(5, email);
            oCSF.setString(6, password);
            oCSF.setString(7, userStatusId);
            oCSF.execute();
            oCSF.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
//get location id from locations table
    public int getLocationID(String u_id){
        int loc_id;
        try {
            CallableStatement oCSF = connection.prepareCall("{?=call FN_GETLOCATIONID(?)}");
            oCSF.setString(2, u_id);
            oCSF.registerOutParameter(1, Types.INTEGER);
            oCSF.execute();
            loc_id=oCSF.getInt(1);
            oCSF.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            return 1;
        }
        
        return loc_id;
    }
}
