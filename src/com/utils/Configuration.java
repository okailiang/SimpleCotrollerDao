package com.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <p>
 * Class:Configuration
 * </p>
 * <p>
 * Description: 解析or_mapping.xml文件
 * </p>
 * <p>
 * Copyright: USTC
 * </p>
 * 
 * @author Oukailiang
 * @version 1.0.0
 */
public class Configuration {
	private static String driver;
	private static String url;
	private static String user;
	private static String password;
	private static Connection conn = null;
	private static Map<String, List> ORMap = new HashMap<String, List>();

	public Configuration parseORMapping() {

		try {
			// 创建saxReader对象
			SAXReader reader = new SAXReader();
			// 通过read方法读取一个文件 转换成Document对象
			Document document = reader.read(Configuration.class
					.getClassLoader().getResourceAsStream("or_mapping.xml"));
			// 获取根节点元素对象
			Element node = document.getRootElement();
			// 获得数据库连接参数
			getJDBC(node);
			//
			getORMapping(node);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
	}

	@SuppressWarnings("unchecked")
	private void getJDBC(Element node) {
		// 获得jdbc节点及子元素
		Element jdbcE = node.element("jdbc");
		List<Element> property = jdbcE.elements("property");
		// 获得连接数据库属性
		for (Element e : property) {
			String name = e.element("name").getText().trim();
			String value = e.element("value").getText().trim();
			if ("driver_class".equals(name)) {
				driver = value;
			}
			if ("url_path".equals(name)) {
				url = value;
			}
			if ("db_username".equals(name)) {
				user = value;
			}
			if ("db_userpassword".equals(name)) {
				password = value;
			}
		}

	}

	@SuppressWarnings("unchecked")
	private void getORMapping(Element node) {
		// 解析类与属性映射
		Element claE = node.element("class");
		// 类节点元素
		Element classE = claE.element("name");
		// 表节点元素
		Element tableE = claE.element("table");
		Element idE = claE.element("id").element("name");

		List<String> list = new ArrayList<String>();
		//
		String className = classE.getText().trim();
		String table = tableE.getText().trim();
		String id = idE.getText().trim();
		list.add(className);
		ORMap.put("class", list);
		list = new ArrayList<String>();
		list.add(table);
		ORMap.put(tableE.getName(), list);
		list = new ArrayList<String>();
		list.add(id);
		ORMap.put("id", list);

		List<Element> propCol = claE.elements("property");
		Map<String, String> propColMap;
		List<Map> colList = new ArrayList<Map>();
		for (Element e : propCol) {
			propColMap = new HashMap<String, String>();
			propColMap.put("name", e.element("name").getText().trim());
			propColMap.put("colum", e.element("colum").getText().trim());
			propColMap.put("type", e.element("type").getText().trim());
			propColMap.put("lazy", e.element("lazy").getText().trim());
			colList.add(propColMap);
		}
		ORMap.put("property", colList);
	}

	public static Map<String, List> getORMapping() {
		return ORMap;
	}

	/**
	 * 
	 * @Description：创建MySql数据库连接操作
	 * @return
	 */
	public static Connection openDBConnection() {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
