package com.nhaowan.mobile.base.bean;

import java.io.File;
import java.io.Serializable;

import com.leo.utils.FileSerializable;
import com.nhaowan.mobile.base.utils.Contants;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String city = "外太空";
	private String name = "玩家追梦";
	private String headUrl;
	private String province;
	private String birthday;
	private String token;
	private SEX sex;
	private String platform;// MediaType.QQWEIBO.toString()
							// MediaType.SINAWEIBO.toString()

	public static enum SEX {
		MAN, WOMEN, UNKNOWN
	}

	private static User user = null;

	public User() {
	}

	public static User getInstance() {
		if (user == null) {
			Object obj = null;
			try {
				obj = FileSerializable.readFromLocal(Contants.SERIAL_USER_FILE);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (obj != null) {
				user = (User) obj;
			}
			if (user == null) {
				user = new User();
			}
		}
		return user;
	}

	public User(String id, String city, String name, String headUrl, String province, String birthday,
			SEX sex, String platform) {
		this.id = id;
		this.city = city;
		this.name = name;
		this.headUrl = headUrl;
		this.province = province;
		this.birthday = birthday;
		this.sex = sex;
		this.platform = platform;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public SEX getSex() {
		return sex;
	}

	public void setSex(SEX sex) {
		this.sex = sex;
	}

	public void clear() {
		new File(Contants.SERIAL_USER_FILE).delete();
		token = null;
		user = null;
	}

	public void refresh() {
		user = null;
		getInstance();
	}

}
