package com.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.model.UserBean;
import com.model.UserBeanProxy;

/**
 * <p>
 * Class:Conversation
 * </p>
 * <p>
 * Description:负责完成将对象操作映射为数据表操作
 * </p>
 * <p>
 * Copyright: USTC
 * </p>
 * 
 * @author Oukailiang
 * @version 1.0.0
 */
@SuppressWarnings("static-access")
public class Conversation {
	private static Connection conn = null;
	private PreparedStatement ptmt = null;
	private ResultSet rs = null;
	private static Configuration config = null;
	@SuppressWarnings("unchecked")
	private static Map<String, List> oRMap = null;
	private static UserBean userTable = null;
	private static String tableName;
	private static boolean passwordLazy = false;
	private static boolean userNameLazy = false;

	static {
		config = new Configuration().parseORMapping();
		oRMap = config.getORMapping();
		conn = config.openDBConnection();
		getUserTable();
	}

	/**
	 * @Description：根据用户名查询数据
	 * @param userID
	 * @return
	 */
	public UserBean getUser(String userName, Object value) {
		UserBean userBean = new UserBean();
		String sql = "";
		try {

			sql = "select * from " + tableName + " where "
					+ userTable.getUserName() + "= ?";
			ptmt = conn.prepareStatement(sql);
			ptmt.setObject(1, value);
			rs = ptmt.executeQuery();
			if (rs == null) {
				return null;
			}
			// 取得查询结果集的用户信息
			while (rs.next()) {
				userBean.setId(rs.getString("id"));
				userBean.setUserName(rs.getString("userName"));
				userBean.setPassword(rs.getString("password"));
				return userBean;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closePtmtRs();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * @Description：根据用户id查询数据
	 * @param userID
	 * @return
	 */
	public UserBeanProxy load(Object value) {
		UserBeanProxy userBean = new UserBeanProxy();
		String sql = "";
		try {
			if (userNameLazy != true || passwordLazy != true) {
				sql = "select * from " + tableName + " where "
						+ userTable.getId() + "= ?";
				ptmt = conn.prepareStatement(sql);
				ptmt.setObject(1, value);
				rs = ptmt.executeQuery();
				if (rs == null) {
					return null;
				}
				// 取得查询结果集的用户信息
				while (rs.next()) {
					userBean.setId(rs.getString("id"));
					if (userNameLazy != true) {
						userBean.setUserName(rs.getString("userName"));
					}
					if (passwordLazy != true) {
						userBean.setPassword(rs.getString("password"));
					}
					return userBean;
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closePtmtRs();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * @Description：根据用户id查询数据
	 * @param userID
	 * @return
	 */
	public UserBeanProxy get(UserBean u) {
		UserBeanProxy userBean = new UserBeanProxy();
		String sql = "";
		try {
			if (userNameLazy == true || passwordLazy == true) {
				sql = "select * from " + tableName + " where "
						+ userTable.getId() + "= ?";
				ptmt = conn.prepareStatement(sql);
				ptmt.setString(1, u.getId());
				rs = ptmt.executeQuery();
				if (rs == null) {
					return null;
				}
				// 取得查询结果集的用户信息
				while (rs.next()) {
					u.setId(rs.getString("id"));
					if (userNameLazy == true) {
						u.setUserName(rs.getString("userName"));
					}
					if (passwordLazy == true) {
						u.setPassword(rs.getString("password"));
					}
					return userBean;
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closePtmtRs();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * @Description：增加用户信息
	 * @param u
	 * @return
	 */
	public boolean save(Object o) {
		UserBean u = getUserBean(o);
		String sql = "";
		int num;
		try {
			sql = "insert into " + tableName + "(" + userTable.getId() + ","
					+ userTable.getUserName() + "," + userTable.getPassword()
					+ ") values(?,?,?)";
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, u.getId());
			ptmt.setString(2, u.getUserName());
			ptmt.setString(3, u.getPassword());
			num = ptmt.executeUpdate();
			System.out.println("save:" + ptmt.toString());
			if (num > 0) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closePtmtRs();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * @Description:更新用户信息
	 * @param u
	 * @return
	 */
	public boolean update(Object o) {
		UserBean u = getUserBean(o);
		String sql = "";
		int num;
		try {
			sql = "update " + tableName + " set " + userTable.getUserName()
					+ "= ?, " + userTable.getPassword() + "= ? where "
					+ userTable.getId() + "= ?";
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, u.getUserName());
			ptmt.setString(2, u.getPassword());
			ptmt.setString(3, u.getId());
			num = ptmt.executeUpdate();
			System.out.println("update:" + ptmt.toString());
			if (num > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closePtmtRs();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * @Description：根据用户id删除用户信息
	 * @param u
	 * @return
	 */
	public boolean delete(Object o) {
		UserBean u = getUserBean(o);
		String sql = "";
		int num;
		try {
			sql = "delete from " + tableName + " where " + userTable.getId()
					+ " = ?";
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, u.getId());
			num = ptmt.executeUpdate();
			System.out.println("delete:" + ptmt.toString());
			if (num > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closePtmtRs();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	private static void getUserTable() {
		userTable = new UserBean();
		tableName = ((List<String>) oRMap.get("table")).get(0);
		userTable.setId(((List<String>) oRMap.get("id")).get(0));
		List<Map<String, String>> listMap = ((List<Map<String, String>>) oRMap
				.get("property"));
		for (Map<String, String> map : listMap) {
			if ("userName".equals(map.get("name"))) {
				userTable.setUserName(map.get("colum"));
				if ("true".equals(map.get("lazy"))) {
					userNameLazy = true;
				}

			}
			if ("password".equals(map.get("name"))) {
				userTable.setPassword(map.get("colum"));
				if ("true".equals(map.get("lazy"))) {
					passwordLazy = true;
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private UserBean getUserBean(Object o) {
		UserBean userBean = null;
		try {
			userBean = (UserBean) Class.forName(
					((List<String>) oRMap.get("class")).get(0)).newInstance();
			userBean = (UserBean) o;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userBean;
	}

	/**
	 * 
	 * @Description：关闭ptmt rs
	 */
	private void closePtmtRs() {
		try {
			if (ptmt != null) {
				ptmt.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
