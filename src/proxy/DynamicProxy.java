package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
public class DynamicProxy implements InvocationHandler {
	// 要代理的真实对象
	private Object subject;
	public DynamicProxy(Object subject) {
		this.subject = subject;
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		System.out.println("before rent...");
		System.out.println("Method:" + method);
		method.invoke(subject, args);
		System.out.println("after rent...");
		return null;
	}
}
