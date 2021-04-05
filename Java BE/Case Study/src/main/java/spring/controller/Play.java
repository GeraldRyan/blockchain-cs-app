package spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import privblock.gerald.ryan.utilities.StringUtils;

@Controller
@RequestMapping("/play")
public class Play {

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String getPlay(Model model) {
		model.addAttribute("heres", "something");
		StringUtils.mapKeyValue(model.asMap());
		return "play";
	}

}
