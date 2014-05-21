package com.nhaowan.mobile.base.response;

import java.util.ArrayList;

import com.leo.utils.Element;
import com.leo.widget.ListResult;
import com.nhaowan.mobile.base.bean.ZanBean;

public class ZanListResponse extends ListResult {
	
	@Element(value = "list" , parseSuper = true)
	private ArrayList<ZanBean> list = new ArrayList();
	
	@Element(value = "msg")
	private String msg;
	
	@Element(value = "page_count")
	private int page_count;
	
	@Element(value = "ret")
	private int ret;

	public ArrayList<ZanBean> getEntities() {
		return this.list;
	}

	public String getMsg() {
		return this.msg;
	}

	public int getPage_count() {
		return this.page_count;
	}

	public int getRet() {
		return this.ret;
	}

	public void setEntities(ArrayList<ZanBean> paramArrayList) {
		this.list = paramArrayList;
	}

	public void setMsg(String paramString) {
		this.msg = paramString;
	}

	public void setPage_count(int paramInt) {
		this.page_count = paramInt;
	}

	public void setRet(int paramInt) {
		this.ret = paramInt;
	}

}
