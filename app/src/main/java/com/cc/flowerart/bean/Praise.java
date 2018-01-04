package com.cc.flowerart.bean;
/**
 * 点赞表
 * @author Administrator
 *
 */
public class Praise  implements EntityBase{
	private String id;
	private String userId;
	private String targetId;//被赞id
	private String time;//点赞时间
	private String kind;//被赞对象的类型：每日一条、用户
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
	public Praise(String id, String userId, String targetId, String time, String kind) {
		super();
		this.id = id;
		this.userId = userId;
		this.targetId = targetId;
		this.time = time;
		this.kind = kind;
	}
	public Praise() {
		super();
	}
	
}
