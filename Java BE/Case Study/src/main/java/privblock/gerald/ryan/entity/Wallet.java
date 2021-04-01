package privblock.gerald.ryan.entity;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;

import privblock.gerald.ryan.utilities.StringUtils;

/**
 * An individual wallet for a miner. Keeps track of miner's balance. Allows
 * miner to authorize Transactions.
 * 
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

	public static Wallet createWallet() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
		String address = String.valueOf(UUID.randomUUID()).substring(0, 8);
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC", "SunEC");
		keyGen.initialize(new ECGenParameterSpec("secp256k1"));
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

	@Override
	public String toString() {
		return "Wallet [balance=" + balance + ", privatekey=" + privatekey + ", publickey=" + publickey + ", address="
				+ address + "]";
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

	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
		UUID uuid = UUID.randomUUID();
		String uuidString = String.valueOf(uuid);
		String substring = uuidString.substring(0, 8);
		System.out.println(substring);

		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC", "SunEC");
		ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256k1");
//		secp256k1
//		[secp256k1,1.3.132.0.10]
		keyGen.initialize(ecSpec);

		KeyPair keyPair = keyGen.generateKeyPair();
		PrivateKey privateKey = keyPair.getPrivate();
		PublicKey publicKey = keyPair.getPublic();
		System.out.println("PUBLIC KEY");
		System.out.println(publicKey.toString());
		Wallet wallet1 = Wallet.createWallet();
		System.out.println(wallet1);
	}

	public static void mainOff(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
		Wallet wallet = Wallet.createWallet();
		System.out.println(wallet.getBalance());
		System.out.println(wallet.getAddress());
		System.out.println(wallet.getPublickey());
		Set<String> algorithms = Security.getAlgorithms("Signature");
//		Security.get
		algorithms.forEach(a -> System.out.println(a.toString()));
		System.out.println("------------------");

		System.out.println(Security.getProviders("AlgorithmParameters.EC")[0].getService("AlgorithmParameters", "EC")
				.getAttribute("SupportedCurves"));
	}
}
