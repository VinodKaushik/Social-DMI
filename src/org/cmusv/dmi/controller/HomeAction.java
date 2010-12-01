package org.cmusv.dmi.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.cmusv.dmi.databeans.Keyword;
import org.cmusv.dmi.databeans.Tweets;
import org.cmusv.dmi.model.KeywordDAO;
import org.cmusv.dmi.model.Model;
import org.cmusv.dmi.model.TweetsDAO;
import org.cmusv.dmi.twitter.SearchTweets;
import org.mybeans.dao.DAOException;

import twitter4j.TwitterException;

/**
 * @Author Vinod Ekambaram
 * @file HomeAction.java
 */

public class HomeAction extends Action {
	
	private TweetsDAO tweetsDAO;
	private KeywordDAO keywordDAO;
	
	
	public String getName() {
		return "home.do";
	}

	public HomeAction(Model model) {

		tweetsDAO = model.getTweetsDAO();
		keywordDAO = model.getKeywordDAO();
	}
	
	public String perform(HttpServletRequest request) {
		Tweets tweetListAll[] = null;
		Keyword keywordsList[]=null;
		Tweets tweetList[];
		int tweetCount=0;
		String[] locations={};
		double[][] latLngArray = null;
		//Tweets recentTweets[] = null;
		
//		SearchTweets st = new SearchTweets(tweetsDAO,keywordDAO);
//		try {
//			st.getTweetsByKeyword();
//		} catch (TwitterException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		try {
			keywordsList = keywordDAO.getKeywords();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String trackWord = request.getParameter("keywordId");
		
		//System.out.println("###################################   "+trackWord);

		try {
			tweetListAll = tweetsDAO.getTweetsByTrackwords(trackWord);
			tweetCount = tweetsDAO.getTweetCountByTrackwords(trackWord);
			locations = tweetsDAO.getTweetPlacesByTrackwords(trackWord);
			latLngArray = tweetsDAO.getLatLng();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if(tweetListAll.length>10){
			tweetList = new Tweets[10];
			for(int i=0;i<10;i++){
				tweetList[i] = tweetListAll[(tweetListAll.length-1)-i];
			}
		}
		else{
			tweetList = tweetListAll;
		}
		
		List<String> list = Arrays.asList(locations);
		Set<String> set = new HashSet<String>(list);
		String[] result = new String[set.size()];
		set.toArray(result);

		request.setAttribute("tweetList", tweetList);
		request.setAttribute("trackWords",keywordsList);
		request.setAttribute("tweetCount",tweetCount);
		request.setAttribute("locations",result);	
		request.setAttribute("trackWord",trackWord);
		request.setAttribute("latLngArray",latLngArray);
		
		return "home.jsp";
	}

}
