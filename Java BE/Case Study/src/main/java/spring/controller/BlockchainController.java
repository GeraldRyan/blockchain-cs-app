package spring.controller;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.pubnub.api.PubNubException;

import exceptions.BlocksInChainInvalidException;
import exceptions.ChainTooShortException;
import exceptions.GenesisBlockInvalidException;
import privblock.gerald.ryan.entity.Block;
import privblock.gerald.ryan.entity.Blockchain;
import privblock.gerald.ryan.entity.TransactionPool;
import privblock.gerald.ryan.initializors.Initializer;
import privblock.gerald.ryan.service.BlockService;
import privblock.gerald.ryan.service.BlockchainService;
import pubsub.PubNubApp;

@Controller
@SessionAttributes({ "blockchain" })
@RequestMapping("blockchain")
public class BlockchainController {

	public BlockchainController() throws InterruptedException {
	}

	BlockService blockApp = new BlockService();
	BlockchainService blockchainApp = new BlockchainService();
	PubNubApp pnapp = new PubNubApp();

	/**
	 * Pulls up beancoin blockchain on startup.
	 * 
	 * If no beancoin exists, create one and populate it with initial values
	 * 
	 * Also syncs blockchain so should be updated
	 */
	@ModelAttribute("blockchain")
	public Blockchain addBlockchain(Model model) throws NoSuchAlgorithmException, InterruptedException {
//		System.err.println("addBlockchain called at controller");
		try {
			Blockchain blockchain = blockchainApp.getBlockchainService("beancoin");
			PubNubApp pnapp = new PubNubApp(blockchain, (TransactionPool) model.getAttribute("transactionpool"));
			System.out.println("Pulling up your beancoin from our records");
			return blockchain;
		} catch (Exception e) {
			System.err.println("Creating new beancoin");
			Blockchain blockchain = blockchainApp.newBlockchainService("beancoin");
			Initializer.loadBC("beancoin");
			Blockchain populated_blockchain = blockchainApp.getBlockchainService("beancoin");
			return populated_blockchain;
		}
	}

	@RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String serveBlockchain(Model model) throws NoSuchAlgorithmException, InterruptedException,
			ChainTooShortException, GenesisBlockInvalidException, BlocksInChainInvalidException {
		refreshChain(model);
		return ((Blockchain) model.getAttribute("blockchain")).toJSONtheChain();
	}

	@GetMapping("/mine")
	public String getMine(@ModelAttribute("blockchain") Blockchain blockchain, Model model)
			throws NoSuchAlgorithmException, PubNubException, InterruptedException {

		String stubbedData = "MAIN INSTANCE STUBBED DATA";
		String[] stubbedDataV = { "MAIN INSTANCE STUBBED DATA" };
		Block new_block = blockchainApp.addBlockService("beancoin", stubbedDataV);
		model.addAttribute("foo", "Bar");
		pnapp.broadcastBlock(new_block);
		return "blockchain/mine";
	}

	public void refreshChain(Model model) {
		System.err.println("Refreshing Blockchain from local database");
		Blockchain newer_blockchain_from_db = blockchainApp.getBlockchainService("beancoin");
		try {
			// Database for some reason loads ArrayList<Block> unsorted. Manually resorting
			// it here upon load.
			ArrayList<Block> new_chain = new ArrayList<Block>(newer_blockchain_from_db.getChain());
			System.out.println("RE-SORTING ArrayList<Block>");
			// I believe I have to make a new chain instance for mutation. Won't mutate
			// Blockchain.chain property?
			Collections.sort(new_chain, Comparator.comparingLong(Block::getTimestamp));
			/*
			 * Are setter methods secure for chain replacement? Could replace with invalid
			 * chain. Should use replaceChain method, incorporating this into that, but
			 * struggling with JPA and loading in order.
			 */
			((Blockchain) model.getAttribute("blockchain")).setChain(new_chain);
		} catch (Exception e) {
			System.out.println("CAN'T SORT IT FOR SOME REASON");
			e.printStackTrace();
		}
	}

}