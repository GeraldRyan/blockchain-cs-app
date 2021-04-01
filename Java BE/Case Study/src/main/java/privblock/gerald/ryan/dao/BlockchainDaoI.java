package privblock.gerald.ryan.dao;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import exceptions.BlocksInChainInvalidException;
import exceptions.ChainTooShortException;
import exceptions.GenesisBlockInvalidException;
import privblock.gerald.ryan.entity.Block;
import privblock.gerald.ryan.entity.Blockchain;

public interface BlockchainDaoI {
	public Blockchain newBlockchain(String name);
	public Block addBlock(String name, String[] data);
	public boolean replaceChain(String blockchain, ArrayList<Block> new_chain) throws NoSuchAlgorithmException, ChainTooShortException, GenesisBlockInvalidException, BlocksInChainInvalidException;
	public List<Blockchain> getAllBlockchains();
	public Blockchain getBlockchainById(int id);
	public Blockchain getTopBlockchain();
	public Block getBlockById(int id); 
	public Block getBlockByHash(String hash); // is it a long? TODO Implement // could overload method if types differ
	public Blockchain getBlockchainByName(String name);
	
}
