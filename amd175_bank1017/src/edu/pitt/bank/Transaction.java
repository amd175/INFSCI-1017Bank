package edu.pitt.bank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

import edu.pitt.utilities.DbUtilities;
import edu.pitt.utilities.MySqlUtilities;
import edu.pitt.utilities.ErrorLogger;



/**
 * @author Antoinette Doyle
 *
 */
public class Transaction {

	private String transactionID;
	private String accountID;
	private String type;
	private double amount;
	private double balance;
	private Date transactionDate; 

	/**
	 * Constructor runs query that returns all the information in the transaction table associated with the given ID
	 * Updates attributes based on results of the query
	 * @param transactionID
	 */
	public Transaction(String transactionID){
		String sql = "SELECT * FROM transaction "; 
		sql += "WHERE transactionID = '" + transactionID + "'"; 

		//System.out.println(sql); 

		DbUtilities db = new MySqlUtilities();
		try { 
			ResultSet rs = db.getResultSet(sql);
			while(rs.next()){
				this.transactionID = rs.getString("transactionID"); 
				this.accountID = rs.getString("accountID");
				this.type = rs.getString("type");
				this.amount = rs.getDouble("amount");
				this.balance = rs.getDouble("balance");
				this.transactionDate = new Date();
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

	/**
	 * Constructor updates attributes based on the parameters, then runs a query to insert the data into the transaction table
	 * @param accountID
	 * @param type
	 * @param amount
	 * @param balance
	 */

	public Transaction(String accountID, String type, double amount, double balance){
		this.transactionID = UUID.randomUUID().toString();
		this.type = type;
		this.amount = amount;
		this.accountID = accountID;
		this.balance = balance;

		String sql = "INSERT INTO transaction ";
		sql += "(transactionID, accountID, amount, transactionDate, type, balance) ";
		sql += " VALUES ";
		sql += "('" + this.transactionID + "', ";
		sql += "'" + this.accountID + "', ";
		sql += amount + ", ";
		sql += "CURDATE(),";
		sql += "'" + this.type + "', ";
		sql += balance + ");";

		//System.out.println(sql);

		DbUtilities db = new MySqlUtilities();
		db.executeQuery(sql);
		try {
			db.closeConnection();
		} catch (SQLException e) {
			ErrorLogger.log("SQL Exception");
			ErrorLogger.log("" + e.getStackTrace());
			e.printStackTrace();
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionID() {
		return transactionID;
	}


}
