package org.cmusv.dmi.model;

/**
 * @Author Vinod Ekambaram
 * @file TweetsDAO.java
 */

import org.cmusv.dmi.databeans.Tweets;
import org.mybeans.dao.DAOException;
import org.mybeans.dao.GenericDAO;
import org.mybeans.factory.MatchArg;
import org.mybeans.factory.RollbackException;

public class TweetsDAO extends GenericDAO<Tweets> {
	
	public TweetsDAO() {
		super(Tweets.class, "tweets", "tweetId");
		setUseAutoIncrementOnCreate(true);

		setIdleConnectionCleanup(true);
	}
	
	public Tweets[] getTweets() throws DAOException {
		try {
			Tweets[] tweets = getFactory().match();
			return tweets;
		} catch (RollbackException e) {
			throw new DAOException(e);
		}
	}
	
	public Tweets[] getTweetsByTrackwords(String trackWord) throws DAOException {
		try {
			Tweets[] tweets = getFactory().match(MatchArg.equals("trackWord", trackWord));
			return tweets;
		} catch (RollbackException e) {
			throw new DAOException(e);
		}
	}
	
	public int getTweetCountByTrackwords(String trackWord) throws DAOException {
		try {
			Tweets[] tweets = getFactory().match(MatchArg.equals("trackWord", trackWord));
			return tweets.length;
		} catch (RollbackException e) {
			throw new DAOException(e);
		}
	}
	
	public String[] getTweetPlacesByTrackwords(String trackWord) throws DAOException {
		try {
			Tweets[] tweets = getFactory().match(MatchArg.equals("trackWord", trackWord));
			String[] locations = new String[tweets.length];
			for(int i=0;i<tweets.length;i++){
				
				locations[i] = tweets[i].getPlaceFullName();
			}
			return locations;
		} catch (RollbackException e) {
			throw new DAOException(e);
		}
	}
	
	public double[][] getLatLng() throws DAOException {
		try {
			Tweets[] tweetsGreater = getFactory().match(MatchArg.greaterThan("latitude", 0.0));
			Tweets[] tweetsLesser = getFactory().match(MatchArg.lessThan("latitude", 0.0));
			int totalLength = tweetsGreater.length+tweetsLesser.length;
			double[][] latLng = new double[totalLength][2];
			for(int i=0;i<tweetsLesser.length;i++){
				latLng[i][0] = tweetsLesser[i].getLatitude();
				latLng[i][1] = tweetsLesser[i].getLongitude();
			}
			for(int d=0,j=tweetsLesser.length;j<totalLength;j++,d++){
					latLng[j][0] = tweetsGreater[d].getLatitude();
					latLng[j][1] = tweetsGreater[d].getLongitude();
			}
			System.out.println("This is the number of records in latLng: "+latLng.length);
			System.out.println("This is the number of records in tweets: "+totalLength);
			System.out.println("Latitude	|	Longitude");
			for(int c=0;c<latLng.length;c++){
				System.out.println(latLng[c][0]+"	|	"+latLng[c][1]);
			}
			return latLng;
		} catch (RollbackException e) {
			throw new DAOException(e);
		}
	}



}
