package privblock.gerald.ryan.dao;

import java.util.List;

import privblock.gerald.ryan.entity.Block;
import privblock.gerald.ryan.entity.Transaction;
import privblock.gerald.ryan.entity.TransactionPool;

public abstract class TransactionDaoI {

	public Transaction addTransaction(Transaction T) {
		return null;
	}

	public abstract Transaction getTransaction(String uuid);

	public abstract Transaction findTransactionByUUID(String UUID);

	public abstract Transaction updateTransaction(Transaction transaction);

	public abstract Transaction removeTransaction(String UUID);

	public abstract TransactionPool getAllTransactionsAsTransactionPool();

}
