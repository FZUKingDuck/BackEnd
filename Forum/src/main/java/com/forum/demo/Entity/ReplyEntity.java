package com.forum.demo.Entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "reply", schema = "forum", catalog = "")
public class ReplyEntity {
    private String id;
    private String postsid;
    private String user;
    private String info;
    private int top;
    private String operator;
    private Date creattime;
    private Date updatetime;
    private String remark;
    private PostsEntity postsByPostsid;
    private CustomEntity customByUser;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "postsid")
    public String getPostsid() {
        return postsid;
    }

    public void setPostsid(String postsid) {
        this.postsid = postsid;
    }

    @Basic
    @Column(name = "user")
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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
    @Column(name = "top")
    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
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
        ReplyEntity that = (ReplyEntity) o;
        return top == that.top &&
                Objects.equals(id, that.id) &&
                Objects.equals(postsid, that.postsid) &&
                Objects.equals(user, that.user) &&
                Objects.equals(info, that.info) &&
                Objects.equals(operator, that.operator) &&
                Objects.equals(creattime, that.creattime) &&
                Objects.equals(updatetime, that.updatetime) &&
                Objects.equals(remark, that.remark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, postsid, user, info, top, operator, creattime, updatetime, remark);
    }

    @ManyToOne
    @JoinColumn(name = "postsid", referencedColumnName = "id", nullable = false,insertable = false, updatable = false)
    public PostsEntity getPostsByPostsid() {
        return postsByPostsid;
    }

    public void setPostsByPostsid(PostsEntity postsByPostsid) {
        this.postsByPostsid = postsByPostsid;
    }

    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "id", nullable = false,insertable = false, updatable = false)
    public CustomEntity getCustomByUser() {
        return customByUser;
    }

    public void setCustomByUser(CustomEntity customByUser) {
        this.customByUser = customByUser;
    }
}
