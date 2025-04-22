package com.spring.boot.keycloak.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenDTO {

//	private String access_token;
//	private String expires_in;
//	private String refresh_expires_in;
	private String refresh_token;
//	private String token_type;
//	private String session_state;
//	private String scope;
}
