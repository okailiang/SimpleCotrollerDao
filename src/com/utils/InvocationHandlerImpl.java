package com.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * <p>
 * Class:InvocationHandlerImpl
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: USTC
 * </p>
 * 
 * @author Oukailiang
 * @version 1.0.0
 */
public class InvocationHandlerImpl implements InvocationHandler {
	private Object target;

	public InvocationHandlerImpl(Object target) {
		this.target = target;
	}

	/**
	 * @Description：
	 * @param proxy
	 * @param method
	 * @param args
	 * @return
	 * @throws Throwable
	 */
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object obj = method.invoke(target, args);
		return obj;
	}

}
