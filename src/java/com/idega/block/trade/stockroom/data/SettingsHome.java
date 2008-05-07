package com.idega.block.trade.stockroom.data;


import javax.ejb.CreateException;
import com.idega.data.IDOHome;
import javax.ejb.FinderException;

public interface SettingsHome extends IDOHome {
	public Settings create() throws CreateException;

	public Settings findByPrimaryKey(Object pk) throws FinderException;
}