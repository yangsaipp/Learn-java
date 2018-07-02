package jadbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

/**
 * https://blog.csdn.net/zkcharge/article/details/50855755
 * @author yangsai
 *
 */
public class JDBCBatchTest {

	Connection conn = getConn();
	
	private static Connection getConn() {
	    String driver = "com.mysql.jdbc.Driver";
	    String url = "jdbc:mysql://localhost:3306/samp_db";
	    String username = "root";
	    String password = "";
	    Connection conn = null;
	    try {
	        Class.forName(driver); //classLoader,加载对应驱动
	        conn = (Connection) DriverManager.getConnection(url, username, password);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return conn;
	}
	
	/**
	 * 参考地址：http://elim.iteye.com/blog/2353672，https://blog.csdn.net/liuxiao723846/article/details/52195332
	 * 疑问：Mybatis在与Spring集成后进行批量操作时事务是否能回滚？答：如果和原spring事务一起使用，将无法回滚。所以批量操作最好是单独操作。
	 * @throws SQLException
	 */
	@Test
	public void testJDBCPrepareBatch() throws SQLException {

		String sql = "insert into b_order(org_code, sales_user_id) values(?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, "org_code_1");
		pstmt.setString(2, "sales_user_id_1");
		pstmt.addBatch();

		pstmt.setString(1, "org_code_2");
		pstmt.setString(2, "sales_user_id_2");
		pstmt.addBatch();

		pstmt.setString(1, "org_code_3");
		pstmt.setString(2, "sales_user_id_3");
		pstmt.addBatch();
		
		pstmt.executeBatch();
		conn.commit();
	}
}
