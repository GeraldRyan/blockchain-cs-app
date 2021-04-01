package privblock.gerald.ryan.entity;

import java.util.HashMap;

import javax.persistence.Entity;

@Entity
public class Transaction {
	
	// input
	long timestamp;
	double balance;
	String senderPublicKey;
	String senderAddress;
	
	// output
	double amount;
	String addressTo;
	
	// as bundles
	HashMap<String, Object> input;
	HashMap<String, Object> output;	
	
	
}
