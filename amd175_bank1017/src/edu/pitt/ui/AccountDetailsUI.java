package edu.pitt.ui;

import java.awt.EventQueue;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JComboBox;










import edu.pitt.bank.Account;
import edu.pitt.bank.Customer;
import edu.pitt.bank.Security;
import edu.pitt.utilities.ErrorLogger;
import edu.pitt.utilities.MySqlUtilities;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Font;

public class AccountDetailsUI {

	private JFrame frmBankAccountDetails;
	private JComboBox cboAccounts;
	private JLabel lblYourAccounts;
	private JLabel lblAccountType;
	private JLabel lblBalance;
	private JLabel lblInterestRate;
	private JLabel lblAmount;
	private JTextField txtAmount;
	private JButton btnDeposit;
	private JButton btnWithdraw;
	private JButton btnShowTransactions;
	private JButton btnExit;
	private JLabel lblPenalty;
	JTextArea txtWelcome;
	private Customer accountOwner;

	/**
	 * Create the application.
	 */
	public AccountDetailsUI(Customer c) {
		accountOwner = c;
		initialize();
		frmBankAccountDetails.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBankAccountDetails = new JFrame();
		frmBankAccountDetails.setTitle("Bank1017 Account Details");
		frmBankAccountDetails.setBounds(100, 100, 483, 331);
		frmBankAccountDetails.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBankAccountDetails.getContentPane().setLayout(null);

		cboAccounts = new JComboBox();
		cboAccounts.setBounds(152, 70, 272, 23);
		ArrayList<Account> custAccount = accountOwner.customerAccount();
		for(int i = 0; i < custAccount.size(); i++){
			cboAccounts.addItem(custAccount.get(i));
		}

		cboAccounts.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				Account acct = (Account) cboAccounts.getSelectedItem();
				String interest = "Interest Rate: " + acct.getInterestRate() + "%";
				String type = "Account Type: " + acct.getType();
				String balance = "Balance: $" + acct.getBalance();
				String penalty = "Penalty: $" + acct.getPenalty();
				lblInterestRate.setText(interest);
				lblAccountType.setText(type);
				lblBalance.setText(balance);
				lblPenalty.setText(penalty);

			}
		});

		frmBankAccountDetails.getContentPane().add(cboAccounts);

		lblYourAccounts = new JLabel("Your Accounts:");
		lblYourAccounts.setBounds(23, 70, 119, 23);
		frmBankAccountDetails.getContentPane().add(lblYourAccounts);

		String type = "Account Type: " + ((Account) cboAccounts.getSelectedItem()).getType();
		lblAccountType = new JLabel(type);
		lblAccountType.setBounds(23, 123, 143, 23);
		frmBankAccountDetails.getContentPane().add(lblAccountType);

		String balance = "Balance: $" + ((Account) cboAccounts.getSelectedItem()).getBalance();
		lblBalance = new JLabel(balance);
		lblBalance.setBounds(23, 144, 200, 13);
		frmBankAccountDetails.getContentPane().add(lblBalance);

		String interest = "Interest Rate: " + ((Account) cboAccounts.getSelectedItem()).getInterestRate() + "%";
		lblInterestRate = new JLabel(interest);
		lblInterestRate.setBounds(23, 159, 200, 13);
		frmBankAccountDetails.getContentPane().add(lblInterestRate);

		lblAmount = new JLabel("Amount:");
		lblAmount.setBounds(243, 141, 61, 18);
		frmBankAccountDetails.getContentPane().add(lblAmount);

		txtAmount = new JTextField();
		txtAmount.setText("20.00");
		txtAmount.setBounds(314, 140, 110, 20);
		frmBankAccountDetails.getContentPane().add(txtAmount);
		txtAmount.setColumns(10);

		btnDeposit = new JButton("Deposit");
		btnDeposit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Double amount;
				String amountString = txtAmount.getText();
				boolean checkAmount = true;
				boolean checkEmpty = true;
				Account acct = ((Account) cboAccounts.getSelectedItem());

				if(amountString.equalsIgnoreCase("")){ 
					checkEmpty = false;
					JOptionPane.showMessageDialog(null, "Please enter an amount.");
				}

				for(int i = 0; i < amountString.length(); i++){ 
					if(amountString.charAt(i) < '0' || amountString.charAt(i) > '9'){
						checkAmount = false;

						if(amountString.charAt(i) == '.'){
							checkAmount = true;
						}
					}
				}
				try{
					if(checkEmpty){
						if(checkAmount){
							amount = Double.parseDouble(amountString);
							acct.deposit(amount);
							String balance = "Balance: $" + acct.getBalance();
							lblBalance.setText(balance);
						}else{
							JOptionPane.showMessageDialog(null, "Please input a numeric value in the amount textbox.");
						}
					}
				}catch(NumberFormatException ex){
					ErrorLogger.log("Number Format Excpetion: the program couldn't parse the given value");
					ErrorLogger.log("" + ex.getStackTrace());
					ex.printStackTrace();

				}catch(NullPointerException ex){
					ErrorLogger.log("Null Pointer Exception: the program tried to use a null value");
					ErrorLogger.log("" + ex.getStackTrace());
					ex.printStackTrace();

				}catch(Exception ex){
					ErrorLogger.log("" + ex.getStackTrace());
					ex.printStackTrace();
				}
			}
		});

		btnDeposit.setBounds(241, 171, 84, 23);
		frmBankAccountDetails.getContentPane().add(btnDeposit);

		btnWithdraw = new JButton("Withdraw");
		btnWithdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Double amount;
				String amountString = txtAmount.getText();
				boolean checkAmount = true;
				boolean checkEmpty = true;
				Account acct = ((Account) cboAccounts.getSelectedItem());
				if(amountString.equalsIgnoreCase("")){ 
					checkEmpty = false;
					JOptionPane.showMessageDialog(null, "Please enter an amount.");
				}

				for(int i = 0; i < amountString.length(); i++){ 
					if(amountString.charAt(i) < '0' || amountString.charAt(i) > '9'){ 
						checkAmount = false;

						if(amountString.charAt(i) == '.'){
							checkAmount = true;
						}
					}
				}

				try{
					if(checkEmpty){
						if(checkAmount){
							amount = Double.parseDouble(amountString);
							acct.withdraw(amount);
							String balance = "Balance: $" + acct.getBalance();
							lblBalance.setText(balance);
						}else{
							JOptionPane.showMessageDialog(null, "Please input a numeric value in the amount textbox.");
						}
					}
				}catch(NumberFormatException ex){
					ErrorLogger.log("Number Format Excpetion: the program couldn't parse the given value");
					ErrorLogger.log("" + ex.getStackTrace());
					ex.printStackTrace();

				}catch(NullPointerException ex){
					ErrorLogger.log("Null Pointer Exception: the program tried to use a null value");
					ErrorLogger.log("" + ex.getStackTrace());
					ex.printStackTrace();

				}catch(Exception ex){
					ErrorLogger.log("" + ex.getStackTrace());
					ex.printStackTrace();
				}
			}
		});

		btnWithdraw.setBounds(335, 171, 89, 23);
		frmBankAccountDetails.getContentPane().add(btnWithdraw);

		btnShowTransactions = new JButton("Show Transactions");
		btnShowTransactions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Account acct = (Account) cboAccounts.getSelectedItem();
				TransactionUI transWindow = new TransactionUI(acct);
			}
		});
		btnShowTransactions.setBounds(167, 247, 158, 23);
		frmBankAccountDetails.getContentPane().add(btnShowTransactions);

		btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setBounds(335, 247, 89, 23);
		frmBankAccountDetails.getContentPane().add(btnExit);

		String penalty = "Penalty: $" + ((Account) cboAccounts.getSelectedItem()).getPenalty();
		lblPenalty = new JLabel(penalty);
		lblPenalty.setBounds(23, 174, 110, 13);
		frmBankAccountDetails.getContentPane().add(lblPenalty);

		txtWelcome = new JTextArea();
		txtWelcome.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtWelcome.setWrapStyleWord(true);
		txtWelcome.setRows(2);
		txtWelcome.setBackground(SystemColor.control);

		String message = accountOwner.getFirstName() + " " + accountOwner.getLastName() + ", ";
		message += "welcome to 1017 bank.  You have the following permissions \r\nin this system: ";
		Security s = new Security();
		ArrayList<String> permissions = s.listUserGroups(accountOwner.getCustomerID());
		for(int i = 0; i < permissions.size()-1; i++){
			message += permissions.get(i) + ", ";
		}
		message += permissions.get(permissions.size()-1);
		txtWelcome.setText(message);
		txtWelcome.setForeground(Color.BLACK);
		txtWelcome.setEditable(false);
		txtWelcome.setBounds(10, 11, 447, 50);
		frmBankAccountDetails.getContentPane().add(txtWelcome);

	}
}
