package com.miaoying.generator.modulardb.personal.model;

import javax.persistence.*;
import java.util.*;
import com.comtop.bizp.dart.framework.orm.model.CoreVO;

/**
 * @auther CodeGenerator
 * @create 2021-05-30 23:59:04
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

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getModifierId() {
        return modifierId;
    }

    public void setModifierId(String modifierId) {
        this.modifierId = modifierId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


    @Override
    public String toString() {
        return "UserVO{" +
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