package com.idega.block.trade.stockroom.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.ejb.FinderException;

import com.idega.block.media.business.MediaBusiness;
import com.idega.block.trade.stockroom.bean.SideProductSearchItem;
import com.idega.core.file.data.ICFile;
import com.idega.data.IDOEntity;
import com.idega.data.IDOException;
import com.idega.data.IDOFactory;
import com.idega.data.IDOLookup;
import com.idega.data.IDOLookupException;
import com.idega.idegaweb.IWMainApplication;

public class SideProductHomeImpl  extends IDOFactory implements SideProductHome {
	private static final long serialVersionUID = 5655532636707285527L;

	protected Class getEntityInterfaceClass() {
		return SideProduct.class;
	}
	
	public SideProduct findSidepProduct(int productId,int sideProductId) {
		try{
			IDOEntity entity = this.idoCheckOutPooledEntity();
			Object id = ((SideProductBMPBean) entity)
					.ejbFindSideProduct(productId, sideProductId);
			this.idoCheckInPooledEntity(entity);
			return (SideProduct) findByPrimaryKeyIDO(id);
		}catch (FinderException e) {}
		return null;
	}
	public boolean isSideProduct(int productId,int sideProductId) {
		try {
			IDOEntity entity = this.idoCheckOutPooledEntity();
			((SideProductBMPBean) entity)
					.ejbFindSideProduct(productId, sideProductId);
			return true;
		}catch (FinderException e) {}
		return false;
	}
	public List findSideProductSearchItem(
			int current, 
			String term, 
			int localeId,
			int start,
			int max
	) throws IDOException, IDOLookupException {
		ProductHome productHome = (ProductHome) IDOLookup.getHome(Product.class);
		Collection products = productHome.findOtherProductsByName(current, term, start, max);
		ArrayList items = new ArrayList(products.size());
		IWMainApplication iwma = IWMainApplication.getDefaultIWMainApplication();
		
		for(Iterator i = products.iterator();i.hasNext();) {
			Product product = (Product)i.next();
			SideProductSearchItem item = new SideProductSearchItem();
			items.add(item);
			int pId = product.getID();
			item.setId(Integer.valueOf(product.getID()));
			ICFile picture = product.getFile();
			if(picture != null){
				item.setImageUrl(
						MediaBusiness.getMediaURL(
								picture, 
								iwma
						)
				);
			}
			item.setName(product.getProductName(localeId));
			
			SideProduct sideProduct = findSidepProduct(current, pId);
			if(sideProduct != null) {
				item.setInSideProducts(true);
				item.setOrder(sideProduct.getOrder());
			}
		}
		return items;
	}

}
