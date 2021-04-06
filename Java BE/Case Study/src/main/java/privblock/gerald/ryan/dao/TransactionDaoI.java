package privblock.gerald.ryan.dao;

import java.util.List;

import privblock.gerald.ryan.entity.Block;
import privblock.gerald.ryan.entity.Transaction;
import privblock.gerald.ryan.entity.TransactionPool;

public interface TransactionDaoI {

	public Transaction getTransaction(String uuid);

	public Transaction addTransaction(Transaction t);

	public Transaction updateTransaction(Transaction transaction);

	public Transaction removeTransaction(String UUID);

	public TransactionPool getAllTransactionsAsTransactionPool();

}
