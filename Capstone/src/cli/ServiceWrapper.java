package cli;

import static cli.AdminAndManager.con;
import static cli.Tiger.sc;
import java.sql.Connection;
import java.util.ArrayList;


import domain.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Scanner;
import services.CardService;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;


import services.MenuServices;
import services.OrderService;
import services.UserService;

public class ServiceWrapper {
	
	Connection con;
	Location loc;
	
	public ServiceWrapper(Connection con) {
		super();
		this.con = con;

	}
        
        public boolean isEmailTaken(String email) {
            UserService us = new UserService(con);
            User user = us.getByEmail(email);
            return user != null;
        }

	public User login(String email, String password){
		
		UserService us = new UserService(con);
		User candidate = us.getByEmail(email);
		System.out.println(candidate.getFirstName());
		if(password.equals(candidate.getPassword())) return candidate;
		else return null;
	}
	
	public User register(String firstName, String lastName, String phone, String email, String password){
		//, String street, String city, String state, String country, String zip, String userStatus
		boolean result = false;
		String userId = Double.toString(Math.random()* 10001);
		String userStatusId = "1";

		User user = new User(userId,firstName,lastName,phone, email,password,userStatusId);
		UserService us = new UserService(con);
		result =  us.add(user);
		return user;
	}
        
        public Location getloc(){
            return loc;
        }
        
	public static void printOptions(ArrayList<String> options){
		options.add("Go back");
		int count = 0;
		for(String option : options) {
			count++;
			System.out.println(count + ". " + option);
		}

	}
	
	public static void printMenuItems(ArrayList<Menu> menus){
		int count = 0;
		for(Menu menu: menus){
			count++;
			System.out.println(count + ". $" + menu.getPrice() + " " + menu.getName());
		}
		System.out.println(++count + ". Go Back");
	}
        
        public static void printReceipt(ArrayList<Menu> menus, Order order){
            int count = 0;
            BufferedWriter bufferedWriter = null;
            String fileOut = "Mummy's Diner Receipt \n\n";
            try{
                File myfile;
                Writer out;
                
                
                Path currentRelativePath = Paths.get("");
                String s = currentRelativePath.toAbsolutePath().toString();
                String finalPath = s + "\\src\\cli\\receipt.txt";
                //System.out.println("Current relative path is: " + finalPath);
                
                
                myfile = new File(finalPath);
                out = new FileWriter(myfile, false);
                for(Menu menu: menus){
                    count++; 
                    fileOut += count + ". $" + menu.getPrice() + " " + menu.getName()+ "\n";
                }
                
                bufferedWriter = new BufferedWriter(out);
                bufferedWriter.write(fileOut);
                bufferedWriter.write("\n\nOrder Total: $" + order.getTotal_price());
            }catch(IOException io){
                System.out.println("Couldn't open file");
            }finally{
                try{
                    if(bufferedWriter != null) bufferedWriter.close();
                } catch(Exception ex){}
            }
        }
        
	public static void printOrders(ArrayList<Order> orders){
            int count = 0;
            for(Order order: orders){
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
		// TODO Auto-generated method stub
		
		currentOrder.setDelivery_status_id("0");
		OrderService os = new OrderService(con);
		os.add(currentOrder);
		
	}

	public ArrayList<Menu> getMenuItems(ArrayList<String> itemIds) {
		
		MenuServices ms = new MenuServices(con);
		ArrayList<Menu> items = new ArrayList<Menu>();
		
		
		for (String itemId:itemIds){
			items.add(ms.getById(itemId));
		}

		return items;
	}

	public int calculateTotalPrice(ArrayList<String> item_ids) {
		int total = 0;
		ServiceWrapper sw = new ServiceWrapper(con);
		ArrayList<Menu> items = sw.getMenuItems(item_ids);
		for(Menu item: items){
			total += item.getPrice();
		}
		return total;
	}
        
    public void addCreditCard(
            String userId, 
            String cardNumber, 
            String securityCode, 
            Date expiryDate) 
    {
        String cardId = Double.toString(Math.random() * 10001);
        CardService cardService = new CardService(con);
        Card newCard = new Card(
                cardId,
                userId,
                cardNumber,
                expiryDate,
                securityCode);
        cardService.add(newCard);
    }
}
