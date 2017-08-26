/******************************************************************************
* Copyright (C) 2014 ShenZhen ComTop Information Technology Co.,Ltd
* All Rights Reserved.
* 本软件为深圳康拓普开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
* 复制、修改或发布本软件.
*****************************************************************************/

package lang.pathMatcher;

import static org.junit.Assert.assertTrue;

import org.junit.Test;


/**
 * @author  杨赛
 * @since   jdk1.7
 * @version 2017年7月28日 杨赛
 */
public class PathMatcherTest {
	/** matcher */
	private PathMatcher matcher = new AntPathMatcher();

	/**
	 * Test method for {@link com.comtop.jadp.auth.client.filter.pathMatcher.PathMatcher#match(java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testMatch() {
		String pattern = "/api/session/**";
		assertTrue(matcher.match(pattern, "/api/session"));
		assertTrue(matcher.match(pattern, "/api/session/4A"));
	}

}
