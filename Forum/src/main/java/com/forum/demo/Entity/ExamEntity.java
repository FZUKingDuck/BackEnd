package com.forum.demo.Entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "exam", schema = "forum", catalog = "")
public class ExamEntity {
    private String id;
    private String classid;
    private String title;
    private String info;
    private Date endtime;
    private String operator;
    private Date creattime;
    private Date updatetime;
    private String remark;
    private ClassInfoEntity classInfoByClassid;
    private Collection<ExamInfoEntity> examInfosById;

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
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
    @Column(name = "endtime")
    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
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
        ExamEntity that = (ExamEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(classid, that.classid) &&
                Objects.equals(title, that.title) &&
                Objects.equals(info, that.info) &&
                Objects.equals(endtime, that.endtime) &&
                Objects.equals(operator, that.operator) &&
                Objects.equals(creattime, that.creattime) &&
                Objects.equals(updatetime, that.updatetime) &&
                Objects.equals(remark, that.remark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, classid, title, info, endtime, operator, creattime, updatetime, remark);
    }

    @ManyToOne
    @JoinColumn(name = "classid", referencedColumnName = "id", nullable = false,insertable = false, updatable = false)
    public ClassInfoEntity getClassInfoByClassid() {
        return classInfoByClassid;
    }

    public void setClassInfoByClassid(ClassInfoEntity classInfoByClassid) {
        this.classInfoByClassid = classInfoByClassid;
    }

    @OneToMany(mappedBy = "examByExamid")
    public Collection<ExamInfoEntity> getExamInfosById() {
        return examInfosById;
    }

    public void setExamInfosById(Collection<ExamInfoEntity> examInfosById) {
        this.examInfosById = examInfosById;
    }
}
