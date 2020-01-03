package com.idega.block.trade.stockroom.data;

import javax.ejb.CreateException;

import com.idega.data.IDOFactory;

public class SideAdHomeImpl extends IDOFactory implements SideAdHome {
	private static final long serialVersionUID = -2219474391074924732L;

	public ConnectedProduct createConnectedProduct() throws CreateException {
		SideAd ad = (SideAd) createIDO();
		ad.setType(SideAd.TYPE_SIDE_PRODUCT);
		return (ConnectedProduct) ad;
	}

	public FileLinkAd createFileLinkAd() throws CreateException {
		SideAd ad = (SideAd) createIDO();
		ad.setType(SideAd.TYPE_FILE_LINK);
		return (FileLinkAd) ad;
	}

	protected Class getEntityInterfaceClass() {
		return SideAd.class;
	}
}
