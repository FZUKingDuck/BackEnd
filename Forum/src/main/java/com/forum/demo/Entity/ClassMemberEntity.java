package com.forum.demo.Entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "class_member", schema = "forum", catalog = "")
public class ClassMemberEntity {
    private String id;
    private String classid;
    private String user;
    private int status;
    private String operator;
    private Date creattime;
    private Date updatetime;
    private String remark;
    private ClassInfoEntity classInfoByClassid;
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
    @Column(name = "classid")
    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
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
    @Column(name = "status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
        ClassMemberEntity that = (ClassMemberEntity) o;
        return status == that.status &&
                Objects.equals(id, that.id) &&
                Objects.equals(classid, that.classid) &&
                Objects.equals(user, that.user) &&
                Objects.equals(operator, that.operator) &&
                Objects.equals(creattime, that.creattime) &&
                Objects.equals(updatetime, that.updatetime) &&
                Objects.equals(remark, that.remark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, classid, user, status, operator, creattime, updatetime, remark);
    }

    @ManyToOne
    @JoinColumn(name = "classid", referencedColumnName = "id", nullable = false,insertable = false, updatable = false)
    public ClassInfoEntity getClassInfoByClassid() {
        return classInfoByClassid;
    }

    public void setClassInfoByClassid(ClassInfoEntity classInfoByClassid) {
        this.classInfoByClassid = classInfoByClassid;
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
