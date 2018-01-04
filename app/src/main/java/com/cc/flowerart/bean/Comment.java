package com.cc.flowerart.bean;

public class Comment implements EntityBase{
	private String id;
	private String userId;
	private String targetId;//被评论对象id
	private String time;//评论时间
	private String kind;//被评论对象的类型：每日一条、帖子
	private String content;//评论内容
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
	public String getTargetId() {
		return targetId;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Comment(String id, String userId, String targetId, String time, String kind, String content) {
		super();
		this.id = id;
		this.userId = userId;
		this.targetId = targetId;
		this.time = time;
		this.kind = kind;
		this.content = content;
	}
	public Comment() {
		super();
	}
	
}
