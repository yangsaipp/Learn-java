package apache;

import java.io.FileReader;
import java.io.Reader;
import java.util.NoSuchElementException;

import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.junit.Test;

/**
 * 
 * @author yangsai
 *
 */
public class Main {

	@Test
	public void test() throws NoSuchElementException, IllegalStateException, Exception {
		ObjectPool<StringBuffer> pool = new GenericObjectPool<StringBuffer>(new StringBufferFactory());
		ReaderUtil readerUtil = new ReaderUtil(pool);
		Reader in = new FileReader(this.getClass().getClassLoader().getResource("test.txt").getFile());
		System.out.println("===:" + readerUtil.readToString(in));
		System.out.println("===bb:" + pool.borrowObject());
	}
	
}
