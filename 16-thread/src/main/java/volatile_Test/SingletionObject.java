/******************************************************************************
* Copyright (C) 2014 ShenZhen ComTop Information Technology Co.,Ltd
* All Rights Reserved.
* 本软件为深圳康拓普开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
* 复制、修改或发布本软件.
*****************************************************************************/

package volatile_Test;

import java.util.Properties;

/**
 * 单例对象
 * @author  杨赛
 * @since   jdk1.7
 * @version 2018年12月12日 杨赛
 */
public class SingletionObject {
	
	/** 默认连接信息 */
	private final static Properties DEFAULT_CONNECT_PROPERTIES = new Properties();
	
	static {
		// 解决remarks为空的问题
		DEFAULT_CONNECT_PROPERTIES.setProperty("remarks","true");
		DEFAULT_CONNECT_PROPERTIES.setProperty("read-only", "true");
	}
	
	/**
	 * 数据源标识id
	 */
	private String dataSourceId;
	
	
	/** 数据源信息*/
	private String datasourceInfo;
	
	 /**  */
    private String url;
    
    /**  */
    private String uName;
    
    /**  */
    private String upwd;
    
    /**  */
    private String driverClass;
    
    /** 连接池连接属性 */
    private Properties connectProperties = (Properties) DEFAULT_CONNECT_PROPERTIES.clone();

    private Boolean flag = false;
	/**
	 * 构造函数
	 */
	public SingletionObject(){

	}



	/**
	 * 构造函数
	 * @param dataSourceId
	 */
	public SingletionObject(String dataSourceId) {
		this.dataSourceId = dataSourceId;
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}



	/**
	 * @return 获取 datasourceInfo属性值
	 */
	public String getDatasourceInfo() {
		return datasourceInfo;
	}

	/**
	 * @param datasourceInfo 设置 datasourceInfo 属性值为参数值 datasourceInfo
	 */
	public void setDatasourceInfo(String datasourceInfo) {
		this.datasourceInfo = datasourceInfo;
	}

	/**
	 * @return 获取 dataSourceId属性值
	 */
	public String getDataSourceId() {
		return dataSourceId;
	}

	/**
	 * @param dataSourceId 设置 dataSourceId 属性值为参数值 dataSourceId
	 */
	public void setDataSourceId(String dataSourceId) {
		this.dataSourceId = dataSourceId;
	}

	/**
	 * @return 获取 url属性值
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url 设置 url 属性值为参数值 url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return 获取 uName属性值
	 */
	public String getuName() {
		return uName;
	}

	/**
	 * @param uName 设置 uName 属性值为参数值 uName
	 */
	public void setuName(String uName) {
		this.uName = uName;
	}

	/**
	 * @return 获取 upwd属性值
	 */
	public String getUpwd() {
		return upwd;
	}

	/**
	 * @param upwd 设置 upwd 属性值为参数值 upwd
	 */
	public void setUpwd(String upwd) {
		this.upwd = upwd;
	}

	/**
	 * @return 获取 driverClass属性值
	 */
	public String getDriverClass() {
		return driverClass;
	}

	/**
	 * @param driverClass 设置 driverClass 属性值为参数值 driverClass
	 */
	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	/**
	 * @return 获取 connectProperties属性值
	 */
	public Properties getConnectProperties() {
		return connectProperties;
	}

	/**
	 * @param connectProperties 设置 connectProperties 属性值为参数值 connectProperties
	 */
	public void setConnectProperties(Properties connectProperties) {
		this.connectProperties = connectProperties;
	}



	/**
	 * @return 获取 flag属性值
	 */
	public Boolean getFlag() {
		return flag;
	}



	/**
	 * @param flag 设置 flag 属性值为参数值 flag
	 */
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
}