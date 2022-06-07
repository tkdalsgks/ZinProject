package shop.order;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.MyServlet;

/**
 * Servlet implementation class OrdersInfo
 */
@WebServlet("/ordersinfo")
public class OrdersInfoServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		session.removeAttribute("ordermsg");
		super.forward(req, resp, "/WEB-INF/views/order/order.jsp");
	}

}
