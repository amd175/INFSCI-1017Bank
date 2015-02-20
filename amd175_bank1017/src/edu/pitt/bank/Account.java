package edu.pitt.bank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import edu.pitt.utilities.DbUtilities;
import edu.pitt.utilities.MySqlUtilities;
import edu.pitt.utilities.ErrorLogger;

/**
 * 
 * @author Antoinette Doyle
 *
 */
public class Account {
	private String accountID;
	private String type;
	private double balance;
	private double interestRate;
	private double penalty;
	private String status;
	private Date dateOpen;
	private ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
	private ArrayList<Customer> accountOwners = new ArrayList<Customer>();

	/**
	 * Constructor runs query that returns all the information in the customer table associated with the given ID and create a new Date object for the dateOpen() attribute
	 * Updates attributes based on results of the query
	 * Creates transaction objects for the account
	 * @param accountID
	 */
	public Account(String accountID){
		String sql = "SELECT * FROM account WHERE accountID = '" + accountID + "';";
		DbUtilities db = new MySqlUtilities();

		try {
			ResultSet rs = db.getResultSet(sql);
			while(rs.next()){
				this.accountID = rs.getString("accountID");
				this.type = rs.getString("type");
				this.balance = rs.getDouble("balance");
				this.interestRate = rs.getDouble("interestRate");
				this.penalty = rs.getDouble("penalty");
				this.status = rs.getString("status");
				this.dateOpen = new Date();

			}
		} catch (SQLException e) {
			ErrorLogger.log("SQL Exception");
			ErrorLogger.log("" + e.getStackTrace());
			e.printStackTrace();
		}

		String sqlTransaction = "SELECT * FROM transaction ";
		sqlTransaction += "WHERE accountID = '" + accountID + "';";
		ResultSet transaction;
		try {
			transaction = db.getResultSet(sqlTransaction);
			if(transaction != null){
				while(transaction.next()){
					createTransaction(transaction.getString("transactionID"));
				}
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

	/**
	 * Updates the attributes type and balance based on parameters, generates random accountID, sets default values for rest of attributes
	 * Runs query to insert the attributes as a new entry in the account table 
	 * @param accountType
	 * @param initialBalance
	 */
	public Account(String accountType, double initialBalance){
		this.accountID = UUID.randomUUID().toString();
		this.type = accountType;
		this.balance = initialBalance;
		this.interestRate = 0;
		this.penalty = 0;
		this.status = "active";
		this.dateOpen = new Date();

		String sql = "INSERT INTO account ";
		sql += "(accountID,type,balance,interestRate,penalty,status,dateOpen) ";
		sql += " VALUES ";
		sql += "('" + this.accountID + "', ";
		sql += "'" + this.type + "', ";
		sql += this.balance + ", ";
		sql += this.interestRate + ", ";
		sql += this.penalty + ", ";
		sql += "'" + this.status + "', ";
		sql += "CURDATE());";

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


	/**
	 * Withdraws given amount from the account
	 * @param amount
	 */
	public void withdraw(double amount){
		this.balance -= amount;
		createTransaction(this.accountID, this.type, amount, this.balance);
		updateDatabaseAccountBalance();
	}

	/**
	 * Deposits given amount in the account
	 * @param amount
	 */
	public void deposit(double amount){
		this.balance += amount;
		createTransaction(this.accountID, this.type, amount, this.balance);
		updateDatabaseAccountBalance();
	}

	/**
	 * Runs an update query to update the balance of an account
	 */
	private void updateDatabaseAccountBalance(){
		String sql = "UPDATE account SET balance = " + this.balance + " ";
		sql += "WHERE accountID = '" + this.accountID + "';";

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

	/**
	 * Create a new transaction for an account based on just the transactionID
	 * @param transactionID
	 * @return Transaction object
	 */
	private Transaction createTransaction(String transactionID){
		Transaction t = new Transaction(transactionID);
		transactionList.add(t);
		return t;
	}

	/**
	 * Create a new Transaction object from the given parameters
	 * @param accountID
	 * @param type
	 * @param amount
	 * @param balance
	 * @return
	 */
	private Transaction createTransaction(String accountID, String type, double amount, double balance){
		Transaction t = new Transaction(accountID, type, amount, balance);
		transactionList.add(t);
		return t;
	}

	/**
	 * Adds given account owner to the accountOwner attribute
	 * @param accountOwner
	 */
	public void addAccountOwner(Customer accountOwner){
		this.accountOwners.add(accountOwner);
	}

	public String getAccountID(){
		return this.accountID;
	}

	public double getBalance(){
		return this.balance;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public double getPenalty() {
		return penalty;
	}

	public void setPenalty(double penalty) {
		this.penalty = penalty;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ArrayList<Transaction> getTransactionList() {
		return transactionList;
	}

	public void setTransactionList(ArrayList<Transaction> transactionList) {
		this.transactionList = transactionList;
	}

	public ArrayList<Customer> getAccountOwners() {
		return accountOwners;
	}

	public void setAccountOwners(ArrayList<Customer> accountOwners) {
		this.accountOwners = accountOwners;
	}

	public Date getDateOpen() {
		return dateOpen;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String toString(){
		return this.accountID;
	}


}
