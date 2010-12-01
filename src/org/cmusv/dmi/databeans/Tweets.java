package org.cmusv.dmi.databeans;

/**
 * @Author Vinod Ekambaram
 * @file Tweets.java
 */

import java.util.Date;


public class Tweets {
	
	private long tweetId;
	
	private String tweetOwner;

	private String tweetDesc;
	
	private double latitude;
	
	private double longitude;
	
	private String placeFullName;
	
	private String trackWord;
	
	private Date createdAt;
	
	public Tweets(long tweetId) {
		this.tweetId = tweetId;
	}
	
	public long getTweetId() {
		return tweetId;
	}

	public String getTweetOwner() {
		return tweetOwner;
	}

	public void setTweetOwner(String tweetOwner) {
		this.tweetOwner = tweetOwner;
	}

	public String getTweetDesc() {
		return tweetDesc;
	}

	public void setTweetDesc(String tweetDesc) {
		this.tweetDesc = tweetDesc;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getPlaceFullName() {
		return placeFullName;
	}

	public void setPlaceFullName(String placeFullName) {
		this.placeFullName = placeFullName;
	}

	public String getTrackWord() {
		return trackWord;
	}

	public void setTrackWord(String trackWord) {
		this.trackWord = trackWord;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	

}
