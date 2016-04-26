package com.model;

import com.dao.UserDao;

/**
 * 
 * <p>
 * Class:UserBean
 * </p>
 * <p>
 * Description:用户实体存有用户的属性
 * </p>
 * <p>
 * Copyright: USTC
 * </p>
 * 
 * @author Oukailiang
 * @version 1.0.0
 */
public class UserBean {
	private String id;
	private String userName;
	private String password;

	public UserBean() {
		super();
	}

	public UserBean(String id, String userName, String password) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 
	 * @Description：处理用户登录
	 * @param userBean
	 * @return
	 */
	public boolean login(UserBean userBean) {
		UserDao userDao = new UserDao();
		UserBean newUser = new UserBean();

		// 查询该用户
		newUser = userDao.queryUser(userBean.getUserName());
		userBean.setId("11111");
		userBean.setUserName("qqqqqqqqq");
		userBean.setPassword("123456");
		// 插入一条数据
		if (userDao.insertUser(userBean)) {
			System.out.println("成功插入一条数据！");
		}
		// 更新一条数据
		if (userDao.updateUser(userBean)) {
			System.out.println("成功更新一条数据！");
		}
		// 删除一条数据
		if (userDao.deleteUser(userBean)) {
			System.out.println("成功删除一条数据！");
		}
		if (newUser != null) {
			// 比较数据库中取出的用户密码和前台传来的密码是否相等
			if (newUser.getPassword().equals(userBean.getPassword())) {
				return true;
			}
		}
		return false;
	}
}
