package com.dinsaren.mobilebankingserver.payload.response;

public class JwtRes {
	private String accessToken;
	private String tokenType = "Bearer";
	private String refreshToken;
	private int expiresIn;


	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	@Override
	public String toString() {
		return "JwtRes{" +
				"accessToken='" + accessToken + '\'' +
				", tokenType='" + tokenType + '\'' +
				", refreshToken='" + refreshToken + '\'' +
				", expiresIn=" + expiresIn +
				'}';
	}
}
