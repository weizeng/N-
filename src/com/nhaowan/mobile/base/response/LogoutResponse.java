package com.nhaowan.mobile.base.response;

import com.leo.utils.Element;
import com.leo.widget.ListResult;

public class LogoutResponse extends  ListResult{
	@Element(value = "ret")
	private int ret;

	@Element(value = "msg")
	private String msg;

	public int getRet() {
		return ret;
	}

	public void setRet(int ret) {
		this.ret = ret;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
