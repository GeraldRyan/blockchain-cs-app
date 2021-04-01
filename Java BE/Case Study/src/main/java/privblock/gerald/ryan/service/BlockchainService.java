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

	/**
	 * Gets all blockchain instances as list
	 */
	public List<Blockchain> getAllBlockchainsService() {
		return blockchainD.getAllBlockchains();
	}

	/**
	 * Adds block to blockchain as a service that persists to the database. 
	 * Calls add_block method of blockchain, which calls mine_block method of Block class
	 * @param name
	 * @param data
	 * @return
	 */
	public Block addBlockService(String name, String[] data) {
		return blockchainD.addBlock(name, data);
	}

	/**
	 * Gets first blockchain result in database query
	 * @return
	 */
	public Blockchain getTopBlockchain() {
		return blockchainD.getTopBlockchain();
	}
	
	/**
	 * Replaces blockchain chain by calling DAO and updates local database entries. 
	 * TODO - Find better way to replace blockchain OneToMany table in database, as opposed to current method
	 * of setting it to null (truncating) and then setting the incoming chain in order to avoid collisions 
	 * @param name
	 * @param new_chain
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws ChainTooShortException
	 * @throws GenesisBlockInvalidException
	 * @throws BlocksInChainInvalidException
	 */
	public boolean replaceChainService(String name, ArrayList<Block> new_chain) throws NoSuchAlgorithmException, ChainTooShortException, GenesisBlockInvalidException, BlocksInChainInvalidException {
		return blockchainD.replaceChain(name, new_chain);
	}

}
