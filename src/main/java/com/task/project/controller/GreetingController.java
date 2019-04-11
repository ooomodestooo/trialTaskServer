package com.task.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.task.project.domain.User;
import com.task.project.repository.UserRepository;

@RestController
public final class GreetingController {

	List<String> usersLogonID = new ArrayList<>();
	
	@Autowired
	private UserRepository userRepository;

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/userSearch", consumes = "application/json")
	public List<User> searchForm(@Valid @RequestBody Map<String, String> request) {
		if (userRepository.findByName(request.get("name")).isEmpty()) {
			List<User> listUser = userRepository.findAll();
			return listUser;
		} else {
			List<User> listUser = userRepository.findByName(request.get("name"));
			return listUser;
		}
	}
	
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/user", consumes = "application/json")
	Map<String, String> registerUser(@Valid @RequestBody Map<String, String> request) {
		
		Map<String, String> response = new HashMap<>();
		
		boolean logonIsExist = userRepository.existsByLogonID(request.get("logonID")); 
		
		if (logonIsExist) {
			response.put("Response", "Exist");
			return response;
		}
		
		User u = new User(request.get("logonID"), request.get("name"), request.get("password"), request.get("email"));
		
		try {
			userRepository.save(u);
		} catch (Exception e) {
			response.put("Response", e.toString());
			return response;
		}
		response.put("Response", "Saved");
		return response;
  }
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/userUpdate", consumes = "application/json")
	Map<String, String> userUpdate(@Valid @RequestBody Map<String, String> request) {
		
		Map<String, String> response = new HashMap<>();

		User userNeadUpdate = userRepository.findByLogonID(request.get("logonID"));
		
		boolean smtChange = false;
		
		if (!(userNeadUpdate.getName().equals(request.get("name"))) && (request.get("name") != null)) {
			userNeadUpdate.setName(request.get("name"));
			smtChange = true;
		}
		
		if ((request.get("password") != null) && !(userNeadUpdate.getPassword().equals(request.get("password"))) && (request.get("password").length() >= 6)) {
			userNeadUpdate.setPassword(request.get("password"));
			smtChange = true;
		}
		
		if (userNeadUpdate.getEmail() == null || (!userNeadUpdate.getEmail().equals(request.get("email")) && (request.get("email") != ""))) {
			userNeadUpdate.setEmail(request.get("email"));
			smtChange = true;
		}

		User userUpdate = new User(userNeadUpdate.getLogonID(), userNeadUpdate.getName(), userNeadUpdate.getPassword(), userNeadUpdate.getEmail());
		
		userRepository.save(userUpdate);

		if (smtChange) 
			response.put("Response", "Saved");
		
		else 
			response.put("Response", "NthChange");

		return response;
    }
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/userDelete", consumes = "application/json")
	Map<String, String> userDelete(@Valid @RequestBody Map<String, String> request){
		
		Map<String, String> response = new HashMap<>();
		
		try {
			userRepository.deleteById(request.get("logonID"));
		} catch (Exception e) {
			response.put("Response", e.toString());
			return response;
		}
		response.put("Response", "Deleted");
		return response;
    }
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/userCheck", consumes = "application/json")
	Map<String, String> logonUser(@Valid @RequestBody Map<String, String> request) {
		
		Map<String, String> response = new HashMap<>();
		
		boolean userIsRegister = userRepository.existsByLogonIDAndPassword(request.get("logonID"), request.get("password")); 
		
		if (userIsRegister) {
			usersLogonID.add(request.get("logonID"));
			response.put("Response", "Success");
		} else {
			response.put("Response", "Wrong");
		}
		return response;
    }
	
	@CrossOrigin(origins = "*")
	@GetMapping("/getAllUsers")
	List<User> getAllUsers() {
		List<User> listUser = userRepository.findAll();
		return listUser;
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/userInfo")
	User userProfile(@Valid @RequestBody Map<String, String> request) {
		return userRepository.findByLogonID(request.get("logonID"));
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/checkUserAut")
	Map<String, String> checkUserAut(@Valid @RequestBody Map<String, String> request) {
		
		Map<String, String> response = new HashMap<>();
		
		if (usersLogonID.contains(request.get("logonID")))
		{
			response.put("Response", "Success");
		} else {
			response.put("Response", "Wrong");
		}
		return response;
	}

	@CrossOrigin(origins = "*")
	@PostMapping("/logout")
	Map<String, String> logout(@Valid @RequestBody Map<String, String> request) {
		
		Map<String, String> response = new HashMap<>();
		
		if (usersLogonID.remove(request.get("logonID")))
		{
			while (usersLogonID.remove(request.get("logonID")));
			response.put("Response", "Success");
		} else {
			response.put("Response", "Wrong");
		}
		return response;
	}
}