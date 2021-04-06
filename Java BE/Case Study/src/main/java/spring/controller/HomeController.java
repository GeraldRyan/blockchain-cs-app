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
import java.util.UUID;

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
@SessionAttributes({ "blockchain", "wallet", "user", "randomnumber", "isloggedin" })
public class HomeController {


	PubNubApp pnapp = new PubNubApp();

	public HomeController() throws InterruptedException {
//		pnapp = new PubNubApp(); // moved to @modelAttribute new blockchain
	}


	@ModelAttribute("isloggedin")
	public boolean isLoggedIn() {
		return false;
	}

	@ModelAttribute("afb")
	public String addFooBar() {
		return "FooAndBar";
	}

	@ModelAttribute("randomnumber")
	public String randomUUID() {
		return String.valueOf(UUID.randomUUID()).substring(0, 8);
	}

	@ModelAttribute("transactionpool")
	public TransactionPool initTransactionPool() {
		return new TransactionPool();
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



//	@ModelAttribute("pubnubapp")
//	public PubNubApp addPubNub() throws InterruptedException {
//		return new PubNubApp();
//	}

	@Development // does nothing just a markup annotation
	public void consoleModelProperties(Model model) {
		System.out.println("Model class is " + model.getClass());
		System.out.println(model.toString());

	}

	@GetMapping("")
	public String showIndex(Model model) {
		consoleModelProperties(model);
		return "index";
	}



	@GetMapping("/wallet")
	public String getWallet(Model model) {

		return "wallet";
	}


	@GetMapping("/wallet/transact")
	public String getTransact(@ModelAttribute("wallet") Wallet w)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
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
		return "login/login";
	}

	@PostMapping("/login")
	public String processInput(@RequestParam("name") String name, @RequestParam("email") String email) {

		System.out.println(name);
		System.out.println(email);
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

	@RequestMapping(value = "homeplay", method = RequestMethod.GET)
	public String getPlay(Model model) {
//		StringUtils.mapKeyValue(model.asMap(), "homecontroller 302");
		System.out.println("Wallet Address " + ((Wallet) model.getAttribute("wallet")).getAddress());
		System.out.println("random number" + ((String) model.getAttribute("randomnumber")));
		return "play";
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