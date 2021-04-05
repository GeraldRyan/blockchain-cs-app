/**
 * 
 */
package privblock.gerald.ryan.entity;

import java.util.HashMap;

/**
 * @author Gerald Ryan
 *
 */
public class TransactionPool {

	HashMap<String, Object> transactionMap = new HashMap<String, Object>();

	public TransactionPool() {

	}

	/**
	 * Set a transaction in this pool
	 * 
	 * @param transaction
	 * @return
	 */
	public Transaction setTransaction(Transaction transaction) {
		transactionMap.put(transaction.getUuid(), transaction);
		return transaction;
	}

	public void syncTransactionPool() {

	}

	public void broadcastTransactionPool() {

	}

	public void TransactionPool() {

	}

	public HashMap<String, Object> getTransactionMap() {
		return transactionMap;
	}



}
