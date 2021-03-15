package privblock.gerald.ryan.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptoHash {

	public static String getSHA256(String... sarray) throws NoSuchAlgorithmException {
		String s = concat(sarray);
		System.out.printf("Hashing \"%s\"\n", s);
		MessageDigest md;
		md = MessageDigest.getInstance("SHA-256");
		byte[] b = md.digest(s.getBytes(StandardCharsets.UTF_8));
		BigInteger number = new BigInteger(1, b);
		StringBuilder hexString = new StringBuilder(number.toString(16));
		while (hexString.length() < 32) {
			hexString.insert(0, '0');
		}
		String mds = hexString.toString();
		System.out.printf("hash is:\n%s\n", mds);
		return hexString.toString();
	}
	
	public static String getSHA256(long timestamp, String last_hash, String[] data, int difficulty, int nonce) throws NoSuchAlgorithmException {
		System.out.println("Hashing something");
		String s = "";
		s +=  Long.toString(timestamp);
		s += last_hash;
		s += concat(data);
		s += Integer.toString(difficulty);
		s += Integer.toString(nonce);
		System.out.printf("Hashing \"%s\"\n", s);
		MessageDigest md;
		md = MessageDigest.getInstance("SHA-256");
		byte[] b = md.digest(s.getBytes(StandardCharsets.UTF_8));
		BigInteger number = new BigInteger(1, b);
		StringBuilder hexString = new StringBuilder(number.toString(16));
		System.out.println(hexString);
		while (hexString.length() < 32) {
			hexString.insert(0, '0');
		}
		String messageDigestString = hexString.toString();
		System.out.printf("hash is:\n%s\n", messageDigestString);
		return hexString.toString();
	}

	public static String concat(String... args) {
		String s = "";
		for (String $ : args) {
			s += $;
		}
		System.out.println(s);
		return s;
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {
//		c.concat("foo","bar", "bat");
		String md = getSHA256("foo", "bar", "bat");

	}

}
