package br.com.olx.challenge.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.olx.challenge.bean.LoginBean;

public class LoginFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		LoginBean loginBean = (LoginBean) ((HttpServletRequest) request)
				.getSession().getAttribute("loginBean");

		if (loginBean == null || loginBean.getLoggedUser() == null) {
			String contextPath = ((HttpServletRequest) request)
					.getContextPath();
			((HttpServletResponse) response).sendRedirect(contextPath
					+ "/login.xhtml");
		}

		chain.doFilter(request, response);

	}

	public void init(FilterConfig config) throws ServletException {
		
	}

	public void destroy() {
	}
}
