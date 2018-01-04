package com.cc.flowerart.bean;

import com.cc.flowerart.utils.DateUtil;

import java.util.Date;


public class Post implements EntityBase {
    private String id;
    private String content;
    private String time;
    private String pictureUrls;
    private String userId;
    private String kind;
    private int praiseNum;
    private int collectionNum;
    private int commentNum;

    public int getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(int praiseNum) {
        this.praiseNum = praiseNum;
    }

    public int getCollectionNum() {
        return collectionNum;
    }

    public void setCollectionNum(int collectionNum) {
        this.collectionNum = collectionNum;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPictureUrls() {
        return pictureUrls;
    }

    public void setPictureUrls(String pictureUrls) {
        this.pictureUrls = pictureUrls;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Post() {
        super();
        this.collectionNum = 0;
        this.praiseNum = 0;
        this.commentNum = 0;
    }

    public Post(String id, String content, String time, String pictureUrls, String userId, String kind, int praiseNum,
                int collectionNum, int commentNum) {
        super();
        this.id = id;
        this.content = content;
        this.time = DateUtil.getFormatDate(new Date());
        this.pictureUrls = pictureUrls;
        this.userId = userId;
        this.kind = kind;
        this.praiseNum = praiseNum;
        this.collectionNum = collectionNum;
        this.commentNum = commentNum;
    }

}
