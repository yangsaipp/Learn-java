package mybatis.internal.plugin2;

/**
 * 引入拦截器概念，用于具体处理相关类的要拦截的方法
 * @author yangsai
 *
 */
public interface Interceptor {

	/**
	 * 拦截相关类的方法，进行逻辑处理
	 * @param invocation
	 * @return
	 */
	Object intercept(Invocation invocation);
}
