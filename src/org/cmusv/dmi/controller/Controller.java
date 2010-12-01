package org.cmusv.dmi.controller;

/**
 * @Author Vinod Ekambaram
 * @file Controller.java
 */

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cmusv.dmi.model.Model;


public class Controller extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {
		Model model = new Model(getServletConfig());
		Action.add(new HomeAction(model));
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nextPage = performTheAction(request);
		sendToNextPage(nextPage, request, response);
	}

	/*
	 * Extracts the requested action and (depending on whether the user is
	 * logged in) perform it (or make the user login). @param request @return
	 * the next page (the view)
	 */
	private String performTheAction(HttpServletRequest request) {
		String servletPath = request.getServletPath();
		String action = getActionName(servletPath);

		if (action.equals("start") || action.equals("home.do")) {
			return Action.perform("home.do", request);
		}
		
		if (action.equals("viewCampaign.do")) {
			return Action.perform("viewCampaign.do", request);
		}
		
		request.setAttribute("message",
						"The Page you requested is not available! Please check if the URL is correct");
		// Let the logged in user run his chosen action
		return "apperror.jsp";
	}

	/*
	 * If nextPage is null, send back 404 If nextPage starts with a '/',
	 * redirect to this page. In this case, the page must be the whole servlet
	 * path including the webapp name Otherwise dispatch to the page (the view)
	 * This is the common case Note: If nextPage equals "image", we will
	 * dispatch to /image. In the web.xml file, "/image" is mapped to the
	 * ImageServlet will which return the image bytes for display.
	 */
	private void sendToNextPage(String nextPage, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		if (nextPage == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, request
					.getServletPath());
			return;
		}

		if (nextPage.charAt(0) == '/') {
			String host = request.getServerName();
			String port = ":" + String.valueOf(request.getServerPort());
			if (port.equals(":80"))
				port = "";
			response.sendRedirect("http://" + host + port + nextPage);
			return;
		}

		RequestDispatcher d = request.getRequestDispatcher("/view/" + nextPage);
		d.forward(request, response);
	}

	/*
	 * Returns the path component after the last slash removing any "extension"
	 * if present.
	 */
	private String getActionName(String path) {
		// We're guaranteed that the path will start with a slash
		int slash = path.lastIndexOf('/');
		return path.substring(slash + 1);
	}
}
