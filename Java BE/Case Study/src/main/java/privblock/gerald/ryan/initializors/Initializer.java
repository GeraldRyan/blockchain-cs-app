package privblock.gerald.ryan.initializors;

import java.security.NoSuchAlgorithmException;

import privblock.gerald.ryan.entity.Blockchain;
import privblock.gerald.ryan.service.BlockchainService;
import privblock.gerald.ryan.utilities.StringUtils;

// drop table blocksbychain; drop table block; drop table blockchain;
public class Initializer {

	public static Blockchain initRandomBlockchain() throws NoSuchAlgorithmException {
		Blockchain blockchain = Blockchain.createBlockchainInstance(StringUtils.RandomStringLenN(5));
		for (int i = 0; i < 2; i++) {
			blockchain.add_block(String.valueOf(i));
		}
		return blockchain;
	}

	public static void loadBC(String nameOfBlockchain) {
		BlockchainService blockchainApp = new BlockchainService();
		blockchainApp.addBlockService(nameOfBlockchain, new String[] { "Dance", "The", "Quickstep" });
		blockchainApp.addBlockService(nameOfBlockchain, new String[] { "Dance", "The", "Waltz" });
		blockchainApp.addBlockService(nameOfBlockchain, new String[] { "Dance", "The", "Tango" });
		blockchainApp.addBlockService(nameOfBlockchain, new String[] { "Dance", "The", "Samba" });
		blockchainApp.addBlockService(nameOfBlockchain, new String[] { "Dance", "With", "Us", "America" });
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
