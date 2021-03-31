package privblock.gerald.ryan.dao;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import exceptions.BlocksInChainInvalidException;
import exceptions.ChainTooShortException;
import exceptions.GenesisBlockInvalidException;
import privblock.gerald.ryan.dbConnection.DBConnection;
import privblock.gerald.ryan.entity.Block;
import privblock.gerald.ryan.entity.Blockchain;

public class BlockchainDao extends DBConnection implements BlockchainDaoI {

	@Override
	public Blockchain newBlockchain(String name) {
		this.connect();
		try {
			Blockchain new_blockchain = new Blockchain(name);
			em.getTransaction().begin();
			em.persist(new_blockchain);
			em.getTransaction().commit();
			this.disconnect();
			System.out.println("New Blockchain added");
			return new_blockchain;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Blockchain getBlockchainById(int id) {
		try {
			this.connect();
			Blockchain b = em.find(Blockchain.class, id);
			this.disconnect();
			return b;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Blockchain getBlockchainByName(String name) throws NoResultException {
		this.connect();
		Query query = em.createQuery("select b from Blockchain b where b.instance_name = :name");
		query.setParameter("name", name);
		Blockchain blockchain = (Blockchain) query.getSingleResult();
		this.disconnect();
		return blockchain;
	}

	@Override
	public boolean addBlock(String name, String[] data) {
		this.connect();
		Query query = em.createQuery("select b from Blockchain b where b.instance_name = :name");
		query.setParameter("name", name);
		Blockchain blockchain = (Blockchain) query.getSingleResult();
		try {
			em.getTransaction().begin();
			Block new_block = blockchain.add_block(data);
			em.persist(new_block);
			em.getTransaction().commit();
			this.disconnect();
			System.out.println("Returning true");
			return true;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Blockchain getTopBlockchain() {
		this.connect();
		Query query = em.createQuery("select b from Blockchain b");
		Blockchain blockchain = (Blockchain) query.setMaxResults(1).getSingleResult();
		return blockchain;
	}

	@Override
	public boolean replaceChain(String name, ArrayList<Block> new_chain) throws NoSuchAlgorithmException,
			ChainTooShortException, GenesisBlockInvalidException, BlocksInChainInvalidException {
		this.connect();
		em.getTransaction().begin();
		Query query = em.createQuery("select b from Blockchain b where b.instance_name = :name");
		query.setParameter("name", name);
		Blockchain blockchain = (Blockchain) query.getSingleResult();
		System.out.println("GOING TO REPLACE CHAIN AS SERVICE");
//		Query jq = em.createQuery("select c from Blocksbychain c where c.blockchain_id = 1");
//		List<Object> jqr = jq.getResultList();
//		System.out.println(jqr.toString());
			
//		if (blockchain.willReplace(new_chain)) {
	
//			System.out.println("WILL REPLACE CHAIN");
//			// going to be replaced by new chain (lifecycle method), so let's clean up DB,
//			// trusting will be restored
//			// or atomically rolled back. Can we really trust in practice? Maybe not
//			for (Block b : new_chain) {
//				Block emblock = em.find(Block.class, b.getTimestamp());
//				if (emblock != null) {
//					System.out.println("Should remove block");
//					System.out.println(emblock.toJSONtheBlock());
//					em.remove(emblock);
//					em.getTransaction().commit();
//				}
//			}
//		}
		// now cleaned tables (with cascading) should be no primary key collisions
		if (blockchain.willReplace(new_chain)) {
			blockchain.setChain(null);
			em.getTransaction().commit();
		em.getTransaction().begin();
		blockchain.replace_chain(new_chain);
		em.getTransaction().commit();
		}
		this.disconnect();
		return true;
	}

	@Override
	public List<Blockchain> getAllBlockchains() {
		try {
			this.connect();
			List<Blockchain> list_of_chains = em.createQuery("select b from Blockchain b").getResultList();
			this.disconnect();
			return list_of_chains;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Block getBlockById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Block getBlockByHash(String hash) {
		// TODO Auto-generated method stub
		return null;
	}

}
