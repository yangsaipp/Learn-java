import org.junit.Test;

import com.comtop.bizp.dart.framework.component.cache.CacheFactory;
import com.comtop.bizp.dart.framework.component.cache.CacheInterface;

public class BFSCacheTest {

	CacheInterface cacheInterface = CacheFactory.getCache();
	
	public static void main(String[] args) {
		CacheFactory.getCache().setAttribute("ss", "ss");
		CacheFactory.getCache().removeAttribute("ss");
	}
	
	@Test
	public void testDel() {
		CacheFactory.getCache().removeMultAttributes("001:admin:*");
	}
	
	@Test
	public void testAdd() {
//		String value = "(org_code like 'sz%' or org_code>'bj')";
		for(int i=0; i<100; i++) {
			cacheInterface.setAttribute("001:admin:com.comtop.data.model.readOrder" + i, "(org_code like 'sz%' or org_code>'bj')");
		}
	}
}
