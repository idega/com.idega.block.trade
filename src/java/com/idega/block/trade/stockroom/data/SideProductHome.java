package com.idega.block.trade.stockroom.data;

import java.util.List;

import com.idega.data.IDOException;
import com.idega.data.IDOHome;
import com.idega.data.IDOLookupException;

public interface SideProductHome  extends IDOHome {
	public List findSideProductSearchItem(
			int current, 
			String term, 
			int localeId,
			int start,
			int max
	)  throws IDOException, IDOLookupException ;
	
	public boolean isSideProduct(int productId,int sideProductId);
	public SideProduct getSidepProduct(int productId,int sideProductId);
}
