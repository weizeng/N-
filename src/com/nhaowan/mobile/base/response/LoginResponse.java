package com.nhaowan.mobile.base.response;

import com.leo.utils.Element;
import com.leo.widget.ListResult;

public class LoginResponse extends ListResult {
	@Element(value = "token")
	private String token;

	@Element(value = "expires_in")
	private String expiresIn;

	@Element(value = "msg")
	private String msg;

	@Element(value = "ret")
	private int ret;

	public LoginResponse() {
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getRet() {
		return ret;
	}

	public void setRet(int ret) {
		this.ret = ret;
	}

	public String getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}

	@Override
	public int getTotalPage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getDataSize4EachRequest() {
		return 0;
	}

}
