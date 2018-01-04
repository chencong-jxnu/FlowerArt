package com.cc.flowerart.bean;

public class Goods implements EntityBase{
	private String id;
	private String goodName;
	private String content;
	private float price;
	private String kind;
	private String pictureUrls;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGoodName() {
		return goodName;
	}
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getPictureUrls() {
		return pictureUrls;
	}
	public void setPictureUrls(String pictureUrls) {
		this.pictureUrls = pictureUrls;
	}
	public Goods(String id, String goodName, String content, float price, String kind, String pictureUrls) {
		super();
		this.id = id;
		this.goodName = goodName;
		this.content = content;
		this.price = price;
		this.kind = kind;
		this.pictureUrls = pictureUrls;
	}
	public Goods() {
		super();
	}
	

}
