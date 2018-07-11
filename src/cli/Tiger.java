package cli;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.lang.model.util.ElementScanner6;

import domain.Menu;
import domain.Order;
import domain.Store;
import domain.User;
import java.sql.Timestamp;
import services.MenuServices;
import services.OrderService;
import services.StoreService;
import services.UserService;

public class Tiger {

    public static ServiceWrapper sw;
    public static Connection con;
    public static User currentUser;
    public static Order currentOrder;
    public static Store currentStore;
    public static int gate;

    static Scanner sc;

    public static void main(String[] args) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "db_uSpring", "pass");
        } catch (Exception e) {
            e.printStackTrace();
        }
        sw = new ServiceWrapper(con);
        sc = new Scanner(System.in);
        firstScreen();
        sc.close();
    }

    public static void firstScreen() 
    {
            gate = 0;
            while(gate == 0)
            {
                System.out.println(" __  __ _                     _ _        _____       __     \n|  \\/  (_)                   (_| )      / ____|     / _|    \n| \\  / |_ _ __ ___  _ __ ___  _|/ ___  | |     __ _| |_ ___ \n| |\\/| | | '_ ` _ \\| '_ ` _ \\| | / __| | |    / _` |  _/ _ \\\n| |  | | | | | | | | | | | | | | \\__ \\ | |___| (_| | ||  __/\n|_|  |_|_|_| |_| |_|_| |_| |_|_| |___/  \\_____\\__,_|_| \\___|");
                ArrayList<String> options = new ArrayList<String>();
                options.add("Login");
                options.add("Register");
                options.add("Quit");
                StoreService ss = new StoreService(con);
                currentStore = ss.getById("0");

                int count = 0;
                for (String option : options) 
                {
                    count++;
                    System.out.println(count + ". " + option);
                }
                    String strIn = sc.nextLine();
                

                if(strIn.matches("\\d+"))
                {
                    int input = Integer.parseInt(strIn);

                    if(input>=1 && input<=4)
                    {
                        gate++;
                        switch (input) 
                        {
                            case 1:
                                loginScreen();
                                break;
                            case 2:
                                registerScreen();
                                break;
                            case 3:
                                System.out.println("Goodbye");
                                System.exit(0);
                                break;
                            case 4:
                                AdminAndManager aam = new AdminAndManager(con);
                                aam.adminScreen();
                                break;
                        }
                    }
                }


        }


    }

    public static void loginScreen() 
    {

            System.out.println("\n*Login*");
            System.out.println("Enter email:");
            String email = sc.nextLine();

            if(isValidEmail(email) == false)
            {
                System.out.println("Not a proper email");
                firstScreen();
            }
            else
            {
                System.out.println("Enter password:");
                String password = sc.nextLine();
        
                UserService us = new UserService(con);
                User candidate = us.getByEmail(email);
                if (candidate == null) 
                {
                    System.out.println("Wrong email");
                    firstScreen();
                }
                else
                {
                    if (password.equals(candidate.getPassword())) 
                    {
                        currentUser = candidate;
                        currentOrder = new Order();
                        currentOrder.setOrder_id(Double.toString(Math.random() * 10001));
                        currentOrder.setUser_id(currentUser.getUserId());
                        currentOrder.setDelivery_status_id("0");
                        //currentOrder.setCard_id();
                        homeScreen();
                    } 
                    else 
                    {
                        System.out.println("Wrong email or password");
                        try 
                        {
                            TimeUnit.SECONDS.sleep(1);
                            firstScreen();
                        } 
                        catch (InterruptedException e) 
                        {
                            e.printStackTrace();
                        }
                    }
                }

            }

    }

    public static void registerScreen() 
    {
            System.out.println("\n*Register*");
            String email = "";
            String password = "";
            String first = "";
            String last = "";
            String phone = "";
            gate = 0;

            while(gate == 0)
            {
                System.out.println("Enter email:");
                email = sc.nextLine();
                
                if(isValidEmail(email) == false)
                {
                    System.out.println("Not a valid email");
                }
                else
                {
                    gate++;
                }

            }
            gate--;
            
            while(gate == 0)
            {
                System.out.println("Enter password:");
                password = sc.nextLine();
                System.out.println("Enter password again:");
                String passwordConfirm = sc.nextLine();

                if (isValidText(password) == true && password.equals(passwordConfirm)) 
                {
                    gate++;
                }
                else
                {
                    System.out.println("Please type in a correct password and confirmation of said password without spaces");
                }

            }
            gate--;

            while(gate == 0)
            {
                System.out.println("Enter first name:");
                first = sc.nextLine();

                if(isValidText(first))
                {
                    gate++;
                }
                else
                {
                    System.out.println("Please fill in a name please, no spaces");
                }
            }
            gate--;

            while(gate == 0)
            {
                System.out.println("Enter last name:");
                last = sc.nextLine();

                if(isValidText(last))
                {
                    gate++;
                }
                else
                {
                    System.out.println("Please fill in a name please, no spaces");
                }
            }
            gate--;


            while(gate == 0)
            {
                System.out.println("Enter phone:");
                phone = sc.nextLine();
              
                    if(isValidText(phone) && phone.length() == 10 && phone.matches("\\d+"))
                    {
                        gate++;
                    }
                    else
                    {
                        System.out.println("Phone number needs to be 10 digits long and have no spaces.");
                    }    


            }
        
            System.out.println("Registered");
            currentUser = sw.register(first, last, phone, email, password);
            currentOrder = new Order();
            currentOrder.setOrder_id(Double.toString(Math.random() * 10001));
            currentOrder.setUser_id(currentUser.getUserId());
            currentOrder.setDelivery_status_id("0");
            homeScreen();
        
    }

    public static void homeScreen() {
        gate = 0;
        System.out.println("Welcome " + currentUser.getFirstName());
        
        while(gate == 0)
        {
            System.out.println("\n*Home*");
            ArrayList<String> options = new ArrayList<String>();
            options.add("Menu");
            options.add("Order");
            options.add("Account");
            options.add("Store Details");
            options.add("Logout");
            options.add("Quit");
        
            int count = 0;
            for (String option : options) {
                count++;
                System.out.println(count + ". " + option);
            }
            String inStr = sc.nextLine();
            if(inStr.matches("\\d+"))
            {
                int input = Integer.parseInt(inStr);
                if(input <= options.size() && input > 0 )
                {
                    gate++;
                    if (input == 1) 
                    {
                        menuScreen();
                    }
                    if (input == 2) {
                        currentOrderScreen();
                    }
                    if (input == 3) {
                        accountScreen();
                    }
                    if (input == 4) {
                        storeDetailsScreen();
                    }
                    if (input == 5) {
                        firstScreen();
                    }
                    if (input == 6) {
                        System.out.println("Goodbye");
                        System.exit(0);
                    }
                }

            }

        }
    }

    public static void menuScreen() 
    {
         gate = 0;
        while(gate == 0)
        {

            System.out.println("\n*Menu*");
            MenuServices ms = new MenuServices(con);
            ArrayList<Menu> menus = ms.getAll();
            ServiceWrapper.printMenuItems(menus);

            String strIn = sc.nextLine();
            if(strIn.matches("\\d+"))
            {
                int input = Integer.parseInt(strIn);
                if(input <= menus.size() + 1 && input > 0)
                {
                    if (input == menus.size() + 1) 
                    {
                        homeScreen();
                    } 
                    else 
                    {
                        menuItemScreen(menus.get(input - 1));
                    }
                }

            }

        }
    }

    public static void menuItemScreen(Menu menu) 
    {
        gate = 0;
        while(gate == 0)
        {
            System.out.println("\n*" + menu.getName() + "*");
            System.out.println(menu.getDescription());
            System.out.println("$" + menu.getPrice());
            System.out.println("1. Enter Quantity");
            System.out.println("2. Go Back");
            String strInp = sc.nextLine();
            if(strInp.matches("\\d+"))
            {
                int input = Integer.parseInt(strInp);
                if (input == 1) 
                {
                    itemQuantityScreen(menu);
                    gate++;
                } 
                else 
                {
                    if (input == 2) 
                    {
                        menuScreen();
                        gate++;
                    }
                }
            }
        }
    }
    //TODO finish this

    public static void itemQuantityScreen(Menu menu) 
    {
        gate = 0;
        while(gate==0)
        {
            System.out.println("Enter Quantity");
            String strInp = sc.nextLine();
            
            if(strInp.matches("\\d+"))
            {
                int input = Integer.parseInt(strInp);

                if(input>0)
                {
                    gate++;
                    for (int i = 0; i < input; i++)    
                    {
                        currentOrder.addItem_id(menu.getId());
                    }
                    System.out.println("Item(s) added");
                    menuScreen();
                }
            }
        }
    }

    public static void currentOrderScreen() 
    {
        gate = 0;
        while(gate==0)
        {
            System.out.println("\n*Current Order*");
            System.out.println("Placed: " + currentOrder.getPlaced_timestamp());
            System.out.println("Delivered: " + currentOrder.getDelivery_timestamp());
            ServiceWrapper sw = new ServiceWrapper(con);
            currentOrder.setTotal_price(sw.calculateTotalPrice(currentOrder.getItem_ids()));
            System.out.println("Total price: $" + currentOrder.getTotal_price());
            System.out.println("Method: " + currentOrder.getDelivery_method_id());
            System.out.println("Status: " + currentOrder.getDelivery_status_id());
            System.out.println("1. Cancel");
            System.out.println("2. View\\Edit Items");
            System.out.println("3. Edit Order");
            System.out.println("4. Submit Order");
            System.out.println("5. Go Back");
            String strInp = sc.nextLine();
            
            if(strInp.matches("\\d+"))
            {
                
                int input = Integer.parseInt(strInp);
                if(input > 0 && input <=5)
                {
                    gate++;
                    if (input == 1 && confirm()) 
                    {
                        currentOrder = new Order();
                        currentOrder.setOrder_id(Double.toString(Math.random() * 10001));
                        currentOrder.setUser_id(currentUser.getUserId());
                        currentOrder.setDelivery_status_id("0");
                        System.out.println("Order Canceled");
                        homeScreen();
                    }
                    if (input == 2) 
                    {
                        viewEditOrderItems(currentOrder);
                    }
                    if (input == 3) 
                    {
                        editOrder(currentOrder);
                    }
                    if (input == 4 && confirm()) 
                    {
                        sw.submitOrder(currentOrder);
                        System.out.println("Order Sent");
                        homeScreen();
                    } 
                    else 
                    {
                        if (input == 5) 
                        {
                            homeScreen();
                        }
                    }
                }
            }
        }
    }

    private static void editOrder(Order currentOrder2) 
    {
        gate = 0;
        while(gate == 0)
        {
            System.out.println("\n*Edit Order*");
            ArrayList<String> options = new ArrayList<String>();
            options.add("Edit Tip");
            options.add("Edit delivery time");
            options.add("Edit Instructions");
            options.add("Edit Delivery Method");
            options.add("Edit Store");
            options.add("Go Back");
            int count = 0;
            for (String option : options) 
            {
                count++;
                System.out.println(count + ". " + option);
            }

            String strInp = sc.nextLine();

            if(strInp.matches("\\d+"))
            {
                int input = Integer.parseInt(strInp);

                if(input > 0 && input <=6)
                {
                    if (input == 1) 
                    {
                        strInp = editString();
                        if(strInp.matches("\\d+"))
                        {
                            int newTip = Integer.parseInt(strInp);

                            if(newTip < 0)
                            {
                                System.out.println("Tip has to be zero or greater.");
                            }
                            else
                            {
                                gate++;
                                currentOrder.setTip(newTip);
                                System.out.println("Tip Changed to: $" + newTip);
                                currentOrderScreen();
                            }
                        }
                        else
                        {
                            System.out.println("Not a number");
                        }

                    }
                    if (input == 2) 
                    {
                        gate++;
                        int newDelivery_timestamp = Integer.parseInt(editString());
                        //currentOrder.setDelivery_timestamp(newDelivery_timestamp);
                        System.out.println("Delivery Time Changed to: " + newDelivery_timestamp);
                        currentOrderScreen();
                    }
                    if (input == 3) 
                    {
                        gate++;
                        String newInstructions = editString();
                        currentOrder.setInstuctions(newInstructions);
                        System.out.println("Instructions Changed to: " + newInstructions);
                        currentOrderScreen();
                    }
                    if (input == 4) 
                    {
                        gate++;
                        String newDelivery_method = editString();
                        currentOrder.setDelivery_method_id(newDelivery_method);
                        System.out.println("Delivery Method Changed to: " + newDelivery_method);
                        currentOrderScreen();
                    }
                    if (input == 5) 
                    {
                        gate++;
                        String newStore = editString();
                        currentOrder.setStore_id(newStore);
                        System.out.println("Delivery Method Changed to: " + newStore);
                        currentOrderScreen();
                    }
        
                    if (input == 6) 
                    {
                        gate++;
                        homeScreen();
                    }
        
                }

            }

        }

    }

    //TODO get item from item id here
    private static void viewEditOrderItems(Order order) 
    {
        gate = 0;

        while(gate==0)
        {
                
            System.out.println("*View Items*");
            ArrayList<String> itemIds = currentOrder.getItem_ids();
            ArrayList<Menu> items = sw.getMenuItems(itemIds);
            if (items.isEmpty()) 
            {
                System.out.println("No items");
            }
            ServiceWrapper.printMenuItems(items);
    
            String strInp = sc.nextLine();
            if(strInp.matches("\\d+"))
            {
 
                int input = Integer.parseInt(strInp);
                if (input <= items.size() && input > 0) 
                {
                    gate++;
                    orderItemScreen(items.get(input-1));
                    viewEditOrderItems(order);
                } 
                else 
                {  
                    if (input == items.size() + 1) 
                    {
                        gate++;
                        currentOrderScreen();
                    } 
                }
            
            }

        }
    }

    public static void orderItemScreen(Menu menu) {
        /*System.out.println(menu.getName());
		System.out.println(menu.getDescription());
		System.out.println(menu.getPrice());
		System.out.println("1. Enter Quantity");
		System.out.println("2. Go Back");
		Scanner sc = new Scanner(System.in);
	    int input = sc.nextInt();
	    if(input==1) itemQuantityScreen(menu);
	    else if(input==2) System.exit(0);*/
    }

    //TODO
    public static void submitOrder() {
        System.out.println("\n*Submit*");

        //OrderService os = new OrderService(con);
        //input should be equal to number of items in order
        //Menu menu = null;
        // int input = 0;
        //for(int i=0;i<input;i++){
        //create order item and add to item
        //os.addItem_id(menu.getId(), currentOrder.getOrder_id());
        // }
        //OrderService os = new OrderService(con);
        //os.update(currentOrder);
    }

    public static void accountScreen() {
        System.out.println("\n*Account*");
        ArrayList<String> options = new ArrayList<String>();
        options.add("Edit First Name");
        options.add("Edit Last Name");
        options.add("Edit Email");
        options.add("Edit Password");
        options.add("Edit Phone Number");
        options.add("Edit Payment Options");
        options.add("Edit Saved Locations");
        options.add("View Orders");
        options.add("Go Back");
        int count = 0;
        for (String option : options) {
            count++;
            System.out.println(count + ". " + option);
        }
        int input = sc.nextInt();
        if (input == 1) {
            String newFirstName = editString();
            currentUser.setFirstName(newFirstName);
            System.out.println("First Name Changed to: " + newFirstName);
        }
        if (input == 2) {
            String newLastName = editString();
            currentUser.setLastName(newLastName);
            System.out.println("Last Name Changed to: " + newLastName);
        }
        if (input == 3) {
            String newEmail = editString();
            currentUser.setEmail(newEmail);
            System.out.println("Email Changed to: " + newEmail);
        }
        if (input == 4) {
            String newPassword = editString();
            currentUser.setPassword(newPassword);
            System.out.println("Password Changed to: " + newPassword);
        }
        if (input == 5) {

            String newPhoneNumber = editString();
            currentUser.setPhone(newPhoneNumber);
            System.out.println("Phone Number Changed to: " + newPhoneNumber);
        }
        if (input == 6) {
            editCards();
        }
        if (input == 7) {
            editLocations();
        }
        if (input == 8) {
            allOrdersScreen();
        }
        if (input == 9) {
            homeScreen();
        }

        UserService us = new UserService(con);
        us.update(currentUser);
        accountScreen();
    }

    private static void editLocations() {
        // TODO Auto-generated method stub

    }

    private static void editCards() {
        // TODO Auto-generated method stub

    }

    private static String editString() {
        System.out.println("Enter new value");
        String inp = sc.nextLine();
        return inp;
    }

    public static void allOrdersScreen() {
        System.out.println("\n*All orders*");
        OrderService os = new OrderService(con);
        ArrayList<Order> orders = os.getUserOrders(currentUser.getUserId());
        ServiceWrapper.printOrders(orders);
        int input = sc.nextInt();
        if (input == orders.size()) {
            homeScreen();
        } else {
            oldOrderScreen(orders.get(input));
        }
    }

    public static void oldOrderScreen(Order order) {
        System.out.println("Placed: " + order.getPlaced_timestamp());
        System.out.println("Delivered: " + order.getDelivery_timestamp());
        System.out.println("Total price: " + order.getTotal_price());
        System.out.println("Method: " + order.getDelivery_method_id());
        System.out.println("Status: " + order.getDelivery_status_id());
        System.out.println("1. Reorder");
        System.out.println("2. Go Back");
        int input = sc.nextInt();
        if (input == 1 && confirm()) {
            currentOrder = order;
            //TODO find out what the status id this thing needs is
            currentOrder.setDelivery_status_id("1");
        } else if (input == 2) {
            accountScreen();
        }
    }

    public static void storeDetailsScreen() 
    {
        System.out.println("\n*Store*");
        System.out.println("Name: " + currentStore.getStoreName());
        System.out.println("Phone Number: " + currentStore.getPhoneNumber());
        System.out.println("Location: " + currentStore.getLocationId());
        System.out.println("Open: " + currentStore.getOpenTime());
        System.out.println("Close: " + currentStore.getCloseTime());
        homeScreen();
    }

    public static boolean confirm() 
    {
        gate = 0;
        boolean endOf = false;
        while(gate == 0)
        {
            System.out.println("\n1*Confirm*");
            System.out.println("1. Yes");
            System.out.println("2. No");
    
            String strInp = sc.nextLine();
                
            if(strInp.matches("\\d+"))
            {
    
                int input = Integer.parseInt(strInp);
    
                if (input == 1) 
                {
                    gate++;
                    endOf = true;
                }
                else
                {
                    if(input == 2)
                    {
                        gate++;
                        endOf = false;
                    }
                }
            }
        }
        return endOf;

    }

    public static boolean isValidEmail(String email)
    {
        String emailStringRules = "^[a-zA-Z0-9_+&*-]+(?:\\."+
        "[a-zA-Z0-9_+&*-]+)*@" +
        "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
        "A-Z]{2,7}$";
         
        Pattern pat = Pattern.compile(emailStringRules);

        if (email == null || email.isEmpty())
        {
            return false;
        }

        return pat.matcher(email).matches();
    }

    public static boolean isValidText(String text)
    {   
        if(!text.isEmpty() && text != null && text.indexOf(' ')<0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
