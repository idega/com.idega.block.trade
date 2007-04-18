package com.idega.block.trade.stockroom.data;


import java.util.Collection;
import javax.ejb.CreateException;
import com.idega.data.IDOHome;
import javax.ejb.FinderException;
import java.sql.Date;

public interface ProductDayInfoCountHome extends IDOHome {
	public ProductDayInfoCount create() throws CreateException;

	public ProductDayInfoCount findByPrimaryKey(Object pk)
			throws FinderException;

	public ProductDayInfoCount find(int productId, Date date, int timeframeId,
			int addressId) throws FinderException;

	public ProductDayInfoCount find(int productId, Date date, int timeframeId,
			Collection addressIds) throws FinderException;
}