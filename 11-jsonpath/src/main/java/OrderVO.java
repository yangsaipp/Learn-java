/******************************************************************************
* Copyright (C) 2014 ShenZhen ComTop Information Technology Co.,Ltd
* All Rights Reserved.
* 本软件为深圳康拓普开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
* 复制、修改或发布本软件.
*****************************************************************************/


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author  杨赛
 * @since   jdk1.7
 * @version 2018年5月30日 杨赛
 */
@Table(name = "b_order")
public class OrderVO {
	
	/** 订单id  */
	@Id
	private String orderId;
	
	/** 所属部门 */
	@Column(name = "org_code")
	private String orgCode;
	
	/** 销售人id */
	@Column(name = "sales_user_id")
	private String salesUserId;
	
	/**  成交时间 */
	@Column(name = "sales_time")
	private Timestamp salesTime;
	
	/** 客户信息 */
	@Column(name = "customer_info")
	private String customerInfo;
	
	/** 订单金额 */
	@Column(name = "amount")
	private Double amount;
	
	/**  创建时间 */
	@Column(name = "create_time")
	private Timestamp createTime;

	/**
	 * @return 获取 orderId属性值
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId 设置 orderId 属性值为参数值 orderId
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return 获取 orgCode属性值
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param orgCode 设置 orgCode 属性值为参数值 orgCode
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * @return 获取 salesUserId属性值
	 */
	public String getSalesUserId() {
		return salesUserId;
	}

	/**
	 * @param salesUserId 设置 salesUserId 属性值为参数值 salesUserId
	 */
	public void setSalesUserId(String salesUserId) {
		this.salesUserId = salesUserId;
	}

	/**
	 * @return 获取 salesTime属性值
	 */
	public Timestamp getSalesTime() {
		return salesTime;
	}

	/**
	 * @param salesTime 设置 salesTime 属性值为参数值 salesTime
	 */
	public void setSalesTime(Timestamp salesTime) {
		this.salesTime = salesTime;
	}

	/**
	 * @return 获取 customerInfo属性值
	 */
	public String getCustomerInfo() {
		return customerInfo;
	}

	/**
	 * @param customerInfo 设置 customerInfo 属性值为参数值 customerInfo
	 */
	public void setCustomerInfo(String customerInfo) {
		this.customerInfo = customerInfo;
	}

	/**
	 * @return 获取 amount属性值
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * @param amount 设置 amount 属性值为参数值 amount
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * @return 获取 createTime属性值
	 */
	public Timestamp getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime 设置 createTime 属性值为参数值 createTime
	 */
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
}
