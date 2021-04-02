package privblock.gerald.ryan.entity;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.util.Date;
import java.util.HashMap;

import javax.persistence.Entity;
import javax.persistence.Id;

import exceptions.TransactionAmountExceedsBalance;
import privblock.gerald.ryan.utilities.StringUtils;

/**
 * Document an exchange of currency from a sender to one or more recipients
 * 
 * @author User
 *
 */
@Entity
public class Transaction {

//	long id;
	@Id
	String uuid; // could have also gone with ye olde timestamp
	Wallet senderWallet;
	String recipientAddress;
	double amount;
	/**
	 * Data structure about who the recipients are in the transaction and how much
	 * currency they are receiving and how much goes back to sender (change). Kind
	 * of like a simple receipt/ledger.
	 */
	HashMap<String, Object> output; // like basic receipt
	/**
	 * Meta info about transaction including timestamp, address, publickey of sender
	 * and signature, used to determine validity. Depends on output above
	 */
	HashMap<String, Object> input; // like wire transfer document

	public Transaction(Wallet senderWallet, String recipientAddress, double amount)
			throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, IOException {
		super();
		this.uuid = StringUtils.getUUID8();
		try {
			this.output = Transaction.createOutput(senderWallet, recipientAddress, amount);
		} catch (TransactionAmountExceedsBalance e) {
			e.printStackTrace();
			System.out.println("No new transaction created. See stack trace above");
			System.out.println("Returning to main program");
			return;
		}
		try {
			this.input = Transaction.createInput(senderWallet, this.output);
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.senderWallet = senderWallet;
		this.recipientAddress = recipientAddress;
		this.amount = amount;
		System.out.println("Valid New Transaction created");
	}

	public Transaction() {
	}

	/**
	 * Structures output data of wallet - a hashmap of two items, currency going to
	 * recipient at address and change going back to sender
	 * 
	 * @param senderWallet
	 * @param recipientAddress
	 * @param amount
	 * @return
	 * @throws TransactionAmountExceedsBalance
	 */
	public static HashMap<String, Object> createOutput(Wallet senderWallet, String recipientAddress, double amount)
			throws TransactionAmountExceedsBalance {
		if (amount > senderWallet.getBalance()) {
			System.out.println("Amount exceeds balance");
			throw new TransactionAmountExceedsBalance("The transaction amount exceeds the current balance");
		}
		HashMap<String, Object> output = new HashMap<String, Object>();
		output.put(recipientAddress, amount);
		output.put(senderWallet.getAddress(), (senderWallet.getBalance() - amount));
		return output;
	}

	/**
	 * Structured meta data about transaction, including digitial binding signature
	 * of transaction from sender Includes senders public key for verification
	 * 
	 * @param senderWallet
	 * @param output
	 * @return
	 * @throws IOException
	 * @throws SignatureException
	 * @throws NoSuchProviderException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 */
	public static HashMap<String, Object> createInput(Wallet senderWallet, HashMap<String, Object> output)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, SignatureException,
			IOException {
		HashMap<String, Object> input = new HashMap<String, Object>();
		input.put("timestamp", new Date().getTime());
		input.put("amount", senderWallet.getBalance());
		input.put("address", senderWallet.getAddress());
		input.put("publicKey", senderWallet.getPublickey());
		// sign off on the transactions, by digitally signing the transactions.
		input.put("signature", senderWallet.sign(output));
		return input;
	}

	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException,
			InvalidAlgorithmParameterException, InvalidKeyException, IOException {
		Wallet senderWallet = Wallet.createWallet();
		System.out.println(senderWallet.getBalance());
		Transaction t1 = new Transaction(senderWallet, "recipientWalletAddress1920", 15);
		System.out.println(t1.toString());
//		Transaction t2 = new Transaction(senderWallet, "sdfsdf", 1000);
	}

	@Override
	public String toString() {
		return "Transaction [uuid=" + uuid + ", senderWallet=" + senderWallet + ", recipientAddress=" + recipientAddress
				+ ", amount=" + amount + ", output=" + output + ", input=" + input + "]";
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void setSenderWallet(Wallet senderWallet) {
		this.senderWallet = senderWallet;
	}

	public void setRecipientAddress(String recipientAddress) {
		this.recipientAddress = recipientAddress;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setOutput(HashMap<String, Object> output) {
		this.output = output;
	}

	public void setInput(HashMap<String, Object> input) {
		this.input = input;
	}

//	// output
//	String addressTo;

	// as bundles

}
