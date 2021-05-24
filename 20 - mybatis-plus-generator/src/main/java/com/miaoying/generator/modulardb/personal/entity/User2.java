package com.miaoying.generator.modulardb.personal.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @auther CodeGenerator
 * @create 2021-05-22 23:55:31
 * @describe 人员和组织的关联对象表实体类
 */
@TableName("top_user")
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value="User对象", description="人员和组织的关联对象表")
public class User2 implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    @TableId(value = "user_id", type = IdType.UUID)
    private String userId;

    @ApiModelProperty(value = "组织ID")
    @TableField("org_id")
    private String orgId;

    @ApiModelProperty(value = "人员ID")
    @TableField("employee_id")
    private String employeeId;

    @ApiModelProperty(value = "状态。1任职，2免职")
    @TableField("state")
    private Integer state;

    @ApiModelProperty(value = "用户的类型,0-本单位用户;1-外协单位用户(最小系统使用)")
    @TableField("user_type")
    private Integer userType;

    @ApiModelProperty(value = "创建人ID")
    @TableField("creator_id")
    private String creatorId;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "更新者ID")
    @TableField("modifier_id")
    private String modifierId;

    @ApiModelProperty(value = "记录更新时间")
    @TableField("update_time")
    private Date updateTime;


    public String getUserId() {
        return userId;
    }

    public User2 setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getOrgId() {
        return orgId;
    }

    public User2 setOrgId(String orgId) {
        this.orgId = orgId;
        return this;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public User2 setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    public Integer getState() {
        return state;
    }

    public User2 setState(Integer state) {
        this.state = state;
        return this;
    }

    public Integer getUserType() {
        return userType;
    }

    public User2 setUserType(Integer userType) {
        this.userType = userType;
        return this;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public User2 setCreatorId(String creatorId) {
        this.creatorId = creatorId;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public User2 setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getModifierId() {
        return modifierId;
    }

    public User2 setModifierId(String modifierId) {
        this.modifierId = modifierId;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public User2 setUpdateTime(Date updateTime) {
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