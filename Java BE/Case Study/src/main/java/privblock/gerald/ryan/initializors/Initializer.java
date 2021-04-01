package privblock.gerald.ryan.initializors;

import java.security.NoSuchAlgorithmException;

import privblock.gerald.ryan.entity.Blockchain;
import privblock.gerald.ryan.service.BlockchainService;
import privblock.gerald.ryan.utilities.StringUtils;

// drop table blocksbychain; drop table block; drop table blockchain;
public class Initializer {

	
	/**
	 * Used to load a new blockchain up with 5 valid blocks (or used after loaded)
	 * @param nameOfBlockchain
	 */
	public static void loadBC(String nameOfBlockchain) {
		BlockchainService blockchainApp = new BlockchainService();
		blockchainApp.addBlockService(nameOfBlockchain, new String[] { "Dance", "The", "Quickstep" });
		blockchainApp.addBlockService(nameOfBlockchain, new String[] { "Dance", "The", "Waltz" });
		blockchainApp.addBlockService(nameOfBlockchain, new String[] { "Dance", "The", "Tango" });
		blockchainApp.addBlockService(nameOfBlockchain, new String[] { "Dance", "The", "Samba" });
		blockchainApp.addBlockService(nameOfBlockchain, new String[] { "Dance", "With", "Us", "America" });
	}
	
	/**
	 * Initialize random blockchain instances with random string names.
	 * Why would one use this?
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static Blockchain initRandomBlockchain() throws NoSuchAlgorithmException {
		Blockchain blockchain = Blockchain.createBlockchainInstance(StringUtils.RandomStringLenN(5));
		for (int i = 0; i < 2; i++) {
			blockchain.add_block(String.valueOf(i));
		}
		return blockchain;
	}
	

	public static void main(String[] args) {
		BlockchainService blockchainApp = new BlockchainService();
		blockchainApp.newBlockchainService("beancoin");
		blockchainApp.addBlockService("beancoin", new String[] { "Dance", "The", "Quickstep" });
		blockchainApp.addBlockService("beancoin", new String[] { "Dance", "The", "Waltz" });
		blockchainApp.addBlockService("beancoin", new String[] { "Dance", "The", "Tango" });
		blockchainApp.addBlockService("beancoin", new String[] { "Dance", "The", "Samba	" });
	}
}
