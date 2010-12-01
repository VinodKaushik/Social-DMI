package org.cmusv.dmi.model;

/**
 * @Author Vinod Ekambaram
 * @file AttributeDAO.java
 */

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.mybeans.factory.BeanTable;

public class Model {
	

	private KeywordDAO keywordDAO;
	
	private TweetsDAO tweetsDAO;

	public Model(ServletConfig config) throws ServletException {

		String jdbcDriver = config.getInitParameter("jdbcDriverName");
		String jdbcURL = config.getInitParameter("jdbcURL");
		BeanTable.useJDBC(jdbcDriver, jdbcURL, "root", "");
		keywordDAO = new KeywordDAO();
		tweetsDAO = new TweetsDAO();
	}

	public Model(String jdbcDriver, String jdbcURL) {
		BeanTable.useJDBC(jdbcDriver, jdbcURL);
		keywordDAO = new KeywordDAO();
		tweetsDAO = new TweetsDAO();
	}

	public KeywordDAO getKeywordDAO() {
		return keywordDAO;
	}

	public TweetsDAO getTweetsDAO() {
		return tweetsDAO;
	}
	
}
