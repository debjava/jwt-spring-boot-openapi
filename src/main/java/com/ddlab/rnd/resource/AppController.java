package com.ddlab.rnd.resource;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ddlab.rnd.entity.User;
import com.ddlab.rnd.entity.UserDTO;
import com.ddlab.rnd.role.Role;
import com.ddlab.rnd.service.UserAuthService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication")
@RestController
@RequestMapping(path = "api/public")
public class AppController {

	@Autowired
	private UserAuthService userAuthService;

	private ModelMapper mapper = new ModelMapper();

	@PostMapping("/signup")

	public String signup(@RequestBody UserDTO user) {
		return userAuthService.signup(mapper.map(user, User.class));
	}

	@PostMapping("/login")
	public String logon(@RequestParam String username, //
			@RequestParam String password) {
		return userAuthService.login(username, password);
	}

//	@GetMapping(value = "/whoisthis")
//	@RolesAllowed(Role.AUTHOR_ADMIN)
//	public UserResponseDTO whoIsThis(HttpServletRequest req) {
//		return mapper.map(userAuthService.whoisThis(req), UserResponseDTO.class);
//	}

	@GetMapping("/refresh")
	@RolesAllowed({Role.Names.ROLE_ADMIN, Role.Names.ROLE_CLIENT})
	public String refresh(HttpServletRequest req) {
		return userAuthService.refresh(req.getRemoteUser());
	}

	@GetMapping("/something")
	@RolesAllowed(Role.Names.ROLE_ADMIN)
	public String getSensistiveInfo() {
		return "This is a sensitive information for Admin.";
	}
	
	@GetMapping("/clientInfo")
	@RolesAllowed(Role.Names.ROLE_CLIENT)
	public String getClientInfo() {
		return "This is information only for Client.";
	}
	
	

}
