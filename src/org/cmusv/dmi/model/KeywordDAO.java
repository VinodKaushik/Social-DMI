package org.cmusv.dmi.model;

import org.cmusv.dmi.databeans.Keyword;
import org.mybeans.dao.DAOException;
import org.mybeans.dao.GenericDAO;
import org.mybeans.factory.MatchArg;
import org.mybeans.factory.RollbackException;

/**
 * @Author Vinod Ekambaram
 * @file AttributeDAO.java
 */

public class KeywordDAO extends GenericDAO<Keyword> {

	public KeywordDAO() {
		super(Keyword.class, "keyword", "keywordId");
		
		
		setUseAutoIncrementOnCreate(true);

		// Long running web apps need to clean up idle database connections (for
		// MySQL anyway).
		// (You would only notice a problem after leaving your web app running
		// for several hours.)
		setIdleConnectionCleanup(true);
		loadData();
	}
	
	public void loadData(){
		String[] track = { "Thai flood", "DISASTER WARNING", "VICTIMS",
				"DONATE", "RESCUE", "firemen", "Cholera", "floods",
				"emergency", "survivors" };
		Keyword keyword;
		Keyword[] keywordInit={};
		
		try {
			keywordInit= this.getKeywords();
		} catch (DAOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("track length "+track.length+"> Keywordinit length "+keywordInit.length);
		if(track.length>keywordInit.length){
			for(int i=keywordInit.length;i<track.length;i++){
	        	keyword = new Keyword(track[i]);
	        	keyword.setKeywordDesc(track[i]);
	        	try {
					this.create(keyword);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }  
		}
	}

	public Keyword lookup(String keywordDesc) throws DAOException {
		Keyword[] keyword;
		try {
			keyword = getFactory().match(
					MatchArg.equals("keywordDesc", keywordDesc));
			return (keyword.length == 0 ? null : keyword[0]);
		} catch (RollbackException e) {
			throw new DAOException(e);
		}
	}
	
	public Keyword[] getKeywords() throws DAOException {
		try {
			Keyword[] keywords = getFactory().match();
			return keywords;
		} catch (RollbackException e) {
			throw new DAOException(e);
		}
	}
	
	public String[] getKeywordString() throws DAOException {
		
		try {
			Keyword[] keywords = getFactory().match();
			String[] trackWords = new String[keywords.length];
			for(int i=0;i<keywords.length;i++){
				trackWords[i]=keywords[i].getKeywordDesc();
			}
			return trackWords;
		} catch (RollbackException e) {
			throw new DAOException(e);
		}
	}


	public Keyword getKeywordById(int keywordId) {
		try {
			Keyword[] keywords = getFactory().match(
					MatchArg.equals("keywordId", keywordId));
			if (keywords != null && keywords.length > 0)
				return keywords[0];
			else
				return null;
		} catch (RollbackException e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public void incrementCount(Keyword keyword){
		try {
			Keyword lkeyword = getFactory().lookup(keyword.getKeywordId());
			lkeyword.setKeywordCount((lkeyword.getKeywordCount()+1));
			this.update(lkeyword);
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
	}
	
	public void incrementFont(Keyword keyword){		
		try {
			Keyword lkeyword = getFactory().lookup(keyword.getKeywordId());
			int fSize = lkeyword.getFontSize();
			int kCount = lkeyword.getKeywordCount();
			if((kCount>=1000) && (kCount<=30000)){
				fSize = 22 + (kCount/1000);
			}
			else if(kCount>30000){
				fSize = 52;
			}	
			lkeyword.setFontSize(fSize);
			this.update(lkeyword);
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}

}
