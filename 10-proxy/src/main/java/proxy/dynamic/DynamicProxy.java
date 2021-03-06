package proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 该类会拦截被代理类所有指定的方法。
 * @author  杨赛
 * @since   jdk1.7
 * @version 2018年7月10日 杨赛
 */
public class DynamicProxy implements InvocationHandler
{
    //　这个就是我们要代理的真实对象
    private Object subject;
    
    //    构造方法，给我们要代理的真实对象赋初值
    public DynamicProxy(Object subject)
    {
        this.subject = subject;
    }
    
    /**
     * @param object 被调用的对象，一般是JDK生成的代理类
     * @param method 被调用的方法
     * @param args  被调用的参数
     */
    @Override
    public Object invoke(Object object, Method method, Object[] args)
            throws Throwable
    {
        //　　在代理真实对象前我们可以添加一些自己的操作
        System.out.println("before rent house");
        
        System.out.println("Method:" + method);
        System.out.println("object:" + object.getClass());	// 输出：object:class com.sun.proxy.$Proxy0
        
        //    当代理对象调用真实对象的方法时，其会自动的跳转到代理对象关联的handler对象的invoke方法来进行调用
        method.invoke(subject, args);
        
        //　　在代理真实对象后我们也可以添加一些自己的操作
        System.out.println("after rent house");
        
        return null;
    }

}