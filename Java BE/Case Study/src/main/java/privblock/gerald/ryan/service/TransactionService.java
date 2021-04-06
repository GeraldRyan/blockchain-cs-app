package privblock.gerald.ryan.service;

import privblock.gerald.ryan.dao.TransactionDao;
import privblock.gerald.ryan.entity.Transaction;
import privblock.gerald.ryan.entity.TransactionPool;

public class TransactionService {
	TransactionDao transactionD = new TransactionDao();

	public Transaction getTransactionService(String uuid) {
		// TODO Auto-generated method stub
		return transactionD.getTransaction(uuid);
	}

	public Transaction findTransactionByUUIDService(String UUID) {
		// TODO Auto-generated method stub
		return transactionD.findTransactionByUUID(UUID);
	}

	public Transaction updateTransactionService(Transaction transaction) {
		// TODO Auto-generated method stub
		return transactionD.updateTransaction(transaction);
	}

	public Transaction removeTransactionService(String UUID) {
		// TODO Auto-generated method stub
		return transactionD.removeTransaction(UUID);
	}

	public TransactionPool getAllTransactionsAsTransactionPoolService() {
		// TODO Auto-generated method stub
		return transactionD.getAllTransactionsAsTransactionPool();
	}
}
