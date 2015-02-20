package edu.pitt.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import edu.pitt.bank.Customer;
import edu.pitt.bank.Security;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class LoginUI {

	private JFrame frmBankLogin;
	private JTextField txtLoginName;
	private JTextField txtPassword;
	private JLabel lblLoginName;
	private JButton btnExit;
	private JButton btnLogin;
	private JLabel lblPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginUI window = new LoginUI();
					window.frmBankLogin.setVisible(true);
						
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBankLogin = new JFrame();
		frmBankLogin.setTitle("Bank1017 Login");
		frmBankLogin.setBounds(100, 100, 450, 218);
		frmBankLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBankLogin.getContentPane().setLayout(null);
		
		lblLoginName = new JLabel("Login Name:");
		lblLoginName.setBounds(35, 26, 78, 28);
		frmBankLogin.getContentPane().add(lblLoginName);
		
		lblPassword = new JLabel("Password:");
		lblPassword.setBounds(45, 71, 66, 20);
		frmBankLogin.getContentPane().add(lblPassword);
		
		txtLoginName = new JTextField();
		txtLoginName.setBounds(123, 30, 267, 20);
		frmBankLogin.getContentPane().add(txtLoginName);
		txtLoginName.setColumns(10);
		
		txtPassword = new JTextField();
		txtPassword.setBounds(123, 71, 267, 20);
		frmBankLogin.getContentPane().add(txtPassword);
		txtPassword.setColumns(10);
		
		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String loginName = txtLoginName.getText();
				String pinString = txtPassword.getText();
				boolean checkPin = true;
				boolean checkFields = true;

				if(loginName.equalsIgnoreCase("") || pinString.equalsIgnoreCase("")){ //check to make sure that the user has actually entered username and password
					checkFields = false;
					JOptionPane.showMessageDialog(null, "One or more fields is empty.  Please enter both your username and your pin.");
				}

				if(checkFields){
					for(int i = 0; i < pinString.length(); i++){ 
						if(pinString.charAt(i) < '0' || pinString.charAt(i) > '9'){ 
							checkPin = false;
						}
					}
				}

				if(checkFields){
					if(checkPin){
						int pin = Integer.parseInt(txtPassword.getText());
						Security s = new Security();
						Customer c = s.validateLogin(loginName, pin);
						if(c != null){
							AccountDetailsUI ad = new AccountDetailsUI(c);
							frmBankLogin.setVisible(false);
						}
						else{
							JOptionPane.showMessageDialog(null, "Invalid login.");
						}
					}
					else{
						JOptionPane.showMessageDialog(null, "Please enter a numeric pin");
					}
				}
			}

		});
		
		btnLogin.setBounds(238, 115, 66, 23);
		frmBankLogin.getContentPane().add(btnLogin);
		
		btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0); //StackOverflow: http://stackoverflow.com/questions/8632705/how-to-close-a-gui-when-i-push-a-jbutton				
			}
		});
		btnExit.setBounds(313, 115, 77, 23);
		frmBankLogin.getContentPane().add(btnExit);
	}
}
