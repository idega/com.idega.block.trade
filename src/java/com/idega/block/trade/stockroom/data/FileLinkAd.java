package com.idega.block.trade.stockroom.data;

public interface FileLinkAd extends SideAd {
	
	String getAdUrl();
	void setAdUrl(String url);
	
	String getFileUrl();
	void setFileUrl(String url);
	
	String getAdName();
	void setAdName(String name);
	
	String getAdInfo();
	void setAdInfo(String info);
	
	String getBookingUrl();
	void setBookingUrl(String url);
	
	
	
}