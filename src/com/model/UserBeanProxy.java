package com.model;

import com.utils.Conversation;

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
public class UserBeanProxy extends UserBean {
	private String id;
	private String userName;
	private String password;

	public UserBeanProxy() {
		super();
	}

	public UserBeanProxy(String id, String userName, String password) {
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
		getUser();
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private void getUser() {
		new Conversation().get(this);
	}
}
