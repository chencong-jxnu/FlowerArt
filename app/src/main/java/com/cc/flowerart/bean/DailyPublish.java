package com.cc.flowerart.bean;

/**
 * 每日一条发布，以html文件存储在云端，数据库存储文件路径，读取html文件由前台webview显示
 * 
 * @author Administrator
 *
 */
public class DailyPublish implements EntityBase {
	private String id;
	private String title;
	private String time;
	private String fileUrl;// 文件路径
	private String titleIcon;
	private String kind;// 每日一条的类型，有园艺、搭配、百科
	private int browseNum;
	private int collectionNum;
	private int praiseNum;

	public int getCollectionNum() {
		return collectionNum;
	}

	public void setCollectionNum(int collectionNum) {
		this.collectionNum = collectionNum;
	}

	public int getPraiseNum() {
		return praiseNum;
	}

	public void setPraiseNum(int praiseNum) {
		this.praiseNum = praiseNum;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTitleIcon() {
		return titleIcon;
	}

	public void setTitleIcon(String titleIcon) {
		this.titleIcon = titleIcon;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getBrowseNum() {
		return browseNum;
	}

	public void setBrowseNum(int browseNum) {
		this.browseNum = browseNum;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public DailyPublish(String id, String title, String time, String fileUrl, String titleIcon, String kind,
			int browseNum, int collectionNum, int praiseNum) {
		super();
		this.id = id;
		this.title = title;
		this.time = time;
		this.fileUrl = fileUrl;
		this.titleIcon = titleIcon;
		this.kind = kind;
		this.browseNum = browseNum;
		this.collectionNum = collectionNum;
		this.praiseNum = praiseNum;
	}

	public DailyPublish() {
		super();
	}

}
