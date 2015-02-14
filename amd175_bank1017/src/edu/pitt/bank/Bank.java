package edu.pitt.bank;

import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.pitt.utilities.DbUtilities;
import edu.pitt.utilities.MySqlUtilities;
import edu.pitt.utilities.ErrorLogger;

public class Bank {
	private ArrayList<Account> accountList = new ArrayList<Account>();
	private ArrayList<Customer> customerList = new ArrayList<Customer>();
	
	public Bank(){
		loadAccounts();
		setAccountOwners();
	}
	
	private void loadAccounts(){
		String sql = "SELECT * FROM account;";
		DbUtilities db = new MySqlUtilities();
		
		try {
			ResultSet rs = db.getResultSet(sql);
			while(rs.next()){
				Account acc = new Account(rs.getString("accountID"));
				this.accountList.add(acc);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			ErrorLogger.log("SQL Exception");
			ErrorLogger.log("" + e.getStackTrace());
			e.printStackTrace();
		}
		try {
			db.closeConnection();
		} catch (SQLException e) {
			ErrorLogger.log("SQL Exception");
			ErrorLogger.log("" + e.getStackTrace());
			e.printStackTrace();
		}

	}
	
	public Account findAccount(String accountID){
		String sql = "SELECT accountID FROM account;";
		DbUtilities db = new MySqlUtilities();
		Account acc = null;
		try {
			ResultSet rs = db.getResultSet(sql);
			while(rs.next()){
				if(accountID == rs.getString("accountID")){
					acc = new Account(rs.getString("accountID"));
				}
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			ErrorLogger.log("SQL Exception");
			ErrorLogger.log("" + e.getStackTrace());
			e.printStackTrace();
		}
		try {
			db.closeConnection();
		} catch (SQLException e) {
			ErrorLogger.log("SQL Exception");
			ErrorLogger.log("" + e.getStackTrace());
			e.printStackTrace();
		}
		return acc;
	}
	
	
	public Customer findCustomer(String customerID){
		Customer match = null;
		for(int i = 0; i < customerList.size(); i++){
			if(customerList.get(i).getCustomerID() == customerID){
				match = customerList.get(i);
			}
		}
		
		return match;
	}
	
	private void setAccountOwners(){
		String sql = "SELECT * FROM account ";
		sql += "JOIN customer_account ON accountID = fk_accountID ";
		sql += "JOIN customer ON fk_customerID = customerID;";
		System.out.println(sql);
		
		DbUtilities db = new MySqlUtilities();
		
		try {
			ResultSet rs = db.getResultSet(sql);
			while(rs.next()){
				Customer newCust = new Customer(rs.getString("customerID"));
				customerList.add(newCust);
				Account acc = new Account(rs.getString("accountID"));
				acc.addAccountOwner(newCust);
				
			}
		} catch (SQLException e) {
			ErrorLogger.log("SQL Exception");
			ErrorLogger.log("" + e.getStackTrace());
			e.printStackTrace();
		}
		
		try {
			db.closeConnection();
		} catch (SQLException e) {
			ErrorLogger.log("SQL Exception");
			ErrorLogger.log("" + e.getStackTrace());
			e.printStackTrace();
		}
	}

	public ArrayList<Account> getAccountList() {
		return accountList;
	}

	public ArrayList<Customer> getCustomerList() {
		return customerList;
	}

}
