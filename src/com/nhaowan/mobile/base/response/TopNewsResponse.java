package com.nhaowan.mobile.base.response;

import java.util.ArrayList;

import com.haha.frame.utils.Element;
import com.haha.frame.utils.ElementList;
import com.haha.frame.widget.ListResult;
import com.nhaowan.mobile.base.bean.NewsBean;
import com.nhaowan.mobile.base.bean.TopBean;

public class TopNewsResponse extends ListResult {
	@Element(value = "ret")
	private int ret;

	@Element(value = "msg")
	private String msg;

	@ElementList(value = "list", checkSuper = true)
	private ArrayList<TopBean> list = new ArrayList<TopBean>();
//	{"ret":0,"msg":"","list":[{"id":251,"title":"\u5e03\u62c9\u5fb7\u91ce\u86ee\u4eba","thumb":"http:\/\/www.nhaowan.com\/uploadfile\/2014\/0509\/20140509052429712.jpeg","catid":32,"create_time":null},{"id":247,"title":"\u5408\u91d1\u5f39\u5934\u5854\u9632","thumb":"http:\/\/www.nhaowan.com\/uploadfile\/2014\/0508\/20140508122927250.jpeg","catid":34,"create_time":null},{"id":249,"title":"\u5c0f\u5c0f\u5b88\u95e8\u5458","thumb":"http:\/\/www.nhaowan.com\/uploadfile\/2014\/0508\/20140508095512400.png","catid":29,"create_time":null},{"id":244,"title":"\u4e00\u8d77\u6d88\u6d88\u6bd2","thumb":"http:\/\/www.nhaowan.com\/uploadfile\/2014\/0507\/20140507075642849.jpg","catid":35,"create_time":null},{"id":245,"title":"Retry","thumb":"http:\/\/www.nhaowan.com\/uploadfile\/2014\/0507\/20140507104852989.jpeg","catid":35,"create_time":null}]}
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

	@Override
	public int getDataSize4EachRequest() {
		return list.size();
	}

	public ArrayList<TopBean> getList() {
		return list;
	}

	public void setList(ArrayList<TopBean> list) {
		this.list = list;
	}

}
