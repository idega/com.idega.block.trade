package com.idega.block.trade.stockroom.data;

import javax.ejb.CreateException;

import com.idega.data.IDOHome;

public interface SideAdHome extends IDOHome {
	ConnectedProduct createConnectedProduct() throws CreateException ;
	FileLinkAd createFileLinkAd() throws CreateException ;
}
