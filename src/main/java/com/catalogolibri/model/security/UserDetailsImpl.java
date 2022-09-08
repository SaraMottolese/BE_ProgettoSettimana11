package com.catalogolibri.model.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.catalogolibri.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
@Data
public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = -8990556584913973635L;
	private Long id;
	private String username;
	private String email;
	@JsonIgnore // indica che non deve essere riportata la password nel json
	private String password;
	private boolean isEnabled;
	private boolean accountNonLocked;
	private boolean accountNonExpired;
	private boolean credentialsNonExpired;
	private Collection<? extends GrantedAuthority> authorities;// quali diritti puo' avere l'utente che si logga 

	public UserDetailsImpl(Long id, String userName, String email, String password,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = userName;
		this.email = email;
		this.password = password;
		this.accountNonLocked = true;
		this.accountNonExpired = true;
		this.credentialsNonExpired = true;
		this.isEnabled = true;
		this.authorities = authorities;

	}

	public static UserDetailsImpl build(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getRoleName().name())).collect(Collectors.toList());
		return new UserDetailsImpl(user.getId(), user.getUserName(), user.getEmail(), user.getPassword(), authorities);
	}

	
}
