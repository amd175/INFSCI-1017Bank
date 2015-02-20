package edu.pitt.bank;

import edu.pitt.utilities.DbUtilities;
import edu.pitt.utilities.MySqlUtilities;
import edu.pitt.utilities.ErrorLogger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Security {
	private String userID;

	/**
	 * Method to validate the login information on the UI by checking the login information against the database
	 * @param loginName
	 * @param pin
	 * @return
	 */
	public Customer validateLogin(String loginName, int pin){
		String sql = "SELECT * FROM customer WHERE loginName = '" + loginName + "' and pin = " + pin + ";";

		DbUtilities db = new MySqlUtilities();
		Customer userFound = null;

		try {
			ResultSet rs = db.getResultSet(sql);
			if(rs.next()){
				userFound = new Customer(rs.getString("customerID")); 
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
		return userFound;
	}

	/**
	 * Returns ArrayList<String> of the permissions of a specific user
	 * @param userID
	 * @return
	 */
	public ArrayList<String> listUserGroups(String userID){
		ArrayList<String> userGroups = new ArrayList<String>();
		String sql = "SELECT groupName FROM groups ";
		sql += "JOIN user_permissions ON user_permissions.groupID = groups.groupID ";
		sql += "AND groupOrUserID = '" + userID + "';";

		DbUtilities db = new MySqlUtilities();
		try {
			ResultSet rs = db.getResultSet(sql);
			while(rs.next()){
				userGroups.add(rs.getString("groupName"));
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
		return userGroups;

	}

}
