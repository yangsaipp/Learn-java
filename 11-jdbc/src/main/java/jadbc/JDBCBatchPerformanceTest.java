package jadbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.Test;

/**
 * https://blog.csdn.net/zhangw1236/article/details/54583192
 * 
 * @author yangsai
 *
 */
public class JDBCBatchPerformanceTest {

	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://192.168.1.103:3306/memo";
	String username = "root";
	String password = "jadp_develop";

	/**
	 * 场景1：不使用事务，不添加rewriteBatchedStatements=true参数
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	@Test
	public void testJDBCPrepareBatch1() {

		Connection conn = null;
		PreparedStatement pst = null;

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, password);

			String sql = "insert into b_order (order_id,org_code,sales_user_id,sales_time,customer_info,amount,create_time,remark) values(?,?,?,?,?,?,?,?)";
			pst = conn.prepareStatement(sql);
			int loop = 0;
			for (loop = 0; loop < 10000; loop++) {
				pst.setString(1, String.valueOf(loop));
				pst.setString(2, "18");
				pst.setString(3, "zhangshan");
				pst.setDate(4, new Date(System.currentTimeMillis()));
				pst.setString(5, "zhangshan");
				pst.setInt(6, 1000);
				pst.setDate(7, new Date(System.currentTimeMillis()));
				pst.setString(8, "测试");
				pst.addBatch();
			}

			pst.executeBatch();
		}catch (Exception e){
			e.printStackTrace();
		} finally {
			close(conn, pst);
		}
	}
	
	/**
	 * 场景2：使用事务，不添加rewriteBatchedStatements=true参数
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	@Test
	public void testJDBCPrepareBatch2() {

		Connection conn = null;
		PreparedStatement pst = null;

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, password);

			String sql = "insert into b_order (order_id,org_code,sales_user_id,sales_time,customer_info,amount,create_time,remark) values(?,?,?,?,?,?,?,?)";
			conn.setAutoCommit(false);
			
			pst = conn.prepareStatement(sql);
			int loop = 0;
			for (loop = 0; loop < 50000; loop++) {
				pst.setString(1, String.valueOf(loop));
				pst.setString(2, "18");
				pst.setString(3, "zhangshan");
				pst.setDate(4, new Date(System.currentTimeMillis()));
				pst.setString(5, "zhangshan");
				pst.setInt(6, 1000);
				pst.setDate(7, new Date(System.currentTimeMillis()));
				pst.setString(8, "测试");
				pst.addBatch();
			}

			pst.executeBatch();
			conn.commit();
		}catch (Exception e){
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			close(conn, pst);
		}
	}

	/**
	 * 场景3：不使用事务，添加rewriteBatchedStatements=true参数
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	@Test
	public void testJDBCPrepareBatch3() {
		
		Connection conn = null;
		PreparedStatement pst = null;

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url + "?rewriteBatchedStatements=true", username, password);

			String sql = "insert into b_order (order_id,org_code,sales_user_id,sales_time,customer_info,amount,create_time,remark) values(?,?,?,?,?,?,?,?)";
			pst = conn.prepareStatement(sql);
			int loop = 0;
			for (loop = 0; loop < 10000; loop++) {
				pst.setString(1, String.valueOf(loop));
				pst.setString(2, "18");
				pst.setString(3, "zhangshan");
				pst.setDate(4, new Date(System.currentTimeMillis()));
				pst.setString(5, "zhangshan");
				pst.setInt(6, 1000);
				pst.setDate(7, new Date(System.currentTimeMillis()));
				pst.setString(8, "测试");
				pst.addBatch();
			}

			pst.executeBatch();
		}catch (Exception e){
			e.printStackTrace();
		} finally {
			close(conn, pst);
		}
	}
	
	/**
	 * 场景2：使用事务，添加rewriteBatchedStatements=true参数
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	@Test
	public void testJDBCPrepareBatch4() {

		Connection conn = null;
		PreparedStatement pst = null;

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url + "?rewriteBatchedStatements=true", username, password);

			String sql = "insert into b_order (order_id,org_code,sales_user_id,sales_time,customer_info,amount,create_time,remark) values(?,?,?,?,?,?,?,?)";
			conn.setAutoCommit(false);
			
			pst = conn.prepareStatement(sql);
			int loop = 0;
			for (loop = 0; loop < 50000; loop++) {
				pst.setString(1, String.valueOf(loop));
				pst.setString(2, "18");
				pst.setString(3, "zhangshan");
				pst.setDate(4, new Date(System.currentTimeMillis()));
				pst.setString(5, "zhangshan");
				pst.setInt(6, 1000);
				pst.setDate(7, new Date(System.currentTimeMillis()));
				pst.setString(8, "测试");
				pst.addBatch();
			}

			pst.executeBatch();
			conn.commit();
		}catch (Exception e){
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			close(conn, pst);
		}
	}

	private void close(Connection conn, PreparedStatement pst) {
		if (pst != null) {
			try {
				pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
