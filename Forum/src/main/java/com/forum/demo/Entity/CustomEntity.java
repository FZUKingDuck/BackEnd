package com.forum.demo.Entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "custom", schema = "forum")
public class CustomEntity {
    private String id;
    private String name;
    private String password;
    private String operator;
    private Date creattime;
    private Date updatetime;
    private String remark;
    private Collection<ClassInfoEntity> classInfosById;
    private Collection<ClassMemberEntity> classMembersById;
    private Collection<ExamInfoEntity> examInfosById;
    private Collection<FriendEntity> friendsById;
    private Collection<FriendEntity> friendsById_0;
    private Collection<ReplyEntity> repliesById;
    private Collection<TalkEntity> talksById;
    private Collection<TalkEntity> talksById_0;
    private Collection<TaskEntity> tasksById;
    private Collection<TaskListEntity> taskListsById;
    private Collection<UserInfoEntity> userInfosById;
    private Collection<WatchedEntity> watchedsById;
    private Collection<WatchedEntity> watchedsById_0;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        CustomEntity that = (CustomEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(password, that.password) &&
                Objects.equals(operator, that.operator) &&
                Objects.equals(creattime, that.creattime) &&
                Objects.equals(updatetime, that.updatetime) &&
                Objects.equals(remark, that.remark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, operator, creattime, updatetime, remark);
    }

    @OneToMany(mappedBy = "customByTeacher")
    public Collection<ClassInfoEntity> getClassInfosById() {
        return classInfosById;
    }

    public void setClassInfosById(Collection<ClassInfoEntity> classInfosById) {
        this.classInfosById = classInfosById;
    }

    @OneToMany(mappedBy = "customByUser")
    public Collection<ClassMemberEntity> getClassMembersById() {
        return classMembersById;
    }

    public void setClassMembersById(Collection<ClassMemberEntity> classMembersById) {
        this.classMembersById = classMembersById;
    }

    @OneToMany(mappedBy = "customByUser")
    public Collection<ExamInfoEntity> getExamInfosById() {
        return examInfosById;
    }

    public void setExamInfosById(Collection<ExamInfoEntity> examInfosById) {
        this.examInfosById = examInfosById;
    }

    @OneToMany(mappedBy = "customByUser")
    public Collection<FriendEntity> getFriendsById() {
        return friendsById;
    }

    public void setFriendsById(Collection<FriendEntity> friendsById) {
        this.friendsById = friendsById;
    }

    @OneToMany(mappedBy = "customByFriends")
    public Collection<FriendEntity> getFriendsById_0() {
        return friendsById_0;
    }

    public void setFriendsById_0(Collection<FriendEntity> friendsById_0) {
        this.friendsById_0 = friendsById_0;
    }

    @OneToMany(mappedBy = "customByUser")
    public Collection<ReplyEntity> getRepliesById() {
        return repliesById;
    }

    public void setRepliesById(Collection<ReplyEntity> repliesById) {
        this.repliesById = repliesById;
    }

    @OneToMany(mappedBy = "customByUser")
    public Collection<TalkEntity> getTalksById() {
        return talksById;
    }

    public void setTalksById(Collection<TalkEntity> talksById) {
        this.talksById = talksById;
    }

    @OneToMany(mappedBy = "customByToUserid")
    public Collection<TalkEntity> getTalksById_0() {
        return talksById_0;
    }

    public void setTalksById_0(Collection<TalkEntity> talksById_0) {
        this.talksById_0 = talksById_0;
    }

    @OneToMany(mappedBy = "customByUser")
    public Collection<TaskEntity> getTasksById() {
        return tasksById;
    }

    public void setTasksById(Collection<TaskEntity> tasksById) {
        this.tasksById = tasksById;
    }

    @OneToMany(mappedBy = "customByUser")
    public Collection<TaskListEntity> getTaskListsById() {
        return taskListsById;
    }

    public void setTaskListsById(Collection<TaskListEntity> taskListsById) {
        this.taskListsById = taskListsById;
    }

    @OneToMany(mappedBy = "customByCustomid")
    public Collection<UserInfoEntity> getUserInfosById() {
        return userInfosById;
    }

    public void setUserInfosById(Collection<UserInfoEntity> userInfosById) {
        this.userInfosById = userInfosById;
    }

    @OneToMany(mappedBy = "customByUser")
    public Collection<WatchedEntity> getWatchedsById() {
        return watchedsById;
    }

    public void setWatchedsById(Collection<WatchedEntity> watchedsById) {
        this.watchedsById = watchedsById;
    }

    @OneToMany(mappedBy = "customByWatchedUser")
    public Collection<WatchedEntity> getWatchedsById_0() {
        return watchedsById_0;
    }

    public void setWatchedsById_0(Collection<WatchedEntity> watchedsById_0) {
        this.watchedsById_0 = watchedsById_0;
    }
}
