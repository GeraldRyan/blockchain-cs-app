package spring.controller;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import privblock.gerald.ryan.entity.User;
import privblock.gerald.ryan.entity.Wallet;
import privblock.gerald.ryan.service.UserService;

@Controller
@RequestMapping("/register")
@SessionAttributes({ "user", "isloggedin" })
public class RegistrationController {
	UserService userService = new UserService();

	@GetMapping("")
	public String showRegisterPage(Model model) {
		model.addAttribute("user", new User());
		return "registration/register";
	}

	@PostMapping("")
	public String registerUser(Model model, @ModelAttribute("user") User user)
			throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
		System.out.println(user.toString());
		System.out.println(user.getEmail());
		System.out.println(user.getHint());
		user.setWallet(Wallet.createWallet());
		new UserService().addUserService(user);
		model.addAttribute("isloggedin", true);
		model.addAttribute("user", user);
		
		return "registration/welcomepage";
	}

	@GetMapping("welcome")
	public String getWelcome(Model model) {
		User u = ((User) model.getAttribute("user"));
		return "registration/welcomepage";
	}

}
