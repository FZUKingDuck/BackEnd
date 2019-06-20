package com.forum.demo.Entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "favorite", schema = "forum", catalog = "")
public class FavoriteEntity {
    private String id;
    private String userid;
    private String postslist;
    private String operator;
    private Timestamp createtime;
    private Timestamp updatetime;
    private String remark;

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
    @Column(name = "postslist")
    public String getPostslist() {
        return postslist;
    }

    public void setPostslist(String postslist) {
        this.postslist = postslist;
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
    @Column(name = "createtime")
    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    @Basic
    @Column(name = "updatetime")
    public Timestamp getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Timestamp updatetime) {
        this.updatetime = updatetime;
    }

    @Basic
    @Column(name = "remark")
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
        FavoriteEntity that = (FavoriteEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userid, that.userid) &&
                Objects.equals(postslist, that.postslist) &&
                Objects.equals(operator, that.operator) &&
                Objects.equals(createtime, that.createtime) &&
                Objects.equals(updatetime, that.updatetime) &&
                Objects.equals(remark, that.remark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userid, postslist, operator, createtime, updatetime, remark);
    }
}
