package spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import privblock.gerald.ryan.entity.User;
import privblock.gerald.ryan.service.UserService;

@Controller
@RequestMapping("/register")
@SessionAttributes({ "user" })
public class RegistrationController {
	UserService userService = new UserService();

	@GetMapping("")
	public String showRegisterPage(Model model) {
		model.addAttribute("user", new User());
		return "registration/register";
	}

	@PostMapping("")
	public String registerUser(@ModelAttribute("user") User user) {
		System.out.println(user.toString());
		return "registration/welcomepage";
	}

	@GetMapping("welcome")
	public String getWelcome(Model model) {
		User u = ((User) model.getAttribute("user"));
		return "registration/welcomepage";
	}

}
