package com.forum.demo.Entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "exam_info", schema = "forum", catalog = "")
public class ExamInfoEntity {
    private String id;
    private String examid;
    private String user;
    private int score;
    private String operator;
    private Date creattime;
    private Date updatetime;
    private String remark;
    private ExamEntity examByExamid;
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
    @Column(name = "examid")
    public String getExamid() {
        return examid;
    }

    public void setExamid(String examid) {
        this.examid = examid;
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
    @Column(name = "score")
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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
        ExamInfoEntity that = (ExamInfoEntity) o;
        return score == that.score &&
                Objects.equals(id, that.id) &&
                Objects.equals(examid, that.examid) &&
                Objects.equals(user, that.user) &&
                Objects.equals(operator, that.operator) &&
                Objects.equals(creattime, that.creattime) &&
                Objects.equals(updatetime, that.updatetime) &&
                Objects.equals(remark, that.remark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, examid, user, score, operator, creattime, updatetime, remark);
    }

    @ManyToOne
    @JoinColumn(name = "examid", referencedColumnName = "id", nullable = false,insertable = false, updatable = false)
    public ExamEntity getExamByExamid() {
        return examByExamid;
    }

    public void setExamByExamid(ExamEntity examByExamid) {
        this.examByExamid = examByExamid;
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
