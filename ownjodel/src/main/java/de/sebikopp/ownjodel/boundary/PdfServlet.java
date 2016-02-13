package de.sebikopp.ownjodel.boundary;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.apache.fop.apps.FOPException;

import de.sebikopp.ownjodel.data.DataReadService;
import de.sebikopp.ownjodel.helpers.convert.PdfHelper;
import de.sebikopp.ownjodel.model.Post;

/**
 * Servlet implementation class PdfServlet
 */
@WebServlet("/PdfServlet")
public class PdfServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Inject
	private DataReadService drs;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PdfServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Anfrage: /PdfServlet?id=<whatever>
		ServletOutputStream outStream = response.getOutputStream();
		response.setHeader("Media-Type", "application/pdf");
		String postid = request.getParameter("id");
		Post post = drs.getPostById(postid);
		byte[] pdf;
		try {
			pdf = PdfHelper.getAsPdf(post);
		} catch (FOPException | TransformerFactoryConfigurationError | TransformerException e) {
			throw new ServletException(e);
		}
		outStream.write(pdf);
		outStream.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
