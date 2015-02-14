package edu.pitt.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

import edu.pitt.bank.Account;
import edu.pitt.bank.Customer;
import edu.pitt.bank.Security;
import edu.pitt.bank.Transaction;
import edu.pitt.utilities.DbUtilities;
import edu.pitt.utilities.ErrorLogger;
import edu.pitt.utilities.MySqlUtilities;

import javax.swing.AbstractListModel;

import java.awt.Color;
import java.awt.List;
import java.awt.ScrollPane;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TransactionUI {

	private JFrame frmTransactionList;
	private JScrollPane transactionPane;
	private JTable tblTransactions;
	private Account acct;

	/**
	 * Create the application.
	 */
	public TransactionUI(Account acct) {
		this.acct = acct;
		initialize();
		frmTransactionList.setVisible(true);

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTransactionList = new JFrame();
		frmTransactionList.setTitle("Bank 1017 Account Transactions");
		frmTransactionList.setAlwaysOnTop(true);
		frmTransactionList.setBounds(100, 100, 450, 300);
		frmTransactionList.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		transactionPane = new JScrollPane();
		frmTransactionList.getContentPane().add(transactionPane);

		Vector<Vector<Object>> transactions = new Vector<Vector<Object>>();

		for(Transaction t : acct.getTransactionList()){
			Vector<Object> row = new Vector<Object>();
			row.add(t.getType());
			row.add(t.getTransactionDate());
			row.add(t.getAmount());

			transactions.add(row);
		}

		String[] cols = {"Transaction Type", "Date/Time", "Amount"};
		Vector<String> columnNames = new Vector<String>();
		for(int column = 0; column < cols.length; column++){
			columnNames.add(cols[column]);
		}

		DefaultTableModel transactionList = new DefaultTableModel(transactions, columnNames);
		tblTransactions = new JTable(transactionList);
		tblTransactions.setFillsViewportHeight(true);
		tblTransactions.setShowGrid(true);
		tblTransactions.setGridColor(Color.black);
		transactionPane.getViewport().add(tblTransactions);


	}
}
