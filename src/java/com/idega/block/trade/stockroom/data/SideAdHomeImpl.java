package com.idega.block.trade.stockroom.data;

import java.util.Collection;
import java.util.Collections;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.idega.data.IDOEntity;
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

	public Collection findsideAdsByProduct(int productId,int start, int max){
		try{
			IDOEntity entity = this.idoCheckOutPooledEntity();
			Collection ids = ((SideAdBMPBean) entity).ejbFindSideAdsByProduct(productId,start,max);
			idoCheckInPooledEntity(entity);
			return getEntityCollectionForPrimaryKeys(ids);
		}catch (FinderException e) {}
		return Collections.EMPTY_LIST;
	}
}
