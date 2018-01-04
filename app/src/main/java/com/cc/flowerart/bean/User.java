package com.cc.flowerart.bean;

public class User implements EntityBase {
	private String id;
	private String password;
	private String nickname="小白";
	private String sex="保密";
	private String sign="学花艺，享生活";
	private String address="保密";
	private String birthday;// 1995-13-30
	private String iconUrl = "http://ot4o92kcv.bkt.clouddn.com/logo.png";
	private String job="保密";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}


	public User() {
		super();
	}

	public User(String id, String password) {
		super();
		this.id = id;
		this.password = password;
	}

	public User(String id, String password, String nickname, String sex, String sign, String address, String birthday,
			String iconUrl, String job) {
		super();
		this.id = id;
		this.password = password;
		this.nickname = nickname;
		this.sex = sex;
		this.sign = sign;
		this.address = address;
		this.birthday = birthday;
		this.iconUrl = iconUrl;
		this.job = job;
	}
}
