package com.ddlab.rnd;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.ddlab.rnd.entity.CreateUserRequest;
import com.ddlab.rnd.role.Role;
import com.ddlab.rnd.service.UserAuthService;

@Component
public class BootStrapInitializer implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	private UserAuthService userAuthService;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		CreateUserRequest request = new CreateUserRequest();
        request.setUsername("deba");
        request.setPassword("Deba@1234");
        Set<Role> authSet = new HashSet<Role>();
        authSet.add(Role.ROLE_ADMIN);
        request.setAuthorities(authSet);
		userAuthService.upsert(request);
	}

}
