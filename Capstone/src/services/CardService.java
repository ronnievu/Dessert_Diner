package services;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import domain.Card;

public class CardService implements Service<Card>{
	
	Connection connection;
	
	public CardService() {
		super();
	}
	public CardService(Connection connection) {
		super();
		this.connection = connection;
	}
	//asd
	public boolean add(Card card){
		try{
			String cardId = card.getCardId();
			String userId = card.getUserId();
			String cardNumber = card.getCardNumber();
			Date expiryDate = card.getExpiryDate();
			String securityCode = card.getSecurityCode();
			
			CallableStatement oCSF = connection.prepareCall("INSERT INTO cards VALUES(?, ?, ?, ?, ?)");
			oCSF.setString(1, cardId);
			oCSF.setString(2, userId);
			oCSF.setString(3, cardNumber);
			oCSF.setDate(4, expiryDate);
			oCSF.setString(5, securityCode);
			oCSF.execute();
			oCSF.close();
			return true;
		}catch(SQLException e){
			System.out.println(e.getMessage());
			return false;
		}	
	}
	public void deleteById(String id){
		try{
			Statement cardsSt = connection.createStatement();
			cardsSt.executeQuery("Delete from cards where card_id = "+id);
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}
	public ArrayList<Card> getAll(){

		ArrayList<Card> cards = new ArrayList<Card>();
		
		try{
			Statement cardsSt = connection.createStatement();
			ResultSet cardsRs = cardsSt.executeQuery("Select * from Cards");
			
			while(cardsRs.next()){
				Card card = new Card(
						cardsRs.getString(1),
						cardsRs.getString(2),
						cardsRs.getString(3),
						cardsRs.getDate(4),
						cardsRs.getString(5)
						); 
				cards.add(card);
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return cards;
	}
	public Card getById(String id){
		Card card = null;
		
		try{
			Statement cardsSt = connection.createStatement();
			ResultSet cardsRs = cardsSt.executeQuery("Select * from Cards where card_id = " + id);
			
			cardsRs.next();
			card = new Card(
					cardsRs.getString(1),
					cardsRs.getString(2),
					cardsRs.getString(3),
					cardsRs.getDate(4),
					cardsRs.getString(5)
					); 
		}catch(Exception e){
			System.out.println(e.getMessage());
		}	
		
		return card;
	}

	public void update(Card card){
		try{
			String cardId = card.getCardId();
			String userId = card.getUserId();
			String cardNumber = card.getCardNumber();
			Date expiryDate = card.getExpiryDate();
			String securityCode = card.getSecurityCode();
			
			CallableStatement oCSF = connection.prepareCall(
                                "UPDATE cards " +
                                "SET user_id = ?, card_number = ?, expiry_date = ?, security_code = ? " + 
                                "WHERE card_id = ?");
			oCSF.setString(1, userId);
			oCSF.setString(2, cardNumber);
			oCSF.setDate(3, expiryDate);
			oCSF.setString(4, securityCode);
                        oCSF.setString(5, cardId);
			oCSF.execute();
			oCSF.close();
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}	
	}
	
	public ArrayList<Card> getUserCards(String userId){

		ArrayList<Card> cards = new ArrayList<Card>();
		
		try{
			Statement cardsSt = connection.createStatement();
			ResultSet cardsRs = cardsSt.executeQuery("Select * from Cards where user_id = '" + userId + "'");
			
			while(cardsRs.next()){
				Card card = new Card(
						cardsRs.getString(1),
						cardsRs.getString(2),
						cardsRs.getString(3),
						cardsRs.getDate(4),
						cardsRs.getString(5)
						); 
				cards.add(card);
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return cards;
	}


}
