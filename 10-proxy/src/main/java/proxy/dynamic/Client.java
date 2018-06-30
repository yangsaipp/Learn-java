package proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class Client
{
    public static void main(String[] args)
    {
    	
    	// 这一句是生成代理类的class文件，前提是你需要在工程根目录下创建com/sun/proxy目录，不然会报找不到路径的io异常
    	System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
    	
        // 我们要代理的真实对象
        Subject realSubject = new RealSubject();

        // 1. 构建InvocationHandler。我们要代理哪个真实对象，就将该对象传进去，最后是通过该真实对象来调用其方法的
        InvocationHandler handler = new DynamicProxy(realSubject);

        /* 
         * 2. 通过Proxy的newProxyInstance方法来创建我们的代理对象。
         * 我们来看看其三个参数：
         * 第一个参数 handler.getClass().getClassLoader() ，我们这里使用handler这个类的ClassLoader对象来加载我们的代理对象
         * 第二个参数realSubject.getClass().getInterfaces()，我们这里为代理对象提供的接口是真实对象所实行的接口，表示我要代理的是该真实对象，这样我就能调用这组接口中的方法了
         * 第三个参数InvocationHandler， 调用handler，请求生成的代理类所代理的方法时，都会调用InvocationHandler的invoke()方法。
         */
        Subject subject = (Subject)Proxy.newProxyInstance(handler.getClass().getClassLoader(), realSubject
                .getClass().getInterfaces(), handler);
        
        System.out.println(subject.getClass().getName());
        subject.rent();
        subject.hello("world");
    }
}