package shop.sales;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.MyServlet;

@WebServlet("/i.sales")
public class IsalesServlet extends MyServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.forward(req, resp, "/WEB-INF/views/sales/isales.jsp");
	}

}
