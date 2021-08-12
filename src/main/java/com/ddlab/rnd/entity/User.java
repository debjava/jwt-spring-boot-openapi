package com.ddlab.rnd.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.userdetails.UserDetails;

import com.ddlab.rnd.role.Role;

import lombok.Data;

@Data
@Document(collection = "users")
public class User implements UserDetails, Serializable {

	private static final long serialVersionUID = -3887138622462420584L;

	@Id
	private ObjectId id;

	@Indexed(unique = true)
	private String username;

	private String password;

	private boolean enabled = true;

	private Set<Role> authorities = new HashSet<>();

	@Override
	public boolean isAccountNonExpired() {
		return enabled;
	}

	@Override
	public boolean isAccountNonLocked() {
		return enabled;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return enabled;
	}

}
