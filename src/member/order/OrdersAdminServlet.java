package member.order;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.MyServlet;

@WebServlet("/ordersadmin")
public class OrdersAdminServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//RequestDispatcher requestDispatcher = req.getRequestDispatcher("ordersadmin.jsp");
		//requestDispatcher.forward(req, resp);
		String cPath = req.getContextPath();
		//resp.sendRedirect("login.jsp");
		resp.sendRedirect(cPath + "/nd.orders");
	}

}
