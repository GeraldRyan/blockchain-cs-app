package privblock.gerald.ryan.entity;

import javax.persistence.Embeddable;

@Embeddable
public class WalletForDB {

	byte[] publickey;
	byte[] privatekey;
	double balance;
	String address;

	public WalletForDB(Wallet wallet) {
		super();
		this.publickey = wallet.getPublickey().getEncoded();
		this.privatekey = wallet.getPrivatekey().getEncoded();
		this.address = wallet.getAddress();
		this.balance = wallet.getBalance();
	}

	public WalletForDB() {

	}

}
