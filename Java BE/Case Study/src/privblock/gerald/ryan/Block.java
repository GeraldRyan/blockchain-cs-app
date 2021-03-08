package privblock.gerald.ryan;

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

	}
}
