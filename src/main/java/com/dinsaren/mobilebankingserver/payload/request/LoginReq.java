package com.dinsaren.mobilebankingserver.payload.request;
public class LoginReq {
	private String phoneNumber;
	private String password;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LoginReq{" +
				"phoneNumber='" + phoneNumber + '\'' +
				", password='" + password + '\'' +
				'}';
	}
}
