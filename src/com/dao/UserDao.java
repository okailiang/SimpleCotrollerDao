package com.dao;

import com.model.UserBean;
import com.model.UserBeanProxy;
import com.utils.Conversation;

/**
 * <p>
 * Class:UserDaoImpl
 * </p>
 * <p>
 * Description:实现数据库的连接和关闭，以及增、删、该、查操作
 * </p>
 * <p>
 * Copyright: USTC
 * </p>
 * 
 * @author Oukailiang
 * @version 1.0.0
 */
public class UserDao {

	/**
	 * @Description：根据用户id查询数据
	 * @param userID
	 * @return
	 */
	public UserBean queryUser(String userName) {
		return new Conversation().getUser("userName", userName);
	}

	/**
	 * @Description：增加用户信息
	 * @param u
	 * @return
	 */
	public boolean insertUser(UserBean u) {

		return new Conversation().save(u);
	}

	/**
	 * @Description：根据用户id更新密码
	 * @param u
	 * @return
	 */
	public boolean updateUser(UserBean u) {
		return new Conversation().update(u);
	}

	/**
	 * @Description：根据用户id删除用户信息
	 * @param u
	 * @return
	 */
	public boolean deleteUser(UserBean u) {
		return new Conversation().delete(u);
	}

	/**
	 * 
	 * @Description：实现对象属性 lazy-loading
	 * @param userBean
	 * @return
	 */
	public UserBean load(UserBean userBean) {
		UserBeanProxy userProxy = new UserBeanProxy();
		userProxy = new Conversation().load(userBean.getId());
		userProxy.getPassword();
		return (UserBean) userProxy;
	}

	/**
	 * 
	 * @Description：测试类的属性延迟加载
	 * @param args
	 */
	public static void main(String[] args) {
		UserBean userBean = new UserBean();
		userBean.setId("111");
		userBean = new UserDao().load(userBean);
		System.out.println("userbean:" + userBean.getId()
				+ userBean.getUserName());
	}
}
