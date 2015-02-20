package edu.pitt.bank;

import java.sql.ResultSet;
import java.sql.SQLException;

import edu.pitt.utilities.MySqlUtilities;
import edu.pitt.utilities.ErrorLogger;

public class Test {
	/**
	 * Used to test methods in the project
	 * @author Antoinette
	 *
	 */

	public static void main(String[] args) {
		//Bank b = new Bank();
		//System.out.println(b.getCustomerList());
		//System.out.println(b.getAccountList());
		
		//Account acc = new Account("00ae9c2a-5d43-11e3-94ef-97beef767f1d");
		//System.out.println(acc.getTransactionList());
		
		Security s = new Security();
		Customer c = s.validateLogin("nmarcus", 8125);
		c.customerAccount();
		
		
		
		

	}

}
