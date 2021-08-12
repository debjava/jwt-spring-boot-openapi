package com.ddlab.rnd.service;

import java.util.HashSet;
import java.util.Optional;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddlab.rnd.entity.CreateUserRequest;
import com.ddlab.rnd.entity.User;
import com.ddlab.rnd.exception.CustomException;
import com.ddlab.rnd.repository.UserRepository;
import com.ddlab.rnd.security.JwtTokenUtil;

@Service
public class UserAuthService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	public String login(String username, String password) {
		String token = null;
		try {
			Optional<User> optUser = userRepository.findByUsername(username); //.getRoles();
			if(optUser.isPresent()) {
				Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
				User user = (User) authenticate.getPrincipal();
				System.out.println("user.getAuthorities()--->"+user.getAuthorities());
				token = jwtTokenUtil.generateAccessToken(user);
			}
			return token;
		} catch (AuthenticationException e) {
			throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	public String signup(User user) {
		if (userRepository.findByUsername(user.getUsername()).isEmpty()) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			userRepository.save(user);
			return jwtTokenUtil.generateAccessToken(user);
		} 
		else {
			throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	@Transactional
    public void upsert(CreateUserRequest request) {
        Optional<User> optionalUser = userRepository.findByUsername(request.getUsername());
        if (optionalUser.isEmpty()) {
            create(request);
        } 
    }
	
	@Transactional
    public void create(CreateUserRequest request) {
        if (!request.getPassword().equals(request.getPassword())) {
            throw new ValidationException("Passwords don't match!");
        }
        if (request.getAuthorities() == null) {
            request.setAuthorities(new HashSet<>());
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAuthorities(request.getAuthorities());
        user = userRepository.save(user);
    }

//	public void delete(String username) {
//		userRepository.deleteByUsername(username);
//	}
//
//	public User search(String username) {
//		User user = userRepository.findByUsername(username);
//		if (user == null) {
//			throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
//		}
//		return user;
//	}
//
//	public User whoisThis(HttpServletRequest req) {
//		return userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
//	}
//
	public String refresh(String username) {
		Optional<User> optUser = userRepository.findByUsername(username);
		return jwtTokenUtil.generateAccessToken(optUser.get());
//		return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoles());
	}

}
