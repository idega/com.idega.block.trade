package com.idega.block.trade.stockroom.webservice;


public interface TradeService {

	public String invalidateProductCache(String productID, String remoteCallingHostName);
		public String clearAddressMaps(String productID, String remoteCallingHostName);
		public String clearProductCache(String supplierID, String remoteCallingHostName);
		public String invalidatePriceCache(String productID, String remoteCallingHostName);
		
}
