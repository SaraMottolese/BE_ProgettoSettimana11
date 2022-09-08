package com.catalogolibri.model.security;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponse {
	private final String type = "Bearer";
	private String token;
	private List<String> roles;

}


