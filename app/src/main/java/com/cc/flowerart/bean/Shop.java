package com.cc.flowerart.bean;

public class Shop implements EntityBase {
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Shop() {
		super();
	}

	public Shop(String id) {
		super();
		this.id = id;
	}
	
}
