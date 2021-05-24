package com.miaoying.generator.modulardb.personal.entity;

import javax.persistence.*;
import java.util.*;
import com.comtop.bizp.dart.framework.orm.model.CoreVO;

/**
 * @auther CodeGenerator
 * @create 2021-05-23 10:54:26
 * @describe 人员和组织的关联对象表实体类
 */
 
@Entity
@Table(name = "top_user")
public class UserVO extends CoreVO {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private String userId;

    /**
     * 组织ID
     */
    @Column(name = "org_id")
    private String orgId;

    /**
     * 人员ID
     */
    @Column(name = "employee_id")
    private String employeeId;

    /**
     * 状态。1任职，2免职
     */
    @Column(name = "state")
    private Integer state;

    /**
     * 用户的类型,0-本单位用户;1-外协单位用户(最小系统使用)
     */
    @Column(name = "user_type")
    private Integer userType;

    /**
     * 创建人ID
     */
    @Column(name = "creator_id")
    private String creatorId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新者ID
     */
    @Column(name = "modifier_id")
    private String modifierId;

    /**
     * 记录更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;



    public String getUserId() {
        return userId;
    }

    public User setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getOrgId() {
        return orgId;
    }

    public User setOrgId(String orgId) {
        this.orgId = orgId;
        return this;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public User setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    public Integer getState() {
        return state;
    }

    public User setState(Integer state) {
        this.state = state;
        return this;
    }

    public Integer getUserType() {
        return userType;
    }

    public User setUserType(Integer userType) {
        this.userType = userType;
        return this;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public User setCreatorId(String creatorId) {
        this.creatorId = creatorId;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public User setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getModifierId() {
        return modifierId;
    }

    public User setModifierId(String modifierId) {
        this.modifierId = modifierId;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public User setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }


    @Override
    public String toString() {
        return "User{" +
        "userId=" + userId +
        ", orgId=" + orgId +
        ", employeeId=" + employeeId +
        ", state=" + state +
        ", userType=" + userType +
        ", creatorId=" + creatorId +
        ", createTime=" + createTime +
        ", modifierId=" + modifierId +
        ", updateTime=" + updateTime +
        "}";
    }
}