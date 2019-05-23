package com.forum.demo.Entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "friend", schema = "forum", catalog = "")
public class FriendEntity {
    private String id;
    private String user;
    private String friends;
    private String operator;
    private Date creattime;
    private Date updatetime;
    private String remark;
    private CustomEntity customByUser;
    private CustomEntity customByFriends;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    @Column(name = "friends")
    public String getFriends() {
        return friends;
    }

    public void setFriends(String friends) {
        this.friends = friends;
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
        FriendEntity that = (FriendEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(user, that.user) &&
                Objects.equals(friends, that.friends) &&
                Objects.equals(operator, that.operator) &&
                Objects.equals(creattime, that.creattime) &&
                Objects.equals(updatetime, that.updatetime) &&
                Objects.equals(remark, that.remark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, friends, operator, creattime, updatetime, remark);
    }

    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "id", nullable = false,insertable = false, updatable = false)
    public CustomEntity getCustomByUser() {
        return customByUser;
    }

    public void setCustomByUser(CustomEntity customByUser) {
        this.customByUser = customByUser;
    }

    @ManyToOne
    @JoinColumn(name = "friends", referencedColumnName = "id", nullable = false,insertable = false, updatable = false)
    public CustomEntity getCustomByFriends() {
        return customByFriends;
    }

    public void setCustomByFriends(CustomEntity customByFriends) {
        this.customByFriends = customByFriends;
    }
}
