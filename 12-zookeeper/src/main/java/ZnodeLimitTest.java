import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.junit.Test;

public class ZnodeLimitTest {
	
	/**
	 *  测试一个Znode最大的存储容量。
	 *  
	 *  <p>
	 *  	内容大小(B)  实际占用大小(B)   相差              结果
	 *		1017406   1052661    35255           失败
	 *		1014041	  1049180    35139           失败
	 *		1013097	  1048133    35036           成功
	 *		1006070   1040862    34792           成功  
	 *  </p>
	 *  结论：
	 *  1.Zookeeper单节点大小默认限制是：1048576Byte=1024KB=1MB。
	 *  2.实际可用于存储数据的大小989KB(1013097Byte)左右。
	 *  3.超过后服务端会有Warn级别的提示：Len error 1052661。
	 */
	@Test
	public void testZnodeLimit() {
		// 需要增加默认连接、会话时间
		int sessionTimeoutMs = 120 * 1000;
		int connectionTimeoutMs = 120 * 1000;
		CuratorUtils.initZookeeperclient("10.10.31.107:2181", sessionTimeoutMs, connectionTimeoutMs);
		CuratorUtils.createNode("/JYTenantConfigt3/test16", readToString(ZnodeLimitTest.class.getClassLoader().getResource("test-1.txt").getPath()));
//		CuratorUtils.createNode("/JYTenantConfigt3/test14", "1");
		System.out.println("插入成功");
	}
	
	@Test
	public void testC() {
		// 需要增加默认连接、会话时间
		int sessionTimeoutMs = 120 * 1000;
		int connectionTimeoutMs = 120 * 1000;
		CuratorUtils.initZookeeperclient("10.10.31.107:2181", sessionTimeoutMs, connectionTimeoutMs);
		CuratorUtils.createNode("/JYTenantConfigt3/test16", "ss");
//		CuratorUtils.createNode("/JYTenantConfigt3/test14", "1");
		System.out.println("插入成功");
	}
	
	@Test
	public void testSessionTimeout() {
		// 需要增加默认连接、会话时间
		int sessionTimeoutMs = 1 * 1000 + 1;
		int connectionTimeoutMs = 120 * 1000;
		CuratorUtils.initZookeeperclient("192.168.1.152:2181", sessionTimeoutMs, connectionTimeoutMs);
		CuratorUtils.createNode("/JYTenantConfigt3/test16", "ss");
//		CuratorUtils.createNode("/JYTenantConfigt3/test14", "1");
		System.out.println("插入成功");
	}
	
	public static void main(String[] args) throws Exception {
//		CuratorUtils.getZookeeperclient();
//		CuratorUtils.createNode("/JYTenantConfigt3/test12", readToString(ZnodeLimitTest.class.getClassLoader().getResource("test-1.txt").getPath()));
//		System.out.println(CuratorUtils.getData("/JYTenantConfigt3", String.class));
//		TreeCache treeCache = new TreeCache(client,"/JYTenantConfigt");
//		treeCache.getListenable().addListener(new TreeCacheListener() {
//            
//            @Override
//            public void childEvent(final CuratorFramework curatorFramework, TreeCacheEvent event)
//                throws UnsupportedEncodingException {
//                ChildData objData = event.getData();
//                if (objData == null) {
//                    return;
//                }
//                // 此处会根据路径判断当前新增的是什么数据
//                String strPath = objData.getPath();
//                switch (event.getType()) {
//                    case NODE_ADDED:
//                        System.out.println("新增：" + strPath);
//                        break;
//                    case NODE_REMOVED:
//                    	System.out.println("删除：" + strPath);
//                        break;
//                    case NODE_UPDATED:
//                    	System.out.println("更新：" + strPath);
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });
//        // 开始监听,这句话会抛出Exception异常
//        treeCache.start();
//        // 开始监听,这句话会抛出Exception异常
//        Thread.sleep(Long.MAX_VALUE); 
	}
	
	public static String readToString(String fileName) {  
        String encoding = "UTF-8";  
        File file = new File(fileName);  
        Long filelength = file.length();  
        System.out.println("内容大小：" + filelength);
        byte[] filecontent = new byte[filelength.intValue()];  
        try {  
            FileInputStream in = new FileInputStream(file);  
            in.read(filecontent);  
            in.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        try {  
            return new String(filecontent, encoding);  
        } catch (UnsupportedEncodingException e) {  
            System.err.println("The OS does not support " + encoding);  
            e.printStackTrace();  
            return null;  
        }  
    }
}
