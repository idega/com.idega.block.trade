package com.idega.block.trade.stockroom.data;

import java.util.Collection;

import javax.ejb.CreateException;

import com.idega.data.IDOHome;

public interface SideAdHome extends IDOHome {
	ConnectedProduct createConnectedProduct() throws CreateException;
	FileLinkAd createFileLinkAd() throws CreateException;
	Collection findsideAdsByProduct(int productId,int start, int max);
	SideAd findByPrimaryKey(Object pk);
	FileLinkAd findFileLinkAdByPk(Object pk);
	ConnectedProduct findConnectedProductByPk(Object pk);
}
