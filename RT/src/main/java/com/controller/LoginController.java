package com.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@RequestMapping("/signup")
	public String redirectToSignUp(HttpServletRequest incomingRequest, Model model) {
		return "signup";
	}

	@RequestMapping("/signin")
	public String redirectToLogin(HttpServletRequest incomingRequest, Model model) {
		return "signin";
	}

	@RequestMapping("/validate-login")
	public String validateLogin(HttpServletRequest incomingRequest, Model model) {
		System.out.println("Validater Invoked --");

		String userName = incomingRequest.getParameter("userName");
		String passWord = incomingRequest.getParameter("password");
		System.out.println(userName);
		System.out.println(passWord);

		List<Map<String, Object>> s = jdbcTemplate.queryForList("select * from sys.rt_travels");

		System.out.println(s.get(3));
		System.out.println(s.size());
		for (int i = 0; i <= s.size() - 1; i++) {

			if (s.get(i).containsValue(userName) && s.get(i).containsValue(passWord)) {
				model.addAttribute("userName", userName);
				return "travel-book";
			}
		}
		return "Error";

	}

	@RequestMapping("/create-user")
	public String createUser(HttpServletRequest incomingRequest, Model model) {
		String email = incomingRequest.getParameter("email");
		String password = incomingRequest.getParameter("password");
		String confirmPassword = incomingRequest.getParameter("confirm-password");
		String dob = incomingRequest.getParameter("dob");

		System.out.println(
				String.format(" Details are email %s and password is %s and confirm password is %s and dob is %s ",
						email, password, confirmPassword, dob));
		/*
		 * System.out.println (" Details are email " + email+ "and password is"
		 * +password+ "and confirm password" + confirm password+ "and dob is  " +dob );
		 */
		int rowCount = jdbcTemplate.update("INSERT INTO sys.rt_travels (`email`, `password`) VALUES (?, ?)", email,
				password);
		System.out.println("Rows INserted " + rowCount);
		model.addAttribute("email", email);

		return "travel-book";
	}

}

