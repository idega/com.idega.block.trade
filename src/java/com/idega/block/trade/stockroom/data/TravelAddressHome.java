package com.idega.block.trade.stockroom.data;


import javax.ejb.CreateException;
import com.idega.data.IDOHome;
import java.sql.SQLException;
import javax.ejb.FinderException;

public interface TravelAddressHome extends IDOHome {
	public TravelAddress create() throws CreateException;

	public TravelAddress findByPrimaryKey(Object pk) throws FinderException;

	public TravelAddress createLegacy();

	public TravelAddress findByPrimaryKey(int id) throws FinderException;

	public TravelAddress findByPrimaryKeyLegacy(int id) throws SQLException;
}