package com.cc.flowerart.bean;

/**
 * 收藏表
 * 
 * @author Administrator
 *
 */
public class Collection implements EntityBase {
	private String id;
	private String userId;
	private String targetId;// 被收藏id
	private String time;// 收藏时间
	private String kind;// 被收藏对象的类型：每日一条、用户

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

	public Collection(String id, String userId, String targetId, String time, String kind) {
		super();
		this.id = id;
		this.userId = userId;
		this.targetId = targetId;
		this.time = time;
		this.kind = kind;
	}

	public Collection() {
		super();
	}

}
