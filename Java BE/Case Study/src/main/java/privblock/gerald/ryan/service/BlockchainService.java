package privblock.gerald.ryan.service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import exceptions.BlocksInChainInvalidException;
import exceptions.ChainTooShortException;
import exceptions.GenesisBlockInvalidException;
import privblock.gerald.ryan.dao.BlockchainDao;
import privblock.gerald.ryan.entity.Block;
import privblock.gerald.ryan.entity.Blockchain;

public class BlockchainService {
	private BlockchainDao blockchainD = new BlockchainDao();

	public Blockchain newBlockchainService(String name) {
		return blockchainD.newBlockchain(name);
	}
	
	
	/**
	 * Returns new blockchain based on name provided
	 */
	public Blockchain getBlockchainService(String name) {
		return blockchainD.getBlockchainByName(name);
	}

	public List<Blockchain> getAllBlockchainsService() {
		return blockchainD.getAllBlockchains();
	}

	public boolean addBlockService(String name, String[] data) {
		return blockchainD.addBlock(name, data);
	}

	public Blockchain getTopBlockchain() {
		return blockchainD.getTopBlockchain();
	}
	
	public boolean replaceChainService(String name, ArrayList<Block> new_chain) throws NoSuchAlgorithmException, ChainTooShortException, GenesisBlockInvalidException, BlocksInChainInvalidException {
		return blockchainD.replaceChain(name, new_chain);
	}

}
