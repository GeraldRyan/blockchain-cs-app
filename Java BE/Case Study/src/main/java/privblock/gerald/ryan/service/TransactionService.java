package privblock.gerald.ryan.service;

import privblock.gerald.ryan.dao.TransactionDao;
import privblock.gerald.ryan.entity.Transaction;
import privblock.gerald.ryan.entity.TransactionPool;

public class TransactionService {
	TransactionDao transactionD = new TransactionDao();

	public Transaction getTransactionService(String uuid) {
		return transactionD.getTransaction(uuid);
	}

	public Transaction findTransactionByUUIDService(Transaction t) {
		return transactionD.addTransaction(t);
	}

	public Transaction updateTransactionService(Transaction transaction) {
		return transactionD.updateTransaction(transaction);
	}

	public Transaction removeTransactionService(String UUID) {
		return transactionD.removeTransaction(UUID);
	}

	public TransactionPool getAllTransactionsAsTransactionPoolService() {
		return transactionD.getAllTransactionsAsTransactionPool();
	}
}
