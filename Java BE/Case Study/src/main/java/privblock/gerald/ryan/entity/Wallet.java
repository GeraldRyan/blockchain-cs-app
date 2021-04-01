package privblock.gerald.ryan.entity;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.UUID;

import javax.persistence.Entity;

import privblock.gerald.ryan.utilities.StringUtils;

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
	PrivateKey privatekey;
	PublicKey publickey;
	String address;
	
	static double STARTING_BALANCE = 1000;
	
	public Wallet() {
		
	}
	
	public Wallet(double balance, PrivateKey privatekey, PublicKey publickey, String address) {
		super();
		this.balance = balance;
		this.privatekey = privatekey;
		this.publickey = publickey;
		this.address = address;
	}
	
	public static Wallet createWallet() throws NoSuchAlgorithmException, NoSuchProviderException {
		String address = String.valueOf(UUID.randomUUID()).substring(0,8);
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
		KeyPair keyPair = keyGen.generateKeyPair();
		PrivateKey privateKey = keyPair.getPrivate();
		PublicKey publicKey = keyPair.getPublic();
		Wallet wallet = new Wallet(STARTING_BALANCE, privateKey, publicKey, address);
		System.out.println("NEW WALLET CREATED");
		return wallet;
	}
	
	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public static double getSTARTING_BALANCE() {
		return STARTING_BALANCE;
	}

	public static void setSTARTING_BALANCE(double sTARTING_BALANCE) {
		STARTING_BALANCE = sTARTING_BALANCE;
	}

	public PublicKey getPublickey() {
		return publickey;
	}

	public static void mainOff(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException {
		UUID uuid = UUID.randomUUID();
		String uuidString = String.valueOf(uuid);
		String substring = uuidString.substring(0, 8);
		System.out.println(substring);
		
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
		KeyPair keyPair = keyGen.generateKeyPair();
		PrivateKey privateKey = keyPair.getPrivate();
		PublicKey publicKey = keyPair.getPublic();
		System.out.println("PUBLIC KEY");
		System.out.println(publicKey);
	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException {
		Wallet wallet = Wallet.createWallet();
		System.out.println(wallet.getBalance());
		System.out.println(wallet.getAddress());
		System.out.println(wallet.getPublickey());
		
	}
	
	
}
