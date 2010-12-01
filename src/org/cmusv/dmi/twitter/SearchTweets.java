package org.cmusv.dmi.twitter;

/**
 * @Author Vinod Ekambaram
 * @file SearchTweets.java
 */

import javax.servlet.http.HttpServlet;

import org.cmusv.dmi.databeans.Keyword;
import org.cmusv.dmi.databeans.Tweets;
import org.cmusv.dmi.model.KeywordDAO;
import org.cmusv.dmi.model.TweetsDAO;
import org.mybeans.dao.DAOException;

import twitter4j.FilterQuery;
import twitter4j.GeoLocation;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public class SearchTweets extends HttpServlet implements StatusListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private TwitterStream twitterStream;
	private TweetsDAO tweetsDAO;
	private KeywordDAO keywordDAO;
	public String[] trackWords;
	private Keyword[] keywords;
	int tweetCount;

	
	public SearchTweets(TweetsDAO tweetsDAO1, KeywordDAO keywordDAO1) {
		tweetsDAO = tweetsDAO1;
		keywordDAO = keywordDAO1;
	}
	
	public void getTweetsByKeyword() throws TwitterException{
		
		int count = 0;
        int[] follow = {0};
        String[] track;
		try {
			trackWords = keywordDAO.getKeywordString();
			keywords = keywordDAO.getAll();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        track = new String[trackWords.length];
        track = trackWords; 
        
        double[][] locations = {};				
		FilterQuery fquery = new FilterQuery(count, follow, track, locations);
        
		twitterStream = new TwitterStreamFactory().getInstance("tminer_user", "tminer_password123");
		twitterStream.setStatusListener(this);
		twitterStream.filter(fquery); 
		
	}

	@Override
	public void onDeletionNotice(StatusDeletionNotice arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onException(Exception arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatus(Status status) {
		
		Tweets tweet;
		GeoLocation geolocation[][]={};
		int foundWord=0;
		
		tweet = new Tweets(status.getId());
		tweet.setTweetOwner(status.getUser().getName());
		tweet.setTweetDesc(status.getText());
		tweet.setCreatedAt(status.getCreatedAt());
		if(status.getPlace()!=null){
			//System.out.println(status.getPlace().getFullName()+ " ### Place Found");
			geolocation = status.getPlace().getBoundingBoxCoordinates();
			tweet.setLatitude(geolocation[0][0].getLatitude());
			tweet.setLongitude(geolocation[0][0].getLongitude());
			tweet.setPlaceFullName(status.getPlace().getFullName());
		}
		else{
			tweet.setLatitude(0.0);
			tweet.setLongitude(0.0);
			tweet.setPlaceFullName("Not Disclosed!");
		}
		
		for(int i=0;i<keywords.length;i++){
			if(status.getText().matches("(?i:.*"+keywords[i].getKeywordDesc()+".*)")){
				tweet.setTrackWord(trackWords[i]);
				keywordDAO.incrementCount(keywords[i]);
				keywordDAO.incrementFont(keywords[i]);
				//System.out.println(trackWords[i]+ " ### Trackword Found");
				foundWord=1;
				break;
			}
		}	
		if(foundWord==0){
			tweet.setTrackWord("Untracked");
		}
		
		try {
			tweetsDAO.create(tweet);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(status.getUser().getName() + " : "
				+ status.getText());
	}

	@Override
	public void onTrackLimitationNotice(int arg0) {
		// TODO Auto-generated method stub
		System.out.println("Limit Exceeded!");
		
	}
	
//	public static void main(String args[]) throws TwitterException, ServletException{
//		SearchTweets st = new SearchTweets();
//		st.getTweetsByKeyword();
//	}

}
