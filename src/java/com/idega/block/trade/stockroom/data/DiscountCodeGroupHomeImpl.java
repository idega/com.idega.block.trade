package com.idega.block.trade.stockroom.data;

import java.util.Collection;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.FinderException;

import com.idega.data.IDOEntity;
import com.idega.data.IDOFactory;

public class DiscountCodeGroupHomeImpl  extends IDOFactory implements DiscountCodeGroupHome {

	private static final long serialVersionUID = -1694705291796354579L;

	protected Class getEntityInterfaceClass() {
		return DiscountCodeGroup.class;
	}
	
	private Logger getLogger(){
		return Logger.getLogger(DiscountCodeGroupHomeImpl.class.getName());
	}
	
	
	public Collection getBySupplierId(Object supplierPK,int start, int max){
		try{
			IDOEntity entity = this.idoCheckOutPooledEntity();
			Collection pks = ((DiscountCodeGroupBMPBean) entity).getBySupplier(supplierPK, start, max);
			this.idoCheckInPooledEntity(entity);
			return this.findByPrimaryKeyCollection(pks);
		}catch (FinderException e) {
		}catch (Exception e) {
			getLogger().log(Level.WARNING, "Failed getting by supplier " + supplierPK, e);
		}
		return Collections.EMPTY_LIST;
	}
	
	public Collection getByProductId(Object productId,int start, int max) {
		try{
			IDOEntity entity = this.idoCheckOutPooledEntity();
			return  ((DiscountCodeGroupBMPBean) entity).getByProductId(productId, start, max);
		}catch (FinderException e) {
		}catch (Exception e) {
			getLogger().log(Level.WARNING, "Failed finding by product " + productId, e);
		}
		return Collections.EMPTY_LIST;
	}
}