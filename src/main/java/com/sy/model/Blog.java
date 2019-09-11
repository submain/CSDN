package com.sy.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Blog {
    private Integer id;

    private Integer userid;

    private String title;

    private String content;

    private Integer status;

    private Integer readCount;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private Date createtime;

    private Integer recomment;

    private String resource;

    private String lable;

    private String publishForm;

    private String category;
    private String type;

    private String img;
    //数据库不用此字段做逻辑用
    private String username;

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", userid=" + userid +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", readCount=" + readCount +
                ", createtime=" + createtime +
                ", recomment=" + recomment +
                ", resource='" + resource + '\'' +
                ", lable='" + lable + '\'' +
                ", publishForm='" + publishForm + '\'' +
                ", category='" + category + '\'' +
                ", type='" + type + '\'' +
                ", img='" + img + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getRecomment() {
        return recomment;
    }

    public void setRecomment(Integer recomment) {
        this.recomment = recomment;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public String getPublishForm() {
        return publishForm;
    }

    public void setPublishForm(String publishForm) {
        this.publishForm = publishForm;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}