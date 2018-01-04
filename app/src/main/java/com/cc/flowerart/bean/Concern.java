package com.cc.flowerart.bean;
/**
 * 关注表
 * @author Administrator
 *
 */
public class Concern  implements EntityBase{
	private String id;
	private String userId;
	private String targetId;
	private String time;
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
	public Concern(String id, String userId, String targetId, String time) {
		super();
		this.id = id;
		this.userId = userId;
		this.targetId = targetId;
		this.time = time;
	}
	public Concern() {
		super();
	}
	
}
