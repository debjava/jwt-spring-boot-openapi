package com.ddlab.rnd.role;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
	ROLE_ADMIN(Names.ROLE_ADMIN), ROLE_CLIENT(Names.ROLE_CLIENT);
	
	public class Names{
        public static final String ROLE_ADMIN = "ROLE_ADMIN";
        public static final String ROLE_CLIENT = "ROLE_CLIENT";
    }

    private final String label;

    private Role(String label) {
        this.label = label;
    }


	@Override
	public String getAuthority() {
		return name();
	}

}

