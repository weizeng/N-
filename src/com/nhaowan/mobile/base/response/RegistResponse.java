package com.nhaowan.mobile.base.response;

import com.haha.frame.utils.Element;
import com.haha.frame.widget.ListResult;

public class RegistResponse extends ListResult {
	@Element(value = "msg")
	private String msg;

	@Element(value = "ret")
	private int ret;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getRet() {
		return ret;
	}

	public void setRet(int ret) {
		this.ret = ret;
	}
}
