/******************************************************************************
 * Copyright (C) 2017 ShenZhen ComTop Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为深圳市康拓普信息技术有限公司开发研制。未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/


import java.util.ArrayList;
import java.util.List;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.KeeperException.NoNodeException;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

/**
 * curator管理zookeeper的相关方法工具类
 * 
 * 要点: 1.连接的建立 (两种 OK--使用权限方式)
 * 2.节点的管理操作,增删改查--层叠节点的操作(OK --设立命名空间)
 * 3.节点的监听操作,和无限监听事件触发
 * 4.节点的访问控制ACL操作,密码的添加,修改
 * 5.节点原子性操作
 * 6.节点的分布式锁操作
 *
 * @author 李欢
 * @since jdk1.7
 * @version 2017-7-27 李欢
 */
public final class CuratorUtils {
    
    /** LOG */
    private final static Logger LOG = LoggerFactory.getLogger(CuratorUtils.class);
    
    /** zookeeper 存储编码 */
    public final static String ZOOKEEPER_DATA_ENCODING = "GBK";
    
    /**
     * 设置全局单例模式
     */
    private static CuratorFramework client;
    
    
    /**
     * 私有化构造器
     */
    private CuratorUtils() {
    }
    
    /**
     * 关闭客户端连接
     */
    public static void closeClient() {
        if (null != client) {
            client.close();
        }
    }
    
    public static CuratorFramework initZookeeperclient(String connectString) {
    	RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
    	client = CuratorFrameworkFactory.newClient(connectString, retryPolicy);
    	client.start();
        return client;
    }
    
    public static CuratorFramework initZookeeperclient(String connectString, int sessionTimeoutMs, int connectionTimeoutMs) {
    	RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
    	client = CuratorFrameworkFactory.newClient(connectString, sessionTimeoutMs, connectionTimeoutMs, retryPolicy);
    	client.start();
        return client;
    }
    
    /**
     * 查询某个路径下节点列表
     * 
     * @param <T> T
     * @param clazz class
     * @param parentPath parentPath
     * @return nodes list
     * @描述：获取子节点列表 打印
     */
    public static <T> List<T> nodesList(final Class<T> clazz, final String parentPath) {
        List<String> lstPath = null;
        try {
            lstPath = client.getChildren().forPath(parentPath);
        } catch (Exception e) {
            if (e instanceof NoNodeException) {
                LOG.error("Zookeeper：未找到服务路由配置路径，正在动态创建", parentPath);
                try {
                    client.create().creatingParentsIfNeeded().forPath(parentPath);
                } catch (Exception e1) {
                    throw new RuntimeException("Zookeeper：动态创建路径失败：parentPath=" + parentPath, e);
                }
            } else {
                throw new RuntimeException("Zookeeper：获取子节点列表出错：parentPath=" + parentPath, e);
            }
        }
        if (lstPath == null) {
            return new ArrayList<T>(0);
        }
        List<T> lstNode = new ArrayList<T>(lstPath.size());
        for (String strPath : lstPath) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("遍历查询子节点，子节点路径：{}", parentPath + "/" + strPath);
            }
            lstNode.add(getData(parentPath + "/" + strPath, clazz));
        }
        return lstNode;
    }
    
    /**
     * 获取节点数据
     * 
     * @param <T> T
     * @param clazz class
     * @param path path
     * @return nodes list
     * @描述：获取子节点列表 打印
     */
    public static <T> T getData(final String path, final Class<T> clazz) {
        T t = null;
        try {
            byte[] byteData = client.getData().forPath(path);
            String strJson = new String(byteData, ZOOKEEPER_DATA_ENCODING);
            t = new Gson().fromJson(strJson, clazz);
        } catch (Exception e) {
            if (e instanceof NoNodeException) {
                return null;
            }
            throw new RuntimeException("Zookeeper：获取节点信息出错：path=" + path, e);
            
        }
        
        return t;
    }
    
    /**
     * 新增节点
     * 
     * @param path path
     * @param obj 内容
     */
    public static void createNode(final String path, Object obj) {
        try {
            String strContent = new Gson().toJson(obj);
            Stat objStat = getStat(path);
            if (objStat != null) {
                // TODO: 此处不应该删除， 更新数据即可
                // deleteDataNode(client, path);
                client.setData().forPath(path, strContent.getBytes(ZOOKEEPER_DATA_ENCODING));
            } else {
                client.create().creatingParentsIfNeeded().forPath(path, strContent.getBytes(ZOOKEEPER_DATA_ENCODING));
            }
        } catch (Exception e) {
            throw new RuntimeException("Zookeeper：创建节点信息出错：path=" + path, e);
        }
    }
    
    /**
     * 
     * @param path path
     * @return Stat
     */
    public static Stat getStat(final String path) {
        try {
            return client.checkExists().forPath(path);
        } catch (Exception e) {
            throw new RuntimeException("Zookeeper：获取节点状态出错：path=" + path, e);
        }
    }
    
    /**
     * 设置Zookeeper节点的数据
     * 
     * @param path path
     * @param data message
     * @描述：设置节点中的信息
     */
    public static void setDataNode(final String path, Object data) {
        // 不存在则新增
        Stat objStat = getStat(path);
        if (objStat == null) {
            // 新增节点
            createNode(path, "");
        }
        String strMessage = new Gson().toJson(data);
        try {
            client.setData().forPath(path, strMessage.getBytes(ZOOKEEPER_DATA_ENCODING));
        } catch (Exception e) {
            throw new RuntimeException("Zookeeper：设置节点数据出错：path=" + path, e);
        }
    }
    
    /**
     * 删除Zookeeper节点
     * 
     * @param path path
     */
    public static void deleteDataNode(final String path) {
        
        // Stat stat = client.checkExists().forPath(path);
        // 删除所有子节点
        // Void forPath = client.delete().deletingChildrenIfNeeded().forPath(path);
        try {
            client.delete().deletingChildrenIfNeeded().forPath(path);
        } catch (Exception e) {
            throw new RuntimeException("Zookeeper：删除节点信息出错：path=" + path, e);
        }
    }
    
}
