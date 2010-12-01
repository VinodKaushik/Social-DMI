package org.cmusv.dmi.databeans;

/**
 * @Author Vinod Ekambaram
 * @file Keyword.java
 */

public class Keyword  {


	private int keywordId;

	private String keywordDesc;
	
	private int keywordCount=0;
	
	private int fontSize=22;

	public Keyword(int keywordId) {
		this.keywordId = keywordId;
	}

	public Keyword(String keyword) {
		this.keywordDesc = keyword;
	}

	public String getKeywordDesc() {
		return keywordDesc;
	}

	public void setKeywordDesc(String keywordDesc) {
		this.keywordDesc = keywordDesc;
	}

	public int getKeywordId() {
		return keywordId;
	}

	public int getKeywordCount() {
		return keywordCount;
	}

	public void setKeywordCount(int keywordCount) {
		this.keywordCount = keywordCount;
	}
	
	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

}
