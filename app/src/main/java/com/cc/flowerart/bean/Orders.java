package com.cc.flowerart.bean;

import com.cc.flowerart.utils.DateUtil;

import java.util.Date;


public class Orders implements EntityBase {
	private String id;
	private String userId;
	private String time;
	private float realMoney;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public float getRealMoney() {
		return realMoney;
	}
	public void setRealMoney(float realMoney) {
		this.realMoney = realMoney;
	}
	public Orders(String id, String userId, float realMoney) {
		super();
		this.id = id;
		this.userId = userId;
		this.time = DateUtil.getFormatDate(new Date());
		this.realMoney = realMoney;
	}
	public Orders() {
		super();
	}

	
}	
