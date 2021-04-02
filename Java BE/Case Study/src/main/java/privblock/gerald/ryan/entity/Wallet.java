package privblock.gerald.ryan.entity;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import java.util.Base64;
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

	public static Wallet createWallet()
			throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
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

	/**
	 * Generate a signature based on data using local private key
	 * 
	 * @param data
	 * @throws NoSuchProviderException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 */
	public byte[] sign(byte[] data)
			throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException {
		Signature sig = Signature.getInstance("SHA256withECDSA", "SunEC");
		sig.initSign(privatekey);
		sig.update(data);
		byte[] signatureBytes = sig.sign();
		System.out.println("Data has been successfully signed");
		return signatureBytes;
	}

	public static boolean verifySignature(byte[] signatureBytes, byte[] data, PublicKey publickey)
			throws SignatureException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
		Signature sig = Signature.getInstance("SHA256withECDSA", "SunEC");
		sig.initVerify(publickey);
		sig.update(data);
		return sig.verify(signatureBytes);
	}
	
	public static boolean verifySignature(byte[] signatureBytes, String[] data, PublicKey publickey)
			throws SignatureException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
		Signature sig = Signature.getInstance("SHA256withECDSA", "SunEC");
		sig.initVerify(publickey);
		sig.update(data);
		return sig.verify(signatureBytes);

	}

	public static void testSign() throws NoSuchAlgorithmException, NoSuchProviderException,
			InvalidAlgorithmParameterException, UnsupportedEncodingException, SignatureException, InvalidKeyException {

		Wallet wallet1 = Wallet.createWallet();
		System.out.println(wallet1);
		System.out.println("_________________-");
		byte[] signatureBytes = wallet1.sign("CATSMEOW".getBytes("UTF-8"));
		System.out.println("Signature:" + new org.apache.commons.codec.binary.Base64().encodeToString(signatureBytes));
		System.out.println("Was it signed properly? Expect true. Drumroll... -> "
				+ wallet1.verifySignature(signatureBytes, "CATSMEOW".getBytes("UTF-8"), wallet1.getPublickey()));
	}

	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException,
			InvalidAlgorithmParameterException, InvalidKeyException, UnsupportedEncodingException, SignatureException {
		Wallet.testSign();
	}

	public static void mainOff(String[] args)
			throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
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

	public static void setSTARTING_BALANCE(double STARTING_BALANCE) {
		STARTING_BALANCE = STARTING_BALANCE;
	}

	public PublicKey getPublickey() {
		return publickey;
	}

}
