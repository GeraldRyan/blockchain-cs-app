package privblock.gerald.ryan;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
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
		String md;
		try {
			md = getHash256(s);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		System.out.println(md);
	}

	public static String getHash256(String s) throws NoSuchAlgorithmException {
		MessageDigest md;
		md = MessageDigest.getInstance("SHA-256");
		byte[] b = md.digest(s.getBytes(StandardCharsets.UTF_8));
		BigInteger number = new BigInteger(1, b);
		StringBuilder hexString = new StringBuilder(number.toString(16));
		while (hexString.length() <32) {
			hexString.insert(0, '0');
		}
		return hexString.toString();
	}
}
