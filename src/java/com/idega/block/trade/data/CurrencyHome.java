package com.idega.block.trade.data;


import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.idega.data.IDOHome;

public interface CurrencyHome extends IDOHome {
	public Currency create() throws CreateException;

	public Currency findByPrimaryKey(Object pk) throws FinderException;

	public Currency findByPrimaryKey(int id) throws javax.ejb.FinderException;

	public Currency findByPrimaryKeyLegacy(int id) throws java.sql.SQLException;

	public Collection getCurrenciesByAbbreviation(String currencyAbbreviation) throws FinderException;

	public Currency getCurrencyByAbbreviation(String currencyAbbreviation) throws FinderException, RemoteException;

	public Collection findAll() throws FinderException;

	public Collection findAllInUse() throws FinderException;
}