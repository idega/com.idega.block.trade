package com.idega.block.trade.stockroom.business;


import javax.ejb.CreateException;
import com.idega.business.IBOHome;
import java.rmi.RemoteException;

public interface ProductBusinessHome extends IBOHome {
	public ProductBusiness create() throws CreateException, RemoteException;
}