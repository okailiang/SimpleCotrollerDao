package com.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model.UserBean;

public class LoginController extends HttpServlet {
	/** serialVersionUID */
	private static final long serialVersionUID = -16790640755682343L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html;charset=utf-8");
		req.setCharacterEncoding("utf-8");
		String userName = req.getParameter("userName");
		String password = req.getParameter("password");
		UserBean userBean = new UserBean();
		try {

			// 校验前台输入的用户和密码是否合法
			if (checkInput(userName, password)) {
				userBean.setUserName(userName);
				userBean.setPassword(password);

				// 判断该用户库中的密码和前台传来的密码是否相同
				if (userBean.login(userBean)) {
					req.setAttribute("userName", userName);
					req.getRequestDispatcher("/jsp/login_success.jsp").forward(
							req, resp);
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		req.setAttribute("errorInfo", "*用户名或密码错误！");
		req.getRequestDispatcher("/jsp/login_fail.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	/**
	 * 
	 * @Description：校验前台输入的用户名和密码是否正确
	 * @param userName
	 * @param password
	 * @return
	 */
	private boolean checkInput(String userName, String password) {
		if ((userName == null || userName.trim().length() <= 0)
				&& (password == null || password.trim().length() <= 0)) {
			return false;
		}
		return true;
	}
}
