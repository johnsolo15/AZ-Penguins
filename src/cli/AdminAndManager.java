package cli;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import domain.Card;
import domain.ItemType;
import domain.Location;
import domain.Menu;
import domain.Order;
import domain.Store;
import domain.User;
import java.text.ParseException;
import services.CardService;
import services.DeliveryMethod;
import services.DeliveryMethodService;
import services.DeliveryStatus;
import services.DeliveryStatusService;
import services.ItemTypeService;
import services.LocationService;
import services.MenuServices;
import services.OrderService;
import services.StoreService;
import services.UserService;

public class AdminAndManager {

    static Connection con;

    public AdminAndManager(Connection con) {
        AdminAndManager.con = con;
    }

    public void adminScreen() {
        ArrayList<String> options = new ArrayList<String>();
        System.out.println("Admin View");
        options.add("Alter Cards");
        options.add("Alter Combos");
        options.add("Alter Delivery Methods");
        options.add("Alter Delivery Statuses");
        options.add("Alter Items");
        options.add("Alter Item Types");
        options.add("Alter Locations");
        options.add("Alter Orders");
        options.add("Alter Order_items"); //Probably don't need this one
        options.add("Alter Users");
        options.add("Alter User Statuses");
        ServiceWrapper.printOptions(options);
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        int option = 0;
        switch (input) {
            case 1: {
                option = optionsScreen("Card");
                switch (option) {
                    case 1:
                        alterCardScreen();
                        break;
                    case 2:
                        addCardScreen();
                        break;
                    case 3:
                        deleteCardScreen();
                        break;
                    case 4:
                        break;
                }
                break;
            }
            case 3:
                option = optionsScreen("Delivery Method");
                switch (option) {
                    case 1:
                        alterDeliveryMethodScreen();
                        break;
                    case 2:
                        addDeliveryMethodScreen();
                        break;
                    case 3:
                        deleteDeliveryMethodScreen();
                        break;
                    case 4:
                        break;
                }
                break;
            case 4:
                option = optionsScreen("Delivery Status");
                switch (option) {
                    case 1:
                        alterDeliveryStatusScreen();
                        break;
                    case 2:
                        addDeliveryStatusScreen();
                        break;
                    case 3:
                        deleteDeliveryStatusScreen();
                        break;
                    case 4:
                        break;
                }
                break;
            case 5: {
                option = optionsScreen("Item");
                switch (option) {
                    case 1:
                        alterItemScreen();
                        break;
                    case 2:
                        addItemScreen();
                        break;
                    case 3:
                        deleteItemScreen();
                        break;
                    case 4:
                        break;
                }
                break;
            }
            case 6:
                option = optionsScreen("Item Type");
                switch (option) {
                    case 1:
                        alterItemTypeScreen();
                        break;
                    case 2:
                        addItemTypeScreen();
                        break;
                    case 3:
                        deleteItemTypeScreen();
                        break;
                    case 4:
                        break;
                }
                break;
            case 7:
                option = optionsScreen("Location");
                switch (option) {
                    case 1:
                        alterLocationScreen();
                        break;
                    case 2:
                        addLocationScreen();
                        break;
                    case 3:
                        deleteLocationScreen();
                        break;
                    case 4:
                        break;
                }
                break;
            case 8:
                optionsScreen("Order");
                break;
            case 9:
                optionsScreen("Order Item");
                break;
            case 10: {
                option = optionsScreen("User");
                switch (option) {
                    case 1:
                        System.out.println("not yet supported");
                        break;
                    case 2:
                        addUserScreen();
                        break;
                    case 3:
                        deleteUserScreen();
                        break;
                }
                break;
            }
            case 11:
                optionsScreen("User Statuse");
                break;
            case 12:
                break;
            case 13:
                System.exit(0);
        }

        adminScreen();

    }

    public static int optionsScreen(String thing) {
        System.out.println("How would you like to alter " + thing);
        ArrayList<String> options = new ArrayList<String>();
        options.add("Alter");
        options.add("Add");
        options.add("Delete");
        ServiceWrapper.printOptions(options);
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        return input;
    }

    //Doesn't work
    public static void addCardScreen() {
        System.out.println("Add a Credit Card");
        Scanner sc = new Scanner(System.in);
        System.out.println("\nEnter Card id: ");
        String cardId = sc.next();
        System.out.println("\nEnter id of user this card belongs to: ");
        String userId = sc.next();
        System.out.println("\nEnter Card number: ");
        String cardNumber = sc.next();
        System.out.println("\nEnter expiration year: ");
        int year = sc.nextInt();
        System.out.println("\nEnter expiration month: ");
        int month = sc.nextInt();
        System.out.println("\nEnter expiration date: ");
        int day = sc.nextInt();
        Date expiryDate = new Date(year, month, day);
        System.out.println("Enter Security code: ");
        String securityCode = sc.next();
        Card c = new Card(cardId, userId, cardNumber, expiryDate, securityCode);

        CardService cs = new CardService(con);
        cs.add(c);
    }

    public static void deleteCardScreen() {
        System.out.println("List of cards");
        CardService cs = new CardService(con);
        ArrayList<Card> cl = cs.getAll();
        int count = 1;
        for (Card c : cl) {
            System.out.println(count + ": " + c.getCardNumber());
            count++;
        }
        System.out.println("Select card you'd like to delete");
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        cs.deleteById(cl.get(input - 1).getCardId());
        System.out.println("Deleted Card");
    }

    public static void alterCardScreen() {
        System.out.println("List of cards");
        CardService cs = new CardService(con);
        ArrayList<Card> cl = cs.getAll();
        int count = 1;
        for (Card c : cl) {
            System.out.println(count + ": " + c.getCardNumber());
            count++;
        }
        System.out.println("Enter the number of the card you'd like to alter");
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();

        String cardId = cl.get(input - 1).getCardId();
        System.out.println("Enter id of user this card belongs to: ");
        String userId = sc.next();
        System.out.println("Enter Card number: ");
        String cardNumber = sc.next();
        System.out.println("Enter expiration year: ");
        int year = sc.nextInt();
        System.out.println("Enter expiration month: ");
        int month = sc.nextInt();
        System.out.println("Enter expiration date: ");
        int day = sc.nextInt();
        Date expiryDate = new Date(year, month, day);
        System.out.println("Enter Security code: ");
        String securityCode = sc.next();

        Card c = new Card(cardId, userId, cardNumber, expiryDate, securityCode);
        cs.update(c);
    }

    public static void addDeliveryMethodScreen() {
        System.out.println("Add a delivery method");
        Scanner sc = new Scanner(System.in);
        System.out.println("\nEnter delivery method id: ");
        String id = sc.nextLine();
        System.out.println("\nEnter delivery method type: ");
        String method = sc.nextLine();
        DeliveryMethod dm = new DeliveryMethod(id, method);
        DeliveryMethodService dms = new DeliveryMethodService(con);
        if (dms.add(dm)) {
            System.out.println("Added delivery method");
        }  else {
            System.out.println("Couldn't add delivery method");
        }
    }
    
    public static void deleteDeliveryMethodScreen() {
        System.out.println("List of delivery methods");
        DeliveryMethodService dms = new DeliveryMethodService(con);
        ArrayList<DeliveryMethod> methods = dms.getAll();
        int count = 1;
        for (DeliveryMethod m : methods) {
            System.out.println(count + ". " + m.getDelivery_method());
            count++;
        }
        System.out.println("Select delivery method you'd like to delete");
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                int num = Integer.parseInt(sc.nextLine());
                String id = methods.get(num - 1).getDelivery_method_id();
                dms.deleteById(id);
                System.out.println("Delivery method deleted");
                break;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Enter a number within range");
            } catch (NumberFormatException e) {
                System.out.println("Enter a number");
            }
        }
    }
    
    public static void alterDeliveryMethodScreen() {
        System.out.println("List of delivery methods");
        DeliveryMethodService dms = new DeliveryMethodService(con);
        ArrayList<DeliveryMethod> methods = dms.getAll();
        int count = 1;
        for (DeliveryMethod m : methods) {
            System.out.println(count + ". " + m.getDelivery_method());
            count++;
        }
        System.out.println("Select delivery method you'd like to alter");
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                int num = Integer.parseInt(sc.nextLine());
                String id = methods.get(num - 1).getDelivery_method_id();
                System.out.println("\nEnter delivery method type: ");
                String method = sc.nextLine();
                DeliveryMethod dm = new DeliveryMethod(id, method);
                dms.update(dm);
                System.out.println("Delivery method updated");
                break;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Enter a number within range");
            } catch (NumberFormatException e) {
                System.out.println("Enter a number");
            }
        }
    }
    
    public static void addDeliveryStatusScreen() {
        System.out.println("Add a delivery status");
        Scanner sc = new Scanner(System.in);
        System.out.println("\nEnter delivery status id: ");
        String id = sc.nextLine();
        System.out.println("\nEnter delivery status type: ");
        String status = sc.nextLine();
        DeliveryStatus ds = new DeliveryStatus(id, status);
        DeliveryStatusService dss = new DeliveryStatusService(con);
        dss.add(ds);
        System.out.println("Added delivery method");
    }
    
    public static void deleteDeliveryStatusScreen() {
        System.out.println("List of delivery statuses");
        DeliveryStatusService dss = new DeliveryStatusService(con);
        ArrayList<DeliveryStatus> statuses = dss.getAll();
        int count = 1;
        for (DeliveryStatus s : statuses) {
            System.out.println(count + ". " + s.getDelivery_status());
            count++;
        }
        System.out.println("Select delivery status you'd like to delete");
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                int num = Integer.parseInt(sc.nextLine());
                String id = statuses.get(num - 1).getDelivery_status_id();
                dss.deleteByID(id);
                System.out.println("Delivery status deleted");
                break;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Enter a number within range");
            } catch (NumberFormatException e) {
                System.out.println("Enter a number");
            }
        }
    }
    
    public static void alterDeliveryStatusScreen() {
        System.out.println("List of delivery statuses");
        DeliveryStatusService dss = new DeliveryStatusService(con);
        ArrayList<DeliveryStatus> statuses = dss.getAll();
        int count = 1;
        for (DeliveryStatus s : statuses) {
            System.out.println(count + ". " + s.getDelivery_status());
            count++;
        }
        System.out.println("Select delivery status you'd like to alter");
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                int num = Integer.parseInt(sc.nextLine());
                String id = statuses.get(num - 1).getDelivery_status_id();
                System.out.println("\nEnter delivery status type: ");
                String status = sc.nextLine();
                DeliveryStatus ds = new DeliveryStatus(id, status);
                dss.update(ds);
                System.out.println("Delivery status updated");
                break;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Enter a number within range");
            } catch (NumberFormatException e) {
                System.out.println("Enter a number");
            }
        }
    }
    
    public static void addItemScreen() {
        System.out.println("Add an item");
        Scanner sc = new Scanner(System.in);
        System.out.println("\nEnter item id: ");
        String id = sc.next();
        System.out.println("\nEnter item name: ");
        sc.nextLine();
        String name = sc.nextLine();
        System.out.println("\nEnter vegeterian (y or n): ");
        String vege = sc.next();
        char vegetarian = vege.charAt(0);
        System.out.println("\nEnter a description: ");
        sc.nextLine();
        String description = sc.nextLine();
        System.out.println("\nEnter type number id: ");
        String type = sc.next();
        System.out.println("\nEnter meal time: ");
        String slot_ID = sc.next();
        System.out.println("\nEnter photo link: ");
        String photo = sc.next();
        System.out.println("\nEnter a price: ");
        float price = sc.nextFloat();

        Menu men = new Menu(id, name, vegetarian, type, description, slot_ID, photo, price);
        MenuServices menServ = new MenuServices(con);
        menServ.add(men);
        System.out.println("\n" + name + " added to database\n");

    }

    public static void deleteItemScreen() {
        System.out.println("Choose an item to delete");
        MenuServices ms = new MenuServices(con);
        ArrayList<Menu> menus = ms.getAll();
        ServiceWrapper.printMenuItems(menus);
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                int input = Integer.parseInt(sc.nextLine());
                if (input == menus.size() + 1) {
                    return;
                }
                MenuServices menServ = new MenuServices(con);
                menServ.deleteById(menus.get(input - 1).getId());
                System.out.println("Deleted " + menus.get(input - 1).getName());
                break;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Enter a number within range");
            } catch (NumberFormatException e) {
                System.out.println("Enter a number");
            }
        }
    }

    public static void alterItemScreen() {
        System.out.println("Choose an item to alter");
        MenuServices ms = new MenuServices(con);
        ArrayList<Menu> menus = ms.getAll();
        ServiceWrapper.printMenuItems(menus);
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                int input = Integer.parseInt(sc.nextLine());
                if (input == menus.size() + 1) {
                    return;
                }
                Menu men = menus.get(input - 1);
                MenuServices menServ = new MenuServices(con);
                System.out.println("Enter item name: ");
                String name = sc.nextLine();
                System.out.println("Enter vegeterian (y or n): ");
                String vege = sc.nextLine();
                char vegetarian = vege.charAt(0);
                System.out.println("Enter a description: ");
                String description = sc.nextLine();
                System.out.println("Enter type number id: ");
                String type = sc.nextLine();
                System.out.println("Enter meal time: ");
                String slot_ID = sc.nextLine();
                System.out.println("Enter photo link: ");
                String photo = sc.nextLine();
                System.out.println("Enter a price: ");
                float price = Float.parseFloat(sc.nextLine());
                String id = men.getId();
                Menu menUp = new Menu(id, name, vegetarian, type, description, slot_ID, photo, price);
                menServ.update(menUp);
                System.out.println("Updated " + name);
                break;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Enter a number within range");
            } catch (NumberFormatException e) {
                System.out.println("Enter a number");
            }
        }
    }
    
    public static void addItemTypeScreen() {
        System.out.println("Add a item type");
        Scanner sc = new Scanner(System.in);
        System.out.println("\nEnter item type id: ");
        String id = sc.nextLine();
        System.out.println("\nEnter item type: ");
        String type = sc.nextLine();
        ItemType it = new ItemType(id, type);
        ItemTypeService its = new ItemTypeService(con);
        if (its.add(it)) {
            System.out.println("Added item type");
        } else {
            System.out.println("Couldn't add item type");
        }
    }
    
    public static void deleteItemTypeScreen() {
        System.out.println("List of item types");
        ItemTypeService its = new ItemTypeService(con);
        ArrayList<ItemType> types = its.getAll();
        int count = 1;
        for (ItemType t : types) {
            System.out.println(count + ". " + t.getItem_type());
            count++;
        }
        System.out.println("Select item type you'd like to delete");
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                int num = Integer.parseInt(sc.nextLine());
                String id = types.get(num - 1).getItem_type_id();
                if (its.deleteById(id)) {
                    System.out.println("Item type deleted");
                } else {
                    System.out.println("Couldn't delete item type");
                }
                break;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Enter a number within range");
            } catch (NumberFormatException e) {
                System.out.println("Enter a number");
            }
        }
    }
    
    public static void alterItemTypeScreen() {
        System.out.println("List of item types");
        ItemTypeService its = new ItemTypeService(con);
        ArrayList<ItemType> types = its.getAll();
        int count = 1;
        for (ItemType t : types) {
            System.out.println(count + ". " + t.getItem_type());
            count++;
        }
        System.out.println("Select item types you'd like to alter");
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                int num = Integer.parseInt(sc.nextLine());
                String id = types.get(num - 1).getItem_type_id();
                System.out.println("\nEnter item type: ");
                String status = sc.nextLine();
                ItemType it = new ItemType(id, status);
                if (its.update(it)) {
                    System.out.println("Item type updated");
                } else {
                    System.out.println("Couldn't update item type");
                }
                break;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Enter a number within range");
            } catch (NumberFormatException e) {
                System.out.println("Enter a number");
            }
        }
    }
    
    public static void addLocationScreen() {
        System.out.println("Add a location");
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter location id: ");
        String locationId = sc.nextLine();
        System.out.println("Enter the user id: ");
        String userId = sc.nextLine();
        System.out.println("Enter the tax rate: ");
        Float tax = null;
        while (true) {  
            try {
                tax = Float.parseFloat(sc.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Enter a number");
            }
        }
        System.out.println("Enter the street address: ");
        String street = sc.nextLine();
        System.out.println("Enter the city: ");
        String city = sc.nextLine();
        System.out.println("Enter the state: ");
        String state = sc.nextLine();
        System.out.println("Enter the country: ");
        String country = sc.nextLine();
        System.out.println("Enter the zip code: ");
        String zip = sc.nextLine();
        Location loc = new Location(locationId, userId, tax, street, city, country, state, zip);
        LocationService ls = new LocationService(con);
        if (ls.add(loc)) {
            System.out.println("Location added");
        } else {
            System.out.println("Couldn't add location");
        }
    }
    
    public static void deleteLocationScreen() {
        System.out.println("List of locations");
        LocationService ls = new LocationService(con);
        ArrayList<Location> locations = ls.getAll();
        int count = 1;
        System.out.println(locations.size());
        for (Location l : locations) {
            System.out.println(count + ". " + l.getStreet() + ", " + l.getCity());
            count++;
        }
        System.out.println("Select location you'd like to delete");
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                int num = Integer.parseInt(sc.nextLine());
                String id = locations.get(num - 1).getLocationId();
                ls.deleteById(id);
                System.out.println("Item type deleted");
                break;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Enter a number within range");
            } catch (NumberFormatException e) {
                System.out.println("Enter a number");
            }
        }
    }
    
    public static void alterLocationScreen() {
        System.out.println("List of locations");
        LocationService ls = new LocationService(con);
        ArrayList<Location> locations = ls.getAll();
        int count = 1;
        for (Location l : locations) {
            System.out.println(count + ". " + l.getStreet() + ", " + l.getCity());
            count++;
        }
        Scanner sc = new Scanner(System.in);
        String locationId = null;
        while (true) {
            try {
                int input = Integer.parseInt(sc.nextLine());
                locationId = locations.get(input - 1).getLocationId();
                break;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Enter a number within range");
            } catch (NumberFormatException e) {
                System.out.println("Enter a number");
            }
        }
        System.out.println("Enter the user id: ");
        String userId = sc.nextLine();
        System.out.println("Enter the tax rate: ");
        Float tax = null;
        while (true) {
            try {
                tax = Float.parseFloat(sc.nextLine());
                break;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Enter a number within range");
            } catch (NumberFormatException e) {
                System.out.println("Enter a number");
            }
        }
        System.out.println("Enter the street address: ");
        String street = sc.nextLine();
        System.out.println("Enter the city: ");
        String city = sc.nextLine();
        System.out.println("Enter the state: ");
        String state = sc.nextLine();
        System.out.println("Enter the country: ");
        String country = sc.nextLine();
        System.out.println("Enter the zip code: ");
        String zip = sc.nextLine();
        Location loc = new Location(locationId, userId, tax, street, city, country, state, zip);
        ls.update(loc);
        System.out.println("Location updated");
    }

    public static void addUserScreen() {
        System.out.println("Add a User");
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter user id: ");
        String userId = sc.next();
        System.out.println("Enter first name: ");
        String firstName = sc.next();
        System.out.println("Enter last name: ");
        String lastName = sc.next();
        System.out.println("Enter email: ");
        String email = sc.next();
        System.out.println("Enter password: ");
        String password = sc.next();
        System.out.println("Enter status id: ");
        String userStatusId = sc.next();
        System.out.println("Enter location id: ");
        String locationId = sc.next();
        User u = new User(userId, firstName, lastName, email, password, userStatusId, locationId);
        UserService us = new UserService(con);
        us.add(u);
    }

    public static void deleteUserScreen() {
        System.out.println("List of users");
        UserService us = new UserService(con);
        ArrayList<User> uArr = us.getAll();
        int count = 1;
        for (User u : uArr) {
            System.out.println(count + " " + u.getFirstName() + " " + u.getLastName());
            count++;
        }

        System.out.println("Select user you'd like to delete");
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        us.deleteById(uArr.get(input - 1).getUserId());
        System.out.println(uArr.get(input - 1).getFirstName() + "has been deleted");
    }
}
