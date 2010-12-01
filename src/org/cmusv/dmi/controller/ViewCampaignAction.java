package org.cmusv.dmi.controller;

import javax.servlet.http.HttpServletRequest;

import org.cmusv.dmi.databeans.Keyword;
import org.cmusv.dmi.databeans.Tweets;
import org.cmusv.dmi.model.KeywordDAO;
import org.cmusv.dmi.model.Model;
import org.cmusv.dmi.model.TweetsDAO;
import org.mybeans.dao.DAOException;

public class ViewCampaignAction extends Action {	
	
	private TweetsDAO tweetsDAO;
	private KeywordDAO keywordDAO;

	public String getName() {
		return "viewCampaign.do";
	}

	public ViewCampaignAction(Model model) {
		
		tweetsDAO = model.getTweetsDAO();
		keywordDAO = model.getKeywordDAO();
	}

	public String perform(HttpServletRequest request) {
		try {

			String trackWord = request.getParameter("keywordId");

			Tweets tweetList[] = null;
			Keyword keywordList[] = null;

			tweetList = tweetsDAO.getTweetsByTrackwords(trackWord);


			request.setAttribute("tweetList", tweetList);
			
			String chkKeyword = request.getParameter("newKeyword");
			System.out.println(chkKeyword+"  is the keyword");
			Keyword lkpKeyword = keywordDAO.lookup(chkKeyword);
			String button = request.getParameter("button");
			Keyword keyword;
			if (lkpKeyword != null) {
				keywordList = keywordDAO.getKeywords();
				request.setAttribute("trackWords", keywordList);
				return "home.jsp";
			}

			if (button != null && button.equals("Add Keyword")) {
				keyword = new Keyword(chkKeyword);
				keywordDAO.create(keyword);
			}
			keywordList = keywordDAO.getKeywords();
			request.setAttribute("trackWords",keywordList);
			

			return "home.jsp";
		} catch (DAOException e) {
			return "apperror.jsp";
		}
	}
}
