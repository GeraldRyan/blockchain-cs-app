package privblock.gerald.ryan;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Date; // gets time in ms. 

/**
 * 
 * @author Gerald Ryan Block Class of blockchain app
 *
 *
 *
 */
public class Block {
	long ts;

	public Block() {
		super();
		this.ts = new Date().getTime(); // timestamp in milliseconds
	}

	public static void main(String[] args) {
		String s = "foo";
		System.out.println(s);
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
			e.printStackTrace();
			return;
		}
		System.out.println(md);
	}

//	
//	getHash256(String s){
//		
//	}
}
