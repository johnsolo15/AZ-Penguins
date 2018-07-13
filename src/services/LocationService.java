package services;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import domain.Location;
import java.sql.PreparedStatement;

public class LocationService implements Service<Location> {

    Connection connection;

    public LocationService() {
        super();
    }

    public LocationService(Connection connection) {
        super();
        this.connection = connection;
    }

    @Override
    public boolean add(Location location) {
        try {
            CallableStatement oCSF;
            String userId = location.getUserId();
            String street = location.getStreet();
            String city = location.getCity();
            String state = location.getState();
            String country = location.getCountry();
            String zip = location.getZip();

            oCSF = connection.prepareCall("{call SP_INSERT_LOCATION(?,?,?,?,?,?,?)}");
            oCSF.setString(1,userId);
            oCSF.setDouble(2,18.4);
            oCSF.setString(3,street);
            oCSF.setString(4,city);
            oCSF.setString(5,state);
            oCSF.setString(6,country);
            oCSF.setString(7,zip);
            oCSF.executeUpdate();
            oCSF.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void deleteById(String id) {
        try {
            Statement locationsSt = connection.createStatement();
            locationsSt.executeQuery("Delete from locations where location_id = " + id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Location> getAll() {

        ArrayList<Location> locations = new ArrayList<Location>();

        try {
            Statement locationsSt = connection.createStatement();
            ResultSet locationsRs = locationsSt.executeQuery("Select * from Locations");

            while (locationsRs.next()) {
                Location location = new Location(
                        locationsRs.getString(1),
                        locationsRs.getString(2),
                        locationsRs.getString(3),
                        locationsRs.getString(4),
                        locationsRs.getString(5),
                        locationsRs.getString(6)
                );
                locations.add(location);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return locations;
    }

    public Location getById(String id) {
        Location location = null;

        try {
            Statement locationsSt = connection.createStatement();
            ResultSet locationsRs = locationsSt.executeQuery("Select * from Locations where location_id = " + id);

            locationsRs.next();
            location = new Location(
                    locationsRs.getString(1),
                    locationsRs.getString(2),
                    locationsRs.getString(3),
                    locationsRs.getString(4),
                    locationsRs.getString(5),
                    locationsRs.getString(6)
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return location;
    }

    public void update(Location location) {
        try {
            
            String street = location.getStreet();
            String city = location.getCity();
            String state = location.getState();
            String country = location.getCountry();
            String zip = location.getZip();

            CallableStatement oCSF = connection.prepareCall("{?=call sp_update_location(?,?,?,?,?)}");
           
            oCSF.setString(3, street);
            oCSF.setString(4, city);
            oCSF.setString(5, state);
            oCSF.setString(6, country);
            oCSF.setString(7, zip);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Location> getUserLocations(String userId) {

        ArrayList<Location> locations = new ArrayList<Location>();

        try {
            Statement locationsSt = connection.createStatement();
            ResultSet locationsRs = locationsSt.executeQuery("Select * from Locations where user_id = '" + userId + "'");

            while (locationsRs.next()) {
                Location location = new Location(
                        locationsRs.getString(1),
                        locationsRs.getString(2),
                        locationsRs.getString(3),
                        locationsRs.getString(4),
                        locationsRs.getString(5),
                        locationsRs.getString(6)
                );
                locations.add(location);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return locations;
    }

}
