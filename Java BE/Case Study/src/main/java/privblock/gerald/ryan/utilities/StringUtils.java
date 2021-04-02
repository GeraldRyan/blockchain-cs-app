package privblock.gerald.ryan.utilities;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;
import java.util.UUID;

public class StringUtils {

	public static String RandomStringLenN(int n) {

		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = n;
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(targetStringLength);
		for (int i = 0; i < targetStringLength; i++) {
			int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
			buffer.append((char) randomLimitedInt);
		}
		String generatedString = buffer.toString();
		return generatedString;
	}

	/**
	 * Method for converting a string array into a byte array directly
	 * through streaming objects. Can replace with below? 
	 * 
	 * @param strArray
	 * @return
	 * @throws IOException
	 */
	public static byte[] stringArrayToByteArray(String[] strArray) throws IOException {

		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		final ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
		objectOutputStream.writeObject(strArray);
		objectOutputStream.flush();
		objectOutputStream.close();
		return byteArrayOutputStream.toByteArray();
	}
	
	/**
	 * Converts a object into a byte array directly
	 * through streaming objects. 
	 * 
	 * @param strArray
	 * @return
	 * @throws IOException
	 */
	public static byte[] objectToByteArray(Object obj) throws IOException {

		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		final ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
		objectOutputStream.writeObject(obj);
		objectOutputStream.flush();
		objectOutputStream.close();
		return byteArrayOutputStream.toByteArray();
	}

	public static String[] byteArrayToStringArray(byte[] byteArray) throws ClassNotFoundException, IOException {
		final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);
		final ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
		final String[] stringArray = (String[]) objectInputStream.readObject();
		objectInputStream.close();
		return stringArray;
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		final String[] stringArray = { "foo", "bar", "baz" };
		System.out.println(stringArray.toString());
		for (String s : stringArray) {
			System.out.println(s);
		}
		byte[] byteArray = StringUtils.stringArrayToByteArray(stringArray);
		String[] reconstitutedStringArray = StringUtils.byteArrayToStringArray(byteArray);
		for (String s : reconstitutedStringArray) {
			System.out.println(s);
		}
	}
	
	public static String getUUID8() {
		return String.valueOf(UUID.randomUUID()).substring(0, 8);
	}

//	public static String removeQuotesAndUnescape(String uncleanJson) {
//	    String noQuotes = uncleanJson.replaceAll("^\"|\"$", "");
//
//	    return StringEscapeUtils.unescapeJava(noQuotes);
//	}

}

//package privblock.gerald.ryan.utilities;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class StringToBinary {
//	   public static String convertStringToBinary(String input) {
//
//	        StringBuilder result = new StringBuilder();
//	        char[] chars = input.toCharArray();
//	        for (char aChar : chars) {
//	        	System.out.println(Integer.toBinaryString(aChar));
//	            result.append(
//	                    String.format("%8s", Integer.toBinaryString(aChar))   // char -> int, auto-cast
//	                            .replaceAll(" ", "0")                         // zero pads
//	            );
//	        }
//	        return result.toString();
//
//	    }
//
////	    public static String prettyBinary(String binary, int blockSize, String separator) {
////
////	        List<String> result = new ArrayList<>();
////	        int index = 0;
////	        while (index < binary.length()) {
////	            result.add(binary.substring(index, Math.min(index + blockSize, binary.length())));
////	            index += blockSize;
////	        }
////
////	        return result.stream().collect(Collectors.joining(separator));
////	    }
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//        String input = "cbaeb638cbca8d1ea913d1f0f66523a914c8d19d3e21bdb6edbe93379fc8a25c";
//        String result = convertStringToBinary(input);
//
//        System.out.println(result);
//
//        // pretty print the binary format
////        System.out.println(prettyBinary(result, 8, " "));
//	}
//
//}
