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
 * @author Antoinette Doyle
 *
 */
public class Customer {
	private String customerID;
	private String firstName;
	private String lastName;
	private String ssn;
	private String streetAddress;
	private String city;
	private String state;
	private int zip;
	private String loginName;
	private int pin;

	/**
	 * Constructor runs query that returns all the information in the customer table associated with the given ID
	 * Updates attributes based on results of the query
	 * @param customerID
	 */
	public Customer(String customerID){
		String sql = "SELECT * FROM customer "; 
		sql += "WHERE customerID = '" + customerID + "'"; 

		DbUtilities db = new MySqlUtilities();
		try { 
			ResultSet rs = db.getResultSet(sql);
			while(rs.next()){
				this.customerID = customerID;
				this.firstName = rs.getString("firstName");
				this.lastName = rs.getString("lastName");
				this.ssn = rs.getString("ssn");
				this.streetAddress = rs.getString("streetAddress");
				this.city = rs.getString("city");
				this.state = rs.getString("state");
				this.zip = rs.getInt("zip");
				this.loginName = rs.getString("loginName");
				this.pin = rs.getInt("pin");
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
	 * Constructor updates attributes based on the parameters, then runs a query to insert the data into the transaction table
	 * @param lastName
	 * @param firstName
	 * @param ssn
	 * @param loginName
	 * @param pin
	 * @param streetAddress
	 * @param city
	 * @param state
	 * @param zip
	 */

	public Customer(String lastName, String firstName, String ssn, String loginName, int pin, String streetAddress, String city, String state, int zip){
		this.customerID = UUID.randomUUID().toString();
		this.lastName = lastName;
		this.firstName = firstName;
		this.ssn = ssn;
		this.loginName = loginName;
		this.pin = pin;
		this.streetAddress = streetAddress;
		this.city = city;
		this.state = state;
		//this.zip = zip;

		String sql = "INSERT INTO customer ";
		sql += "(customerID, lastName, firstName, ssn, loginName, pin) ";
		sql += " VALUES ";
		sql += "('" + this.customerID + "', ";
		sql += "'" + this.lastName + "', ";
		sql += "'" + this.firstName + ", ";
		sql += "'" + this.ssn + "', ";
		sql += "'" + this.loginName + "', ";
		sql += pin + ");";

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

	/**
	 * Runs a query to get all of the accounts associated with the customer, returns them in an ArrayList
	 * @param customerID
	 * @return
	 */
	public ArrayList<Account> customerAccount(){
		ArrayList<Account> custAccount = new ArrayList<Account>();
		String sql = "SELECT fk_accountID FROM customer_account WHERE fk_customerID = '" + this.customerID + "';";
		DbUtilities db = new MySqlUtilities();

		try {
			ResultSet rs = db.getResultSet(sql);
			while(rs.next()){
				Account acc = new Account(rs.getString("fk_accountID"));
				custAccount.add(acc);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			ErrorLogger.log("SQL Exception");
			ErrorLogger.log("" + e.getStackTrace());
			e.printStackTrace();
		}

		//System.out.println(sql);
		try {
			db.closeConnection();
		} catch (SQLException e) {
			ErrorLogger.log("SQL Exception");
			ErrorLogger.log("" + e.getStackTrace());
			e.printStackTrace();
		}
		return custAccount;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getZip() {
		return zip;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	public String getCustomerID() {
		return customerID;
	}

	public String getSsn() {
		return ssn;
	}
}
