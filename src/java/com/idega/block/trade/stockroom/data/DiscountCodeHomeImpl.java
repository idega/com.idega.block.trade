package com.idega.block.trade.stockroom.data;

import java.util.Collection;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.FinderException;

import com.idega.data.IDOEntity;
import com.idega.data.IDOFactory;

public class DiscountCodeHomeImpl  extends IDOFactory implements DiscountCodeHome {

	private static final long serialVersionUID = -1694705291796354579L;

	protected Class getEntityInterfaceClass() {
		return DiscountCode.class;
	}
	
	private Logger getLogger(){
		return Logger.getLogger(DiscountCodeHomeImpl.class.getName());
	}
	public DiscountCode getByCodeAndProduct(String code,Object productId){
		try{
			IDOEntity entity = this.idoCheckOutPooledEntity();
			Object id = ((DiscountCodeBMPBean) entity).getByCodeAndProduct(code, productId);
			this.idoCheckInPooledEntity(entity);
			return (DiscountCode) this.findByPrimaryKeyIDO(id);
		}catch (FinderException e) {
		}catch (Exception e) {
			getLogger().log(Level.WARNING, "Failed finding code", e);
		}
		return null;
	}
	
	public DiscountCode getByCode(String code){
		try{
			IDOEntity entity = this.idoCheckOutPooledEntity();
			Object id = ((DiscountCodeBMPBean) entity).getByCode(code);
			this.idoCheckInPooledEntity(entity);
			return (DiscountCode) this.findByPrimaryKeyIDO(id);
		}catch (FinderException e) {
		}
		return null;
	}
	
	public Collection getBySupplier(Object supplierPK,int start, int max){
		try{
			IDOEntity entity = this.idoCheckOutPooledEntity();
			Collection pks = ((DiscountCodeBMPBean) entity).getBySupplier(supplierPK, start, max);
			this.idoCheckInPooledEntity(entity);
			return this.findByPrimaryKeyCollection(pks);
		}catch (FinderException e) {
		}
		return Collections.EMPTY_LIST;
	}
	
	public Collection getBySupplierNotUsed(Object supplierPK,int start, int max){
		try{
			IDOEntity entity = this.idoCheckOutPooledEntity();
			Collection pks = ((DiscountCodeBMPBean) entity).getBySupplierNotUsed(supplierPK, start, max);
			this.idoCheckInPooledEntity(entity);
			return this.findByPrimaryKeyCollection(pks);
		}catch (FinderException e) {
		}
		return Collections.EMPTY_LIST;
	}
	public int countByProduct(Object productId) {
		try{
			IDOEntity entity = this.idoCheckOutPooledEntity();
			int count = ((DiscountCodeBMPBean) entity).countByProduct(productId);
			return count;
		}catch (Exception e) {
			getLogger().log(Level.WARNING, "Failed counting code by product " + productId, e);
		}
		return 0;
	}

	public Collection getPKsByProductId(Object productId,int start, int max) {
		try{
			IDOEntity entity = this.idoCheckOutPooledEntity();
			return  ((DiscountCodeBMPBean) entity).getByProductId(productId, start, max);
		}catch (FinderException e) {
		}catch (Exception e) {
			getLogger().log(Level.WARNING, "Failed finding by product " + productId, e);
		}
		return Collections.EMPTY_LIST;
	}
}
