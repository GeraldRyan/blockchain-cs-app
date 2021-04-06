package spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import privblock.gerald.ryan.entity.User;
import privblock.gerald.ryan.service.UserService;

@Controller
@RequestMapping("/register")
public class RegistrationController {
	UserService userService = new UserService();

	@GetMapping("/")
	public String showRegisterPage(Model model) {
		model.addAttribute("user", new User());

		return "register";
	}

	@GetMapping("welcome")
	public String getWelcome(Model model) {
		User u = ((User) model.getAttribute("user"));
		return "welcomepage";
	}

	@PostMapping("/")
	public String registerUser(@ModelAttribute("user") User user) {
		System.out.println(user.toString());
		return "welcome";
	}

}
