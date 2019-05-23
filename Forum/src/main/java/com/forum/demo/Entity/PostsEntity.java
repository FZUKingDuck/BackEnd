package com.forum.demo.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "posts", schema = "forum")
public class PostsEntity {
    private String id;
    private String userid;
    private String title;
    private String type;
    private String info;
    private String authority;
    private String operator;
    private Date creattime;
    private Date updatetime;
    private String remark;
    private UserInfoEntity userInfoByUserid;
    private Collection<ReplyEntity> repliesById;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "userid")
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "info")
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Basic
    @Column(name = "authority")
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Basic
    @Column(name = "operator")
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Basic
    @Column(name = "creattime")
    public Date getCreattime() {
        return creattime;
    }

    public void setCreattime(Date creattime) {
        this.creattime = creattime;
    }

    @Basic
    @Column(name = "updatetime")
    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    @Basic
    @Column(name = "Remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostsEntity that = (PostsEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userid, that.userid) &&
                Objects.equals(title, that.title) &&
                Objects.equals(type, that.type) &&
                Objects.equals(info, that.info) &&
                Objects.equals(authority, that.authority) &&
                Objects.equals(operator, that.operator) &&
                Objects.equals(creattime, that.creattime) &&
                Objects.equals(updatetime, that.updatetime) &&
                Objects.equals(remark, that.remark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userid, title, type, info, authority, operator, creattime, updatetime, remark);
    }

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "userid", referencedColumnName = "id", nullable = false,insertable = false, updatable = false)
    public UserInfoEntity getUserInfoByUserid() {
        return userInfoByUserid;
    }

    public void setUserInfoByUserid(UserInfoEntity userInfoByUserid) {
        this.userInfoByUserid = userInfoByUserid;
    }

    @OneToMany(mappedBy = "postsByPostsid")
    public Collection<ReplyEntity> getRepliesById() {
        return repliesById;
    }

    public void setRepliesById(Collection<ReplyEntity> repliesById) {
        this.repliesById = repliesById;
    }
}
