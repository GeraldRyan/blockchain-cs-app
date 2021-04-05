package spring.controller;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;

//select instance_name,b.id,hash,data from blockchain c inner join blocksbychain bc on c.id=bc.blockchain_id inner join block b on bc.chain_id=b.id;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.google.gson.Gson;
import com.pubnub.api.PubNubException;

import exceptions.BlocksInChainInvalidException;
import exceptions.ChainTooShortException;
import exceptions.GenesisBlockInvalidException;
import privblock.gerald.ryan.entity.Block;
import privblock.gerald.ryan.entity.BlockData;
import privblock.gerald.ryan.entity.Blockchain;
import privblock.gerald.ryan.entity.Message;
import privblock.gerald.ryan.entity.Transaction;
import privblock.gerald.ryan.entity.TransactionPool;
//import org.springframework.web.bind.annotation.RequestMapping;
import privblock.gerald.ryan.entity.User;
import privblock.gerald.ryan.entity.Wallet;
import privblock.gerald.ryan.initializors.Initializer;
import privblock.gerald.ryan.service.BlockService;
import privblock.gerald.ryan.service.BlockchainService;
import privblock.gerald.ryan.utilities.StringUtils;
import pubsub.PubNubApp;

//@RequestMapping("/admin")
@Controller
@SessionAttributes("blockchain")
public class HomeController {

	BlockService blockApp = new BlockService();
	BlockchainService blockchainApp = new BlockchainService();
	PubNubApp pnapp = new PubNubApp();

	public HomeController() throws InterruptedException {
//		pnapp = new PubNubApp(); // moved to @modelAttribute new blockchain
	}
//	@RequestMapping("/")
//	public ModelAndView welcome() {
//		ModelAndView mav = new ModelAndView("index");
//		return mav;
//	}

//	@RequestMapping(value="/process", method=RequestMethod.POST)
//	public ModelAndView processSomething() {
//		return new ModelAndView("index");
//	}

	@ModelAttribute("afb")
	public String addFooBar() {
		return "FooAndBar";
	}

	@ModelAttribute("transactionpool")
	public TransactionPool initTransactionPool() {
		return new TransactionPool();
	}
	


	/**
	 * Pulls up beancoin blockchain on startup. [Note to self: what is startup? Is
	 * it session based? What is session? Define the terms]
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

	/**
	 * Preload site with wallet object
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws InvalidAlgorithmParameterException
	 */
	@ModelAttribute("wallet")
	public Wallet addWallet()
			throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
		Wallet wallet = Wallet.createWallet();
		return wallet;
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

//	@ModelAttribute("pubnubapp")
//	public PubNubApp addPubNub() throws InterruptedException {
//		return new PubNubApp();
//	}

	@Development // does nothing just a markup annotation
	public void consoleModelProperties(Model model) {
		System.out.println("Model class is " + model.getClass());
		System.out.println(model.toString());

	}

	@GetMapping("/")
	public String showIndex(Model model) {
		consoleModelProperties(model);
		return "index";
	}

	@RequestMapping(value = "blockchain", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String serveBlockchain(Model model) throws NoSuchAlgorithmException, InterruptedException,
			ChainTooShortException, GenesisBlockInvalidException, BlocksInChainInvalidException {
		refreshChain(model);
		return ((Blockchain) model.getAttribute("blockchain")).toJSONtheChain();
	}

	@GetMapping("/blockchaindesc")
	public String serveBlockchaindesc(Model model) throws NoSuchAlgorithmException {
		model.addAttribute("blockdata", new BlockData());
		return "blockchaindesc";
	}

	@GetMapping("/wallet")
	public String getWallet(Model model) {

		return "wallet";
	}

	@PostMapping("/blockchaindesc")
	public String save(@ModelAttribute("blockdata") BlockData blockData) {
		System.out.println(blockData.getBlockdata());
		return "redirect:/blockchain/mine";
	}

	@GetMapping("/blockchain/mine")
	public String getMine(@ModelAttribute("blockchain") Blockchain blockchain, Model model)
			throws NoSuchAlgorithmException, PubNubException, InterruptedException {
//		blockchain.add_block("FOOBARFORTHEWIN");
		String stubbedData = "MAIN INSTANCE STUBBED DATA";
		String[] stubbedDataV = { "MAIN INSTANCE STUBBED DATA" };
		// Block new_block = blockchain.add_block(stubbedData);
		Block new_block = blockchainApp.addBlockService("beancoin", stubbedDataV);
//		blockApp.addBlockService(new_block);
		model.addAttribute("foo", "Bar");
		pnapp.broadcastBlock(new_block);
		return "mine";
	}

	@GetMapping("/wallet/transact")
	public String getTransact(@ModelAttribute("wallet") Wallet w)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
//		Transaction t1 = new Transaction(w, "654s", 100);

		return "transact";
	}

	@PostMapping("/wallet/transact")
	@ResponseBody
	public String postTransact(Model model, @RequestBody Map<String, Object> body, @ModelAttribute("wallet") Wallet w)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
		System.out.printf("Address in post %s\n", body);
		Transaction t1 = new Transaction(w, (String) body.get("address"), (double) ((Integer) body.get("amount")));
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("status", 200);
		hm.put("data", t1.toJSONtheTransaction());
//		return new Gson().toJson(hm); // this would work but gson adds escape slashes and breaks json prettier
//		System.out.println(t1.toString());
		try {
			pnapp.broadcastTransaction(t1);
		} catch (PubNubException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t1.toJSONtheTransaction(); // response body
//		return "transaction";
	}

	@RequestMapping(value = "/wallet/transaction", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String postTransaction(@ModelAttribute("wallet") Wallet w, Model model,
			@RequestParam("address") String address, @RequestParam("amount") double amount, HttpServletRequest request)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
		System.err.println("REQUEST PARAMS");
		System.err.println(address);
		System.err.println(amount);
		Transaction t1 = new Transaction(w, address, amount);
		model.addAttribute("transaction", t1);
		System.out.println(t1.getAmount());
		// Transaction t1 = new Transaction(w, address, amount);
//		System.out.println(t1.toString());
//		return "transaction";

		return t1.toJSONtheTransaction();

	}

	@GetMapping("/login")
	public String showLoginPage() {
		return "login";
	}

	@PostMapping("/login")
	public String processInput(@RequestParam("name") String name, @RequestParam("email") String email) {

		System.out.println(name);
		System.out.println(email);
		return "index";
	}

	@GetMapping("/register")
	public String showRegisterPage(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	@PostMapping("/register")
	public String registerUser(@ModelAttribute("user") User user) {
		System.out.println(user.toString());
		return "index";
	}

	@GetMapping("/data")
	public String getData(Model model) {
		model.addAttribute("blockdata", new BlockData());
		return "data";
	}

	@PostMapping("/data")
	public String processData(@ModelAttribute("blockdata") BlockData blockdata) {
		System.out.println(blockdata.getBlockdata());
		return "data";
	}

	@GetMapping("/publish")
	public String getPublish(Model model) {
		model.addAttribute("message", new Message());
		return "publish";
	}

//	String messages;

	@PostMapping("/publish")
	public String getPublish(@ModelAttribute("message") Message message, Model model)
			throws InterruptedException, PubNubException {

		System.out.println("Publish post mapping ran");
//		messages += message.getMessage() + "\n";
		pnapp.publish(message.getChannel(), message.getMessage());
		model.addAttribute("display", message.getMessage());
//		model.addAttribute("display", messages);
		return "publish";
	}

	@GetMapping("/subscribe")
	public String getSubscribe() {
		return "subscribe";
	}

	@PostMapping("/subscribe")
	public String subToChannel(@RequestParam("channel") String channel) throws InterruptedException, PubNubException {
		pnapp.subscribe(channel);
		System.out.println("Publish post mapping ran");
//		messages += message.getMessage() + "\n";

//		model.addAttribute("display", messages);
		return "subscribe";
	}

}