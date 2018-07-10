package cli;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import domain.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import services.MenuServices;
import services.OrderService;
import services.UserService;

public class ServiceWrapper {

    Connection con;

    public ServiceWrapper(Connection con) {
        super();
        this.con = con;

    }

    public User login(String email, String password) {

        UserService us = new UserService(con);
        User candidate = us.getByEmail(email);
        System.out.println(candidate.getFirstName());
        if (password.equals(candidate.getPassword())) {
            return candidate;
        } else {
            return null;
        }
    }

    public User register(String firstName, String lastName, String phone, String email, String password) {
        //, String street, String city, String state, String country, String zip, String userStatus
        boolean result = false;
        String userId = Double.toString(Math.random() * 10001);
        String userStatusId = "1";

        User user = new User(userId, firstName, lastName, phone, email, password, userStatusId);
        UserService us = new UserService(con);
        result = us.add(user);
        return user;
    }

    public static void printOptions(ArrayList<String> options) {
        options.add("Go back");
        int count = 0;
        for (String option : options) {
            count++;
            System.out.println(count + ". " + option);
        }

    }

    public static void printMenuItems(ArrayList<Menu> menus) {
        int count = 0;
        for (Menu menu : menus) {
            count++;
            System.out.println(count + ". $" + menu.getPrice() + " " + menu.getName());
        }
        System.out.println(++count + ". Go Back");
    }

    public static void printOrders(ArrayList<Order> orders) {
        int count = 0;
        for (Order order : orders) {
            count++;
            System.out.println(count + ". " + order.getPlaced_timestamp());
        }
        System.out.println(count++ + ". Go Back");
    }

    public void cancelOrder(Order order) {
        order.setDelivery_status_id("3");
        OrderService os = new OrderService(con);
        os.update(order);
    }

    public void submitOrder(Order currentOrder) {
        Scanner inScan = new Scanner(System.in);
        // TODO Auto-generated method stub

        currentOrder.setDelivery_status_id("0");
        currentOrder.setPlaced_timestamp(new Timestamp(System.currentTimeMillis()));
        System.out.println("Enter Delevery date in the format 'yyyy-MM-dd HH:mm:ss' inter hours in 24 format");
        String tempStamp = inScan.nextLine();
        
        //SimpleDateFormat STAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh24:mm:ss");
        //Date TS= STAMP_FORMAT.parse(tempStamp);
        currentOrder.setDelivery_timestamp(Timestamp.valueOf(tempStamp));
        OrderService os = new OrderService(con);
        os.add(currentOrder);

    }

    public ArrayList<Menu> getMenuItems(ArrayList<String> itemIds) {

        MenuServices ms = new MenuServices(con);
        ArrayList<Menu> items = new ArrayList<Menu>();

        for (String itemId : itemIds) {
            items.add(ms.getById(itemId));
        }

        return items;
    }

    public int calculateTotalPrice(ArrayList<String> item_ids) {
        int total = 0;
        ServiceWrapper sw = new ServiceWrapper(con);
        ArrayList<Menu> items = sw.getMenuItems(item_ids);
        for (Menu item : items) {
            total += item.getPrice();
        }
        return total;
    }

}
