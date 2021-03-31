package spring.controller;

//select instance_name,b.id,hash,data from blockchain c inner join blocksbychain bc on c.id=bc.blockchain_id inner join block b on bc.chain_id=b.id;

import java.security.NoSuchAlgorithmException;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.pubnub.api.PubNubException;

import privblock.gerald.ryan.entity.Block;
import privblock.gerald.ryan.entity.BlockData;
import privblock.gerald.ryan.entity.Blockchain;
import privblock.gerald.ryan.entity.Message;
//import org.springframework.web.bind.annotation.RequestMapping;
import privblock.gerald.ryan.entity.User;
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

	/*
	 * Pulls up beancoin blockchain on startup. [Note to self: what is startup? Is
	 * it session based? What is session? Define the terms]
	 * 
	 * If no beancoin exists, create one and populate it with initial values
	 */
	@ModelAttribute("blockchain")
	public Blockchain addBlockchain() throws NoSuchAlgorithmException, InterruptedException {
		try {
			Blockchain blockchain = blockchainApp.getBlockchainService("beancoin");
			pnapp = new PubNubApp(blockchain);
			System.out.println("Pulling up your beancoin from our records");
			return blockchain;
		} catch (NoResultException e) {
			System.err.println("Creating new beancoin");
			Blockchain blockchain = blockchainApp.newBlockchainService("beancoin");
			Initializer.loadBC("beancoin");
			Blockchain populated_blockchain = blockchainApp.getBlockchainService("beancoin");
			return populated_blockchain;
		}
	}

//	@ModelAttribute("pubnubapp")
//	public PubNubApp addPubNub() throws InterruptedException {
//		return new PubNubApp();
//	}

	@GetMapping("/")
	public String showIndex() {
		return "index";
	}

	@GetMapping("/blockchain")
	public String serveBlockchain(Model model) {
		return "blockchain";
	}

	@GetMapping("/blockchaindesc")
	public String serveBlockchaindesc(Model model) throws NoSuchAlgorithmException {
		model.addAttribute("blockdata", new BlockData());
		return "blockchaindesc";
	}

	@PostMapping("/blockchaindesc")
	public String save(@ModelAttribute("blockdata") BlockData blockData) {
		System.out.println(blockData.getBlockdata());
		return "redirect:/blockchain/mine";
	}

	@GetMapping("/blockchain/mine")
	public String getMine(@ModelAttribute("blockchain") Blockchain blockchain, Model model)
			throws NoSuchAlgorithmException, PubNubException {
//		blockchain.add_block("FOOBARFORTHEWIN");
		String stubbedData = "STUBBED DATA";
		Block new_block = blockchain.add_block(stubbedData);
		blockApp.addBlockService(new_block);
		model.addAttribute("foo", "Bar");
		pnapp.broadcastBlock(new_block);
		return "mine";
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