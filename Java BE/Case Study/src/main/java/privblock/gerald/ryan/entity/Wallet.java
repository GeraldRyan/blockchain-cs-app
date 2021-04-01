package privblock.gerald.ryan.entity;

import java.util.UUID;

import javax.persistence.Entity;

/**
 * An individual wallet for a miner. 
 * Keeps track of miner's balance. 
 * Allows miner to authorize Transactions.
 * @author User
 *
 */
@Entity
public class Wallet {
	double balance;
	String privatekey;
	String publickey;
	String address;
	
	
	public Wallet() {
		
	}
	
	public Wallet(double balance, String privatekey, String publickey, String address) {
		super();
		this.balance = balance;
		this.privatekey = privatekey;
		this.publickey = publickey;
		this.address = address;
	}
	
	public static void main(String[] args) {
		UUID uuid = new UUID(1, 1);
		System.out.println(uuid);
	}
	
	
}
