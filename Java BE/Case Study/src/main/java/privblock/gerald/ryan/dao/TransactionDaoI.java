package privblock.gerald.ryan.dao;

import java.util.List;

import privblock.gerald.ryan.entity.Block;
import privblock.gerald.ryan.entity.Transaction;
import privblock.gerald.ryan.entity.TransactionPool;

public interface TransactionDaoI {

	public abstract Transaction getTransaction(String uuid);

	public abstract Transaction addTransaction(Transaction t);

	public abstract Transaction updateTransaction(Transaction transaction);

	public abstract Transaction removeTransaction(String UUID);

	public abstract TransactionPool getAllTransactionsAsTransactionPool();

}
