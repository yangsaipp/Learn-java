/******************************************************************************
 * Copyright (C) 2011 ShenZhen ComTop Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为深圳康拓普开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
 * 复制、修改或发布本软件.
 *****************************************************************************/

package com.comtop.tcm.tenantPlatform.bpmp.baseservice.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.comtop.bizp.dart.framework.orm.model.CoreVO;

/**
 * 
 * @author xietuojin
 * @since 1.0
 * @version 2021/5/18 Toking
 *
 */

@Entity
@Table(name = "BPMP_BASE_SERVICE")
public class BaseServiceInfoVO extends CoreVO {
    
    /** FIXME */
    private static final long serialVersionUID = 1L;

    /**
     * 服务ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BASE_SERVICE_ID")
    private String baseServiceId;
    
    /**
     * 基础服务名称
     */
    @Column(name = "BASE_SERVICE_NAME")
    private String baseServiceName;
    
    /**
     * 基础服务编码
     */
    @Column(name = "BASE_SERVICE_CODE")
    private String baseServiceCode;
    
    /**
     * 1 UNIT 2  BASE
     */
    @Column(name = "BASE_SERVICE_TYPE")
    private Integer baseServiceType;
    
    /**
     * 授权访问的token 第三方系统使用
     */
    @Column(name = "ACCESS_TOKEN")
    private String accessToken;
    
    /**
     * rest入口地址 http://127.0.0.1:9100/api
     */
    @Column(name = "VISIT_REST_URL")
    private String visitRestUrl;
    
    /**
     * 租户ID
     */
    @Column(name = "TENANT_ID")
    private String tenantId;
    
    /**
     * 创建人
     */
    @Column(name = "CREATOR_ID")
    private String creatorId;
    
    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;
    
    /**
     * 更新人
     */
    @Column(name = "MODIFIER_ID")
    private String modifierId;
    
    /**
     * 更新时间
     */
    @Column(name = "UPDATE_TIME")
    private Date updateTime;
    
    /**
     * 账号，jadp基础服务使用
     */
    @Column(name = "BASE_SERVICE_ACCOUNT")
    private String baseServiceAccount;
    
    /**
     * 密码，jadp基础服务使用
     */
    @Column(name = "BASE_SERVICE_PASSWORD")
    private String baseServicePassword;
    
    /**
     * 获取授权访问的地址 如/session，jadp基础服务使用
     */
    @Column(name = "GET_ACCESS_URL")
    private String getAccessUrl;
    
    /**
     * jadp基础服务使用，授权码
     */
    @Column(name = "BASE_SERVICE_AUTH_CODE")
    private String baseServiceAuthCode;
    
	/**
     * 1 jadp 2   第三方系统
     */
    @Column(name = "SOURCE_TYPE")
    private Integer sourceType;

	/**
	 * @return the baseServiceId
	 */
	public String getBaseServiceId() {
		return baseServiceId;
	}

	/**
	 * @param baseServiceId the baseServiceId to set
	 */
	public void setBaseServiceId(String baseServiceId) {
		this.baseServiceId = baseServiceId;
	}

	/**
	 * @return the baseServiceName
	 */
	public String getBaseServiceName() {
		return baseServiceName;
	}

	/**
	 * @param baseServiceName the baseServiceName to set
	 */
	public void setBaseServiceName(String baseServiceName) {
		this.baseServiceName = baseServiceName;
	}

	/**
	 * @return the baseServiceCode
	 */
	public String getBaseServiceCode() {
		return baseServiceCode;
	}

	/**
	 * @param baseServiceCode the baseServiceCode to set
	 */
	public void setBaseServiceCode(String baseServiceCode) {
		this.baseServiceCode = baseServiceCode;
	}

	/**
	 * @return the baseServiceType
	 */
	public Integer getBaseServiceType() {
		return baseServiceType;
	}

	/**
	 * @param baseServiceType the baseServiceType to set
	 */
	public void setBaseServiceType(Integer baseServiceType) {
		this.baseServiceType = baseServiceType;
	}

	/**
	 * @return the accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * @param accessToken the accessToken to set
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * @return the visitRestUrl
	 */
	public String getVisitRestUrl() {
		return visitRestUrl;
	}

	/**
	 * @param visitRestUrl the visitRestUrl to set
	 */
	public void setVisitRestUrl(String visitRestUrl) {
		this.visitRestUrl = visitRestUrl;
	}

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return tenantId;
	}

	/**
	 * @param tenantId the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return the creatorId
	 */
	public String getCreatorId() {
		return creatorId;
	}

	/**
	 * @param creatorId the creatorId to set
	 */
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the modifierId
	 */
	public String getModifierId() {
		return modifierId;
	}

	/**
	 * @param modifierId the modifierId to set
	 */
	public void setModifierId(String modifierId) {
		this.modifierId = modifierId;
	}

	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the baseServiceAccount
	 */
	public String getBaseServiceAccount() {
		return baseServiceAccount;
	}

	/**
	 * @param baseServiceAccount the baseServiceAccount to set
	 */
	public void setBaseServiceAccount(String baseServiceAccount) {
		this.baseServiceAccount = baseServiceAccount;
	}

	/**
	 * @return the baseServicePassword
	 */
	public String getBaseServicePassword() {
		return baseServicePassword;
	}

	/**
	 * @param baseServicePassword the baseServicePassword to set
	 */
	public void setBaseServicePassword(String baseServicePassword) {
		this.baseServicePassword = baseServicePassword;
	}

	/**
	 * @return the getAccessUrl
	 */
	public String getGetAccessUrl() {
		return getAccessUrl;
	}

	/**
	 * @param getAccessUrl the getAccessUrl to set
	 */
	public void setGetAccessUrl(String getAccessUrl) {
		this.getAccessUrl = getAccessUrl;
	}

	/**
	 * @return the baseServiceAuthCode
	 */
	public String getBaseServiceAuthCode() {
		return baseServiceAuthCode;
	}

	/**
	 * @param baseServiceAuthCode the baseServiceAuthCode to set
	 */
	public void setBaseServiceAuthCode(String baseServiceAuthCode) {
		this.baseServiceAuthCode = baseServiceAuthCode;
	}

	/**
	 * @return the sourceType
	 */
	public Integer getSourceType() {
		return sourceType;
	}

	/**
	 * @param sourceType the sourceType to set
	 */
	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}
    
}
