package privblock.gerald.ryan;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Date; // gets time in ms.
import privblock.gerald.ryan.util.CryptoHash;

/**
 * 
 * @author Gerald Ryan Block Class of blockchain app
 *
 *         Description: The block hash is the result of the timestamp, the
 *         last_hash, the data, the difficulty and the nonce
 *
 */
public class Block {
	long timestamp;
	String lastHash;
	String hash;
	String[] data;
	int difficulty;
	int nonce;

	static long NANOSECONDS = 1;
	static long MICROSECONDS = 1000 * NANOSECONDS;
	static long MILLISECONDS = 1000 * MICROSECONDS;
	static long SECONDS = 1000 * MILLISECONDS;
	static long MINE_RATE = 4 * SECONDS;

	/**
	 * A block is a unit of storage for a blockchain that supports a cryptocurrency.
	 * 
	 * @param timestamp
	 * @param lastHash
	 * @param hash
	 * @param data
	 * @param difficulty
	 * @param nonce
	 */
	public Block(long timestamp, String lastHash, String hash, String[] data, int difficulty, int nonce) {
		super();
		this.timestamp = timestamp;
		this.lastHash = lastHash;
		this.hash = hash;
		this.data = data;
		this.difficulty = difficulty;
		this.nonce = nonce;
	}

	/**
	 * Mine a block based on given last block and data until a block hash is found
	 * that meets the leading 0's Proof of Work requirement.
	 * 
	 * @param last_block
	 * @param data
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static Block mine_block(Block last_block, String[] data) throws NoSuchAlgorithmException {
		long timestamp = new Date().getTime();
		String last_hash = last_block.getHash();
		int difficulty = Block.adjust_difficulty(last_block, timestamp);
		int nonce = 0;
		String hash = CryptoHash.getSHA256(new Date().getTime(), last_block.getHash(), data, difficulty, nonce);
		return new Block(timestamp, last_hash, hash, data, difficulty, nonce);
	}

	/**
	 * Calculate the adjusted difficulty according to the MINE_RATE. Increase the
	 * difficulty for quickly mined blocks. Decrease the difficulty for slowly mined
	 * blocks.
	 * 
	 * @param last_block
	 * @param new_timestamp
	 */
	public static int adjust_difficulty(Block last_block, long new_timestamp) {
		long time_diff = new_timestamp - last_block.getTimestamp();
		if (time_diff < MINE_RATE) {
			return last_block.getDifficulty() + 1;
		} else if (last_block.getDifficulty() - 1 > 0) {
			return last_block.getDifficulty() - 1;
		} else {
			return 1;
		}
	}

	public int getDifficulty() {
		return difficulty;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public static Block genesis_block() {
		long timestamp = 1;
		String last_hash = "genesis_last_hash";
		String hash = "genesis_hash";
		String[] data = { "buy", "privcoin" };
		int difficulty = 3;
		int nonce = 0;
		return new Block(timestamp, last_hash, hash, data, difficulty, nonce);
	}

	public String getHash() {
		return hash;
	}

	/**
	 * Description -
	 * 
	 * @param args
	 * @throws NoSuchAlgorithmException
	 */

	/**
	 * Validate block by enforcing following rules: - Block must have the proper
	 * last_hash reference - Block must meet the proof of work requirements -
	 * difficulty must only adjust by one - block hash must be a valid combination
	 * of block fields
	 * 
	 * @param last_block
	 * @param block
	 * @return
	 */
	public static boolean is_valid_block(Block last_block, Block block) {
		if (block.getHash() != last_block.getHash()) {
			return false;
			// Should I throw an exception?
		}

		else {
			return true;
		}

	}

	public String toString() {
		return "\n-----------BLOCK--------\ntimestamp: " + this.timestamp + "\nlastHash: " + this.lastHash + "\nhash: "
				+ this.hash + "\ndifficulty: " + this.getDifficulty() + "\nNonce: " + this.nonce
				+ "\n-----------------------\n";

	}

	public static void main(String[] args) throws NoSuchAlgorithmException {
		String md = CryptoHash.getSHA256("foobar");
		Block genesis = genesis_block();
		System.out.println(genesis.toString());
		String[] new_data = { "watch", "AOT" };
		Block new_block = mine_block(genesis, new_data);
		System.out.println(new_block.toString());
		System.out.println(mine_block(new_block, new String[] { "crypto", "is", "fun" }).toString());
	}

}
