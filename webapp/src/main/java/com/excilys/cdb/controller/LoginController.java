package com.excilys.cdb.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.excilys.cdb.dao.UserDAO;
import com.excilys.cdb.dto.CdbUserDetails;
import com.excilys.cdb.dto.User;

@SessionAttributes({ "currentUser" })
@Controller
public class LoginController {
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserDAO userDAO;

	@GetMapping(value = "/login")
	public String login() {
		return "login";
	}

	@GetMapping(value = "/logout")
	public String logout(SessionStatus session) {
		SecurityContextHolder.getContext().setAuthentication(null);
		session.setComplete();
		return "redirect:/login?logout=true";
	}

	@PostMapping(value = "/login")
	public String postLogin(Model model, HttpSession session,
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {
		// read principal out of security context and set it to session
		UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder
				.getContext().getAuthentication();
		System.out.println("azeazd" + authentication);
		validatePrinciple(authentication.getPrincipal());
		User loggedInUser = ((CdbUserDetails) authentication.getPrincipal()).getUserDetails();
		String errorMessge = null;
		if (error != null) {
			errorMessge = "Username or Password is incorrect !!";
		}
		if (logout != null) {
			errorMessge = "You have been successfully logged out !!";
		}
		model.addAttribute("errorMessge", errorMessge);
		model.addAttribute("currentUser", loggedInUser.getUsername());
		session.setAttribute("userId", loggedInUser.getId());
		return "redirect:/home";
	}

	private void validatePrinciple(Object principal) {
		if (!(principal instanceof CdbUserDetails)) {
			System.out.println(27);
			throw new IllegalArgumentException("Principal can not be null!");
		}
		System.out.println(27);
	}
	
	@GetMapping("/register")
    public String doRegister() {
        String encodedPassword  = passwordEncoder.encode("test");
        User user = new User();
        user.setPassword(encodedPassword);
        user.setId(1);
        userDAO.modifyUser(user);      
        User user2 = new User();
        user2.setPassword(encodedPassword);
        user.setId(2);
        userDAO.modifyUser(user2);
        return "redirect:/login";
    }
}
