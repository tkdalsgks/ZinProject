package account;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.MyServlet;

@WebServlet("/logout")
public class logoutServlet extends MyServlet {
	
	public logoutServlet() {
		super();
	}

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		req.setCharacterEncoding("UTF-8");
			
		session.invalidate();
		super.forward(req, resp, "/WEB-INF/index.jsp");
	}
}
