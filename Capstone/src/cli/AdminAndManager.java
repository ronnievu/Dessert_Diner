package cli;

import static cli.Tiger.firstScreen;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


import domain.Card;
import domain.Location;
import domain.Menu;
import domain.Order;
import domain.Store;
import domain.User;
import domain.UserStatus;
import java.sql.CallableStatement;

import services.CardService;
import services.DeliveryMethod;
import services.DeliveryMethodService;
import services.DeliveryStatus;
import services.DeliveryStatusService;
import services.LocationService;
import services.MenuServices;
import services.OrderService;
import services.StoreService;
import services.UserService;
import services.UserStatusService;

public class AdminAndManager {
	
	static Connection con;
        //CallableStatement orclCallableStatement;

	
	public AdminAndManager(Connection con){
		AdminAndManager.con = con;
	}
	
	public void adminScreen(){
		ArrayList<String> options = new ArrayList<String>();
		System.out.println("Admin View");
		options.add("Alter Cards");
		//options.add("Alter Combos");
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
	    switch(input){
	    	case 1:
	    		{
	    			option = optionsScreen("Card");
	    			switch(option){
	    				case 1:
	    					alterCardScreen(); //aria fixed-works
	    				case 2:
	    					addCardScreen(); //works
	    				case 3:
	    					deleteCardScreen(); //works
	    				case 4: 
	    					adminScreen();
	    			}
	    			break;
	    		}
	    	case 2:
	    		option = optionsScreen("Delivery Method");
                        switch(option){
                        case 1:
                                alterDeliveryMethod(); //works not storing though? bug
                                break;
                        case 2: addDeliveryMethod(); //works 
                                break;
                        case 3: deleteDeliveryMethod(); //works
                                break;
                        case 4: 
                                adminScreen();
                            
                        }
                            
                        
	    	case 3:
	    		option = optionsScreen("Delivery Status");
                        switch(option){
                            case 1:
                                   alterDeliveryStatus(); //works
                                   break;
                            case 2: 
                                   addDeliveryStatus(); //works
                                   break;
                            case 3:
                                   deleteDeliveryStatus(); //works
                                   break;
                            case 4:
                                   adminScreen();
                        
                        }
                        
                        
                        
	    	case 4:
	    	{
	    		option = optionsScreen("Item");
    			switch(option){
    				case 1:
    					alterItemScreen(); //works 
    					break;
    				case 2:
    					addItemScreen(); //works
    					break;
    				case 3:
    					deleteItemScreen(); //works
    					break;
    				case 4:
    					adminScreen(); 
    			}
    			break;
	    	}
	    	case 5: {
              
              //THERE IS NO "ItemTypesService" class OR "ItemTypes" CLASS 
	    		option = optionsScreen("Item Type");
                        switch(option){
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
    					adminScreen();
                        }
                         break;
                }
	    	case 6:{
	    		option = optionsScreen("Location");
                        switch(option){
                            case 1:
                                    alterLocationScreen(); //bug 
                                    break;
                            case 2:
                                    addLocationScreen(); //works
                                    break;
                            case 3: 
                                    deleteLocationScreen(); //works
                                    break;
                            case 4:
                                    adminScreen();
                        }
                        break;
                }
	    	case 7: {
	    		option = optionsScreen("Order");
                        switch(option){
                            case 1:
                                   alterOrderScreen(); //works
                                   break;
                            case 2:
                                   addOrderScreen(); //bug
                                   break;
                            case 3:
                                   deleteOrderScreen(); //works
                                   break;
                            case 4:
                                   adminScreen();
                                
                        }
                        
                        break;
                }
                        
	    	case 8: {
                    //not finished
                    option  =	optionsScreen("Order Item");
                    switch(option){
                        case 1:
                                alterOrderItemScreen();
                                break;
                        case 2:
                                addOrderItemScreen();
                                break;
                        case 3:
                                deleteOrderItemScreen();
                                
                    }
                }
                break;
	    	case 9:
	    	{
	    		option = optionsScreen("User");
	    		switch(option){
	    			case 1:
	    				alterUserScreen(); //working not storing changes? bug
                                        break;
	    			case 2:
	    				addUserScreen(); //works
	    			case 3:
	    				deleteUserScreen(); //works
	    		}
	    	break;		
	    	}
	    	case 10:{
	    		option = optionsScreen("User Statuse");
                        switch(option){
                            case 1:
                                    alterUserStatusScreen(); //index out of bound error
                                    break;
                            case 2:
                                    addUserStatusScreen(); //wont store - bug
                                    break;
                            case 3:
                                   deleteUserStatusScreen(); //havent tested deleting 
                                   break;
                            case 4:
                                adminScreen();
                        }
                        break;
                }  
	    	case 11: {
                        firstScreen(); //works 
                }
                break;
	    	case 12:
	    		System.exit(0);
	    }
	    
	 //   adminScreen();
	    
	}
	
	
	public static int optionsScreen(String thing){
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


	public void addCardScreen(){
		System.out.println("Add a Credit Card");
		Scanner sc = new Scanner(System.in);
		System.out.println("\nEnter Card id: ");
	    String cardId= sc.next();
	    System.out.println("\nEnter id of user this card belongs to: ");
		String userId= sc.next();
		System.out.println("\nEnter Card number: ");
		String cardNumber= sc.next();
		System.out.println("\nEnter expiration year: ");
		int year = sc.nextInt();
		System.out.println("\nEnter expiration month: ");
		int month = sc.nextInt();
		System.out.println("\nEnter expiration date: ");
		int day = sc.nextInt();
		Date expiryDate= new Date(year, month, day);
		System.out.println("Enter Security code: ");
		String securityCode= sc.next();
                        
                Card c = new Card(cardId, userId, cardNumber, expiryDate, securityCode);
		                	

	       CardService cs = new CardService(con);
	       cs.add(c);
               
                AdminAndManager aam = new AdminAndManager(con);
		aam.adminScreen();
	}

	public static void deleteCardScreen(){
		System.out.println("List of cards");
		CardService cs = new CardService(con);
		ArrayList<Card> cl = cs.getAll();
		int count=1;
		for(Card c:cl){
			System.out.println(count + ": " + c.getCardNumber());
			count++;
		}
		System.out.println("Select card you'd like to delete");
		Scanner sc = new Scanner(System.in);
	    int input = sc.nextInt();
	    cs.deleteById(cl.get(input-1).getCardId());
	    System.out.println("Deleted Card");
		
	}
	
	public static void alterCardScreen(){
		System.out.println("List of cards");
		CardService cs = new CardService(con);
		ArrayList<Card> cl = cs.getAll();
		int count=1;
		for(Card c:cl){
			System.out.println(count + ": " + c.getCardNumber());
			count++;
		}
		System.out.println("Enter the number of the card you'd like to alter");
		Scanner sc = new Scanner(System.in);
	    int input = sc.nextInt();
	    
	    String cardId= cl.get(input-1).getCardId();
	    System.out.println("Enter id of user this card belongs to: ");
		String userId= sc.next();
		System.out.println("Enter Card number: ");
		String cardNumber= sc.next();
		System.out.println("Enter expiration year: ");
		int year = sc.nextInt();
		System.out.println("Enter expiration month: ");
		int month = sc.nextInt();
		
                
                System.out.println("Enter expiration date: ");
		int day = sc.nextInt();
		Date expiryDate= new Date(year, month, day);
		System.out.println("Enter Security code: ");
		String securityCode= sc.next();
                
                //stops working here
		Card c = new Card(cardId, userId, cardNumber, expiryDate, securityCode);
		
		cs.update(c);
		AdminAndManager aam = new AdminAndManager(con);
		aam.adminScreen();
	}
	
     
        
        
        public static void alterDeliveryMethod(){
            
            System.out.println("List of delivery methods");
                DeliveryMethodService ms = new DeliveryMethodService(con);
		ArrayList<DeliveryMethod> dm = ms.getAll();
	
		int count=1;
		for(DeliveryMethod c:dm){
			System.out.println(count + ": " + c.getDelivery_method());
			count++;
		}
		System.out.println("Enter the id of the method you would like to alter");
		Scanner sc = new Scanner(System.in);
	    int input = sc.nextInt();
	    
	    String cardId= dm.get(input-1).getDelivery_method_id();
	    
		System.out.println("Enter the new delivery method Id: ");
		String delivery_method_id= sc.next();
                System.out.println("entered: " + delivery_method_id);
		System.out.println("Enter the new delivery method name");
		String delivery_method  = sc.next();                
                //stops working here
                DeliveryMethod m = new DeliveryMethod(delivery_method_id, delivery_method);
		DeliveryMethodService dms = new DeliveryMethodService(con);
                dms.update(m);
                //ms.update(m);
                System.out.println("altered delivery method");
		AdminAndManager aam = new AdminAndManager(con);
		aam.adminScreen();
         
        }
           
        public static void addDeliveryMethod(){
            System.out.println("Add a delivery method");
            Scanner sc = new Scanner(System.in);
            
            System.out.println("\n Enter a new delivery method id: ");
            String delivery_method_id = sc.next();
            System.out.println("\n Enter a new delivery method:");
            String delivery_method = sc.next();
            
            DeliveryMethod addnew = new DeliveryMethod(delivery_method_id, delivery_method);
            
            DeliveryMethodService delse = new DeliveryMethodService(con);
            delse.add(addnew);
            System.out.println("new delivery method added");
          
            AdminAndManager aam = new AdminAndManager(con);
            aam.adminScreen();
            
          
            
            
        }
        
        
        public static void deleteDeliveryMethod(){
            System.out.println("Please choose which delivery method you would like to delete:");
            DeliveryMethodService ms = new DeliveryMethodService(con);
            ArrayList<DeliveryMethod> dm = ms.getAll();
	    int count=1;
       	    for(DeliveryMethod c:dm){
            System.out.println(count + ": " + c.getDelivery_method());
        	count++;
		} 
            System.out.println("Enter the id of the method you would like to delete");
		Scanner sc = new Scanner(System.in);
	    int input = sc.nextInt();
	    
            
             ms.deleteById(dm.get(input-1).getDelivery_method_id());
	    System.out.println("Deleted Card");
	  
            
        }
        
        
        
        //not working skipping over it?
        public static void alterDeliveryStatus(){
            
            System.out.println("List of Delivery Statuses");
                DeliveryStatusService dss = new DeliveryStatusService(con);
		ArrayList<DeliveryStatus> dl = dss.getAll();
		int count=1;
		for(DeliveryStatus d:dl){
			System.out.println(count + ": " + d.getDelivery_status_id());
			count++;
		}
        //        int delivery_status_id= dl.get(input-1).getDelivery_status();
		System.out.println("Enter the number of the delivery status youd like to alter");
		Scanner sc = new Scanner(System.in);
                int input = sc.nextInt();
                 System.out.println("Enter the new delivery status id");
                 String delivery_status_id = sc.next();
                 System.out.println("enter the altered delivery status");
                 String delivery_status = sc.next();
                 DeliveryStatus d = new DeliveryStatus(delivery_status_id,delivery_status);
                 

	      DeliveryStatusService d2 = new DeliveryStatusService(con);
	       d2.update(d);
               
                AdminAndManager aam = new AdminAndManager(con);
		aam.adminScreen();
                 
                 
                 
	    
	 
            
        }
        
        public static void addDeliveryStatus(){
            System.out.println("Add a delivery status");
            Scanner sc = new Scanner(System.in);
            System.out.println("\n Enter delivery status id: ");
            String delivery_status_id = sc.next();
            System.out.println("\n Enter delivery status: ");
            String delivery_status = sc.next();
            
            DeliveryStatus nds = new DeliveryStatus(delivery_status_id, delivery_status);
            DeliveryStatusService delstat = new DeliveryStatusService(con);
            delstat.add(nds);
            
            AdminAndManager aam = new AdminAndManager(con);
            aam.adminScreen();
            
        }
        
        public static void deleteDeliveryStatus(){
            System.out.println("List of Delivery Statuses");
            DeliveryStatusService ds = new DeliveryStatusService(con);
            ArrayList<DeliveryStatus> dl = ds.getAll();
            int count =1;
            for(DeliveryStatus d:dl){
                System.out.println(count + ": " + d.getDelivery_status()+ "delivery status id: "+ d.getDelivery_status_id());
                count++;
            }
            System.out.println("Select the delivery Status id you'd like to delete");
            Scanner sc = new Scanner(System.in);
            int input = sc.nextInt();
            ds.deleteByID(dl.get(input-1).getDelivery_status_id());
            System.out.println("Delivery Status deleted");
            AdminAndManager aam = new AdminAndManager(con);
            aam.adminScreen();
            
            
        }
        
        
        
	public static void addItemScreen(){
            System.out.println("Add an item");
	    Scanner sc = new Scanner(System.in);
	    System.out.println("\nEnter item id: ");
	    String id= sc.next();
	    System.out.println("\nEnter item name: ");
	    sc.nextLine();
		String name= sc.nextLine();
		System.out.println("\nEnter vegeterian (y or n): ");
		String vege = sc.next();
		char vegetarian = vege.charAt(0);
		System.out.println("\nEnter a description: ");
		sc.nextLine();
		String description= sc.nextLine();
		System.out.println("\nEnter type number id: ");
		String type= sc.next();
		System.out.println("\nEnter meal time: ");
		String slot_ID= sc.next();
		System.out.println("\nEnter photo link: ");
		String photo= sc.next();
		System.out.println("\nEnter a price: ");
		float price= sc.nextFloat();
		
		Menu men = new Menu(id, name, vegetarian, type, description, slot_ID, photo, price);
		MenuServices menServ = new MenuServices(con);
		menServ.add(men);
		System.out.println("\n" + name + " added to database\n");
                AdminAndManager aam = new AdminAndManager(con);
		aam.adminScreen();
		
	}
	
	public static void deleteItemScreen(){
		System.out.println("Choose an item to delete");
		MenuServices ms = new MenuServices(con);
		ArrayList<Menu> menus = ms.getAll();
		ServiceWrapper.printMenuItems(menus);
		Scanner sc = new Scanner(System.in);
	    int input = sc.nextInt();
	    if(input == menus.size() + 1)
	    	return;
	    if(input == menus.size()+2)
	    	System.exit(0);
	    MenuServices menServ = new MenuServices(con);
	    
	    menServ.deleteById(menus.get(input-1).getId());
	    System.out.println("Deleted " + menus.get(input-1).getName());
	}
	
	public static void alterItemScreen(){
		System.out.println("Choose an item to alter");
		MenuServices ms = new MenuServices(con);
		ArrayList<Menu> menus = ms.getAll();
		ServiceWrapper.printMenuItems(menus);
		Scanner sc = new Scanner(System.in);
	    int input = sc.nextInt();
	    Menu men = menus.get(input-1);
	    MenuServices menServ = new MenuServices(con);
	    System.out.println("Enter item name: ");
	    sc.nextLine();
		String name= sc.nextLine();
		System.out.println("Enter vegeterian (y or n): ");
		String vege = sc.next();
		char vegetarian = vege.charAt(0);
		System.out.println("Enter a description: ");
		sc.nextLine();
		String description= sc.nextLine();
		System.out.println("Enter type number id: ");
		String type= sc.next();
		System.out.println("Enter meal time: ");
		String slot_ID= sc.next();
		System.out.println("Enter photo link: ");
		String photo= sc.next();
		System.out.println("Enter a price: ");
		float price= sc.nextFloat();
		String id = men.getId();
		Menu menUp = new Menu(id, name, vegetarian, type, description, slot_ID, photo, price);
	    menServ.update(menUp);
	    System.out.println("Updated " + name);
	}
        
        
        public static void alterUserScreen(){
            
            System.out.println("List of users");
		UserService cs = new UserService(con);
		ArrayList<User> cl = cs.getAll();
		int count=1;
		for(User c:cl){
			System.out.println(count + ": " + c.getUserId());
			count++;
		}
		System.out.println("Enter the number of the user youd like to alter");
		Scanner sc = new Scanner(System.in);
	    int input = sc.nextInt();
	    
	    String UserId= cl.get(input-1).getUserId();
            System.out.println("Eneter the new user id:");
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
                us.update(u);
		AdminAndManager aam = new AdminAndManager(con);
		aam.adminScreen();
                        
            
        }
        
        
        
        

	public static void addUserScreen(){
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
		
		AdminAndManager aam = new AdminAndManager(con);
		aam.adminScreen();
	}
	
	public static void deleteUserScreen() {
		System.out.println("List of users");
		UserService us = new UserService(con);
		ArrayList<User> uArr = us.getAll();
		int count=1;
		for(User u:uArr){
			System.out.println(count + " " + u.getFirstName() + " " + u.getLastName());
			count++;
		}
		
		System.out.println("Select user you'd like to delete");
		Scanner sc = new Scanner(System.in);
	    int input = sc.nextInt();
	    us.deleteById(uArr.get(input-1).getUserId());
	    System.out.println(uArr.get(input-1).getFirstName() + "has been deleted");
		
	}
        
        
         public static void alterItemTypeScreen(){
             System.out.println("Choose an item type to alter");
		MenuServices ms = new MenuServices(con);
		ArrayList<Menu> menus = ms.getAll();
		ServiceWrapper.printMenuItems(menus);
		Scanner sc = new Scanner(System.in);
	    int input = sc.nextInt();
	    Menu men = menus.get(input-1);
	    MenuServices menServ = new MenuServices(con);
	    System.out.println("Enter item name: ");
	    sc.nextLine();
		String name= sc.nextLine();
		System.out.println("Enter vegeterian (y or n): ");
		String vege = sc.next();
		char vegetarian = vege.charAt(0);
		System.out.println("Enter a description: ");
		sc.nextLine();
		String description= sc.nextLine();
		System.out.println("Enter new type number id: ");
		String type= sc.next();
		System.out.println("Enter meal time: ");
		String slot_ID= sc.next();
		System.out.println("Enter photo link: ");
		String photo= sc.next();
		System.out.println("Enter a price: ");
		float price= sc.nextFloat();
		String id = men.getId();
		Menu menUp = new Menu(id, name, vegetarian, type, description, slot_ID, photo, price);
	    menServ.update(menUp);
	    System.out.println("Updated item type " + name);
     
         }
        public static void addItemTypeScreen(){
            
            System.out.println("Add an item type");
	    Scanner sc = new Scanner(System.in);
	    System.out.println("\nEnter item type id: ");
	    String id= sc.next();
	    System.out.println("\nEnter item name: ");
	    sc.nextLine();
		String name= sc.nextLine();
		System.out.println("\nEnter vegeterian (y or n): ");
		String vege = sc.next();
		char vegetarian = vege.charAt(0);
		System.out.println("\nEnter a description: ");
		sc.nextLine();
		String description= sc.nextLine();
		System.out.println("\nEnter type number id: ");
		String type= sc.next();
		System.out.println("\nEnter meal time: ");
		String slot_ID= sc.next();
		System.out.println("\nEnter photo link: ");
		String photo= sc.next();
		System.out.println("\nEnter a price: ");
		float price= sc.nextFloat();
		
		Menu men = new Menu(id, name, vegetarian, type, description, slot_ID, photo, price);
		MenuServices menServ = new MenuServices(con);
		menServ.add(men);
		System.out.println("\n" + name + " added to database\n");
            
            
        }
        
        public static void deleteItemTypeScreen(){
            
              System.out.println("List of Items");
             MenuServices ls = new MenuServices(con);
             ArrayList<Menu> ll = ls.getAll();
             int count=1;
             for(Menu l:ll){
                 System.out.println(count + ": "+ l.getId());
                 count++;
             }
             System.out.println("Select the Menu id item youd like to delete");
             Scanner sc = new Scanner(System.in);
             int input = sc.nextInt();
             ls.deleteById(ll.get(input-1).getId());
             System.out.println("Deleted item");
            
            
            
        }
         
         public static void alterLocationScreen(){
             
              System.out.println("List of Locations");
                LocationService ms = new LocationService(con);
		ArrayList<Location> dm = ms.getAll();
	
		int count=1;
		for(Location c:dm){
			System.out.println(count + ": " + c.getLocationId());
			count++;
		}
		System.out.println("Enter the id of the Location you would like to alter");
		Scanner sc = new Scanner(System.in);
	       int input = sc.nextInt();
	    
	    String locationId= dm.get(input-1).getLocationId();
             System.out.println("\n Enter new Location id: ");
             String nlocationId = sc.next();
             System.out.println("\nEnter new user id: ");
             String userID = sc.next();
             System.out.println("\n Enter new taxt rate: ");
             double taxrate = sc.nextDouble();
             System.out.println("\nEnter new street: ");
             String street = sc.next();
             System.out.println("\nEnter new city: ");
             String city = sc.next();
             System.out.println("\nEnter new country: ");
             String country = sc.next();
             System.out.println("\nEnter new state: ");
             String state = sc.next();
             System.out.println("\nEnter new zip code: ");
             String zip = sc.next();
             
             Location nl = new Location(nlocationId,userID,taxrate,street,city,country,state,zip);
             LocationService lss = new LocationService(con);
             lss.update(nl);
	    
	     
             
             
             
             
         }
         
         public static void addLocationScreen(){
             
             System.out.println("Add a location");
             Scanner sc = new Scanner(System.in);
             System.out.println("\n Enter Location id: ");
             String locationId = sc.next();
             System.out.println("\nEnter user id: ");
             String userID = sc.next();
             System.out.println("\n Enter taxt rate: ");
             double taxrate = sc.nextDouble();
             System.out.println("\nEnter street: ");
             String street = sc.next();
             System.out.println("\nEnter city: ");
             String city = sc.next();
             System.out.println("\nEnter country: ");
             String country = sc.next();
             System.out.println("\nEnter state: ");
             String state = sc.next();
             System.out.println("\nEnter zip code: ");
             String zip = sc.next();
             
             Location nl = new Location(locationId,userID,taxrate,street,city,country,state,zip);
             LocationService lss = new LocationService(con);
             lss.add(nl);
             
         
             
             
             
         }
         
         public static void deleteLocationScreen(){
             System.out.println("List of locations");
             LocationService ls = new LocationService(con);
             ArrayList<Location> ll = ls.getAll();
             int count=1;
             for(Location l:ll){
                 System.out.println(count + ": "+ l.getLocationId());
                 count++;
             }
             System.out.println("Select the location youd like to delete");
             Scanner sc = new Scanner(System.in);
             int input = sc.nextInt();
             ls.deleteById(ll.get(input-1).getLocationId());
             System.out.println("Delete location");
             
             
         }
         
         
         public static void alterUserStatusScreen(){
             
              System.out.println("List of User Statuses");
               UserStatusService ms = new UserStatusService(con);
		ArrayList<UserStatus> dm = ms.getAll();
	
		int count=1;
		for(UserStatus c:dm){
			System.out.println(count + ": " + c.getUserStatusId());
			count++;
		}
		System.out.println("Enter the id of the Order you would like to alter");
		Scanner sc = new Scanner(System.in);
	    int input = sc.nextInt();
	    
	    String userStatusId= dm.get(input-1).getUserStatusId();
	  
             System.out.println("\nEnter a new user status Id: ");
         //    String userStatusId = sc.next();
             System.out.println("\nEnter a new user status: ");
             String userStatus = sc.next();
	    
	     
            
             System.out.println("user updated!");
                AdminAndManager aam = new AdminAndManager(con);
		aam.adminScreen();
             
             
             
             
             
             
         }
         
         public static void addUserStatusScreen(){
             System.out.println("Add a User Status");
             Scanner sc = new Scanner(System.in);
             System.out.println("\nEnter a new user status Id: ");
             String userStatusId = sc.next();
             System.out.println("\nEnter a new user status: ");
             String userStatus = sc.next();
             
      //    UserStatus uss = new UserStatus(userStatusId,userStatus);
        //  UserStatusService ss = new UserStatusService(con);
      //    ss.add(uss);
             
             
           
             
             
         }
         
         public static void deleteUserStatusScreen(){
             
             System.out.println("List of user statuses");
		UserStatusService us = new UserStatusService(con);
		ArrayList<UserStatus> uArr = us.getAll();
		int count=1;
		for(UserStatus u:uArr){
			System.out.println(count + " " + u.getUserStatusId() + " " + u.getUserStatus());
			count++;
		}
		
		System.out.println("Select user status id you'd like to delete");
		Scanner sc = new Scanner(System.in);
	    int input = sc.nextInt();
	    us.deleteById(uArr.get(input-1).getUserStatusId());
	    System.out.println(uArr.get(input-1).getUserStatusId() + "has been deleted");
             
             
         }
         
         
         public static void alterOrderScreen(){
            
           System.out.println("List of Orders");
                OrderService ms = new OrderService(con);
		ArrayList<Order> dm = ms.getAll();
	
		int count=1;
		for(Order c:dm){
			System.out.println(count + ": " + c.getOrder_id());
			count++;
		}
		System.out.println("Enter the id of the Order you would like to alter");
		Scanner sc = new Scanner(System.in);
	    int input = sc.nextInt();
	    
	    String order_id= dm.get(input-1).getOrder_id();
	    
	    
	     System.out.println("\nEnter the new id of user this card belongs to: ");
             String user_id= sc.next();
	     System.out.println("\nEnter the new tip: ");
             float tip = sc.nextFloat();
	     System.out.println("\nEnter the new total price: ");
	     float total_price = sc.nextFloat();
	     System.out.println("\nEnter the new placed time stamp: ");
             int placed_stamp = sc.nextInt();
	     System.out.println("\nEnter the new delivery time stamp: ");
	     int delivery_stamp = sc.nextInt();
             System.out.println("\nEnter the new card id: ");
             String card_id = sc.next();
             System.out.println("\n Enter the new Instructions: ");
             String instructions = sc.next();
             System.out.println("\n Enter the new delivery method: ");
             String delivery_method = sc.next();
             System.out.println("\n Enter the new store id ");
             String store_id = sc.next();
             System.out.println("\n Enter the new delivery status id");
             String delivery_status_id = sc.next();
             
             Order ord = new Order(order_id,user_id,tip,total_price,placed_stamp,delivery_stamp,card_id,instructions,delivery_method,store_id,delivery_status_id);
             OrderService os = new OrderService(con);
              os.update(ord);
             System.out.println("Order updated!");
                AdminAndManager aam = new AdminAndManager(con);
		aam.adminScreen();
             
             
             
             
             
             
         }
         
         
         public static void addOrderScreen(){
          
             System.out.println("Add an order");
             Scanner sc = new Scanner(System.in);
	     System.out.println("\nEnter Order id: ");
	     String order_id= sc.next();
	     System.out.println("\nEnter id of user this card belongs to: ");
             String user_id= sc.next();
	     System.out.println("\nEnter tip: ");
             float tip = sc.nextFloat();
	     System.out.println("\nEnter total price: ");
	     float total_price = sc.nextFloat();
	     System.out.println("\nEnter placed time stamp: ");
             int placed_stamp = sc.nextInt();
	     System.out.println("\nEnter delivery time stamp: ");
	     int delivery_stamp = sc.nextInt();
             System.out.println("\nEnter card id: ");
             String card_id = sc.next();
             System.out.println("\n Enter Instructions: ");
             String instructions = sc.next();
             System.out.println("\n Enter delivery method: ");
             String delivery_method = sc.next();
             System.out.println("\n Enter store id ");
             String store_id = sc.next();
             System.out.println("\n Enter delivery status id");
             String delivery_status_id = sc.next();
             Order ord = new Order(order_id,user_id,tip,total_price,placed_stamp,delivery_stamp,card_id,instructions,delivery_method,store_id,delivery_status_id);
             OrderService os = new OrderService(con);
             os.add(ord);
             System.out.println("Order added!");
                AdminAndManager aam = new AdminAndManager(con);
		aam.adminScreen();
             
             
             
             
             
         }
         
         public static void deleteOrderScreen(){
              System.out.println("List of Orders");
             OrderService ls = new OrderService(con);
             ArrayList<Order> ll = ls.getAll();
             int count=1;
             for(Order l:ll){
                 System.out.println(count + ": "+ l.getOrder_id());
                 count++;
             }
             System.out.println("Select the order youd like to delete");
             Scanner sc = new Scanner(System.in);
             int input = sc.nextInt();
             ls.deleteById(ll.get(input-1).getOrder_id());
             System.out.println("Delete Order!");
             
             
             
         }
         
         
         public void alterOrderItemScreen(){
           //necessary?  
         }
         
         public void addOrderItemScreen(){
             Scanner sc = new Scanner(System.in);
             System.out.println("Enter the order id youd like to add and item to");
             String order_id = sc.next();
             System.out.println("Enter the item id youd like to add");
             String item_id = sc.next();
             OrderService os = new OrderService(con);
             os.addItem_id(item_id, order_id);
         }
         
         public void deleteOrderItemScreen(){
             
             System.out.println("List of Orders");
             OrderService ls = new OrderService(con);
             ArrayList<Order> ll = ls.getAll();
             int count=1;
             for(Order l:ll){
                 System.out.println(count + ": "+ l.getOrder_id());
                 count++;
             }
             System.out.println("Select the order youd like to delete");
             Scanner sc = new Scanner(System.in);
             int input = sc.nextInt();
             ls.deleteById(ll.get(input-1).getOrder_id());
             System.out.println("Delete Order!");
             
             
         }
         
         
         
         
}
