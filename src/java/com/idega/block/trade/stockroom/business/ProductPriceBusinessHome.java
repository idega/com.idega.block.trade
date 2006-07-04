package com.idega.block.trade.stockroom.business;


import javax.ejb.CreateException;
import com.idega.business.IBOHome;
import java.rmi.RemoteException;

public interface ProductPriceBusinessHome extends IBOHome {
	public ProductPriceBusiness create() throws CreateException, RemoteException;
}