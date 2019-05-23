package com.forum.demo.Entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "class_info", schema = "forum", catalog = "")
public class ClassInfoEntity {
    private String id;
    private String teacher;
    private String type;
    private String info;
    private String operator;
    private Date creattime;
    private Date updatetime;
    private String remark;
    private CustomEntity customByTeacher;
    private Collection<ClassMemberEntity> classMembersById;
    private Collection<ExamEntity> examsById;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "teacher")
    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
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
        ClassInfoEntity that = (ClassInfoEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(teacher, that.teacher) &&
                Objects.equals(type, that.type) &&
                Objects.equals(info, that.info) &&
                Objects.equals(operator, that.operator) &&
                Objects.equals(creattime, that.creattime) &&
                Objects.equals(updatetime, that.updatetime) &&
                Objects.equals(remark, that.remark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, teacher, type, info, operator, creattime, updatetime, remark);
    }

    @ManyToOne
    @JoinColumn(name = "teacher", referencedColumnName = "id", nullable = false,insertable = false, updatable = false)
    public CustomEntity getCustomByTeacher() {
        return customByTeacher;
    }

    public void setCustomByTeacher(CustomEntity customByTeacher) {
        this.customByTeacher = customByTeacher;
    }

    @OneToMany(mappedBy = "classInfoByClassid")
    public Collection<ClassMemberEntity> getClassMembersById() {
        return classMembersById;
    }

    public void setClassMembersById(Collection<ClassMemberEntity> classMembersById) {
        this.classMembersById = classMembersById;
    }

    @OneToMany(mappedBy = "classInfoByClassid")
    public Collection<ExamEntity> getExamsById() {
        return examsById;
    }

    public void setExamsById(Collection<ExamEntity> examsById) {
        this.examsById = examsById;
    }
}
