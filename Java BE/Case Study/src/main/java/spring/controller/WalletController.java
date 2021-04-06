package spring.controller;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Map;

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

import com.pubnub.api.PubNubException;

import privblock.gerald.ryan.entity.Transaction;
import privblock.gerald.ryan.entity.Wallet;
import pubsub.PubNubApp;

@Controller
@RequestMapping("wallet")
@SessionAttributes({ "wallet" })
public class WalletController {

	PubNubApp pnapp = new PubNubApp();

	public WalletController() throws InterruptedException {

	}

	/**
	 * Preload site with wallet object
	 * 
	 * TODO - GET THIS WALLET FROM DATABASE. NULL IF NULL. OPTION TO CREATE, AND
	 * THEN TO PERSIST BOTH TO DATABASE AND SESSION
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

	@GetMapping("")
	public String getWallet(Model model) {

		return "wallet/wallet";
	}

	@GetMapping("/transact")
	public String getTransact(@ModelAttribute("wallet") Wallet w)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
		return "wallet/transact";
	}

	@PostMapping("/transact")
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

	@RequestMapping(value = "/transaction", method = RequestMethod.GET, produces = "application/json")
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

}
