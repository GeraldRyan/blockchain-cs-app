package privblock.gerald.ryan.service;

import privblock.gerald.ryan.dao.TransactionDao;
import privblock.gerald.ryan.entity.Transaction;
import privblock.gerald.ryan.entity.TransactionPool;

public class TransactionService {
	TransactionDao transactionD = new TransactionDao();

	/**
	 * Gets a transaction from the local database by transaction ID
	 * 
	 * @param uuid
	 * @return
	 */
	public Transaction getTransactionService(String uuid) {
		return transactionD.getTransaction(uuid);
	}

	/**
	 * Adds a transaction to the database
	 * 
	 * @param t
	 * @return
	 */
	public Transaction addTransactionService(Transaction t) {
		return transactionD.addTransaction(t);
	}

	public Transaction updateTransactionService(Transaction transaction) {
		return transactionD.updateTransaction(transaction);
	}

	public Transaction removeTransactionService(String UUID) {
		return transactionD.removeTransaction(UUID);
	}

	/**
	 * Gets entire list of transactions as TransactionPool type from database
	 * 
	 * @return
	 */
	public TransactionPool getAllTransactionsAsTransactionPoolService() {
		return transactionD.getAllTransactionsAsTransactionPool();
	}
}
