package com.spring.boot.keycloak.app.utils;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class HttpParamsUtil {

	private final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

	public static HttpParamsUtil builder() {
		return new HttpParamsUtil();
	}

	public HttpParamsUtil HttpParamsClientId(String clientId) {
		params.add("client_id", clientId);
		return this;
	}

	public HttpParamsUtil HttpParamsClientSecret(String clientSecret) {
		params.add("client_secret", clientSecret);
		return this;
	}
	
	public HttpParamsUtil HttpParamsGrantType(String grantType) {
		params.add("grant_type", grantType);
		return this;
	}

	public HttpParamsUtil HttpParamsUsername(String userName) {
		params.add("username", userName);
		return this;
	}

	public HttpParamsUtil HttpParamsPassword(String password) {
		params.add("password", password);
		return this;
	}

	public HttpParamsUtil HttpParamsTokenRefresh(String tokenRefresh) {
		params.add("refresh_token", tokenRefresh);
		return this;
	}

	public MultiValueMap<String, String> build() {
		return params;
	}
}
