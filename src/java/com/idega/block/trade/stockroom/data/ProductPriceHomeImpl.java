package com.idega.block.trade.stockroom.data;


import java.sql.Date;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.idega.data.IDOEntity;
import com.idega.data.IDOException;
import com.idega.data.IDOFactory;
import com.idega.data.IDOLookupException;
import com.idega.data.IDORelationshipException;

public class ProductPriceHomeImpl extends IDOFactory implements ProductPriceHome {
	public Class getEntityInterfaceClass() {
		return ProductPrice.class;
	}

	public ProductPrice create() throws CreateException {
		return (ProductPrice) super.createIDO();
	}

	public ProductPrice findByPrimaryKey(Object pk) throws FinderException {
		return (ProductPrice) super.findByPrimaryKeyIDO(pk);
	}

	public void clearPrices(int productId, int currencyId) throws IDOLookupException, FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		((ProductPriceBMPBean) entity).ejbHomeClearPrices(productId, currencyId);
		this.idoCheckInPooledEntity(entity);
	}

	public void clearPrices(int productId, int currencyId, String key) throws FinderException, IDOLookupException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		((ProductPriceBMPBean) entity).ejbHomeClearPrices(productId, currencyId, key);
		this.idoCheckInPooledEntity(entity);
	}

	public Collection findProductPrices(int productId, boolean netBookingOnly) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((ProductPriceBMPBean) entity).ejbFindProductPrices(productId, netBookingOnly);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findProductPrices(int productId, int timeframeId, int addressId, boolean netBookingOnly, String key) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((ProductPriceBMPBean) entity).ejbFindProductPrices(productId, timeframeId, addressId, netBookingOnly, key);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findProductPrices(int productId, int timeframeId, int addressId, boolean netBookingOnly) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((ProductPriceBMPBean) entity).ejbFindProductPrices(productId, timeframeId, addressId, netBookingOnly);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findProductPrices(int productId, int timeframeId, int addressId, int[] visibility, String key) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((ProductPriceBMPBean) entity).ejbFindProductPrices(productId, timeframeId, addressId, visibility, key);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findMiscellaneousPrices(int productId, int timeframeId, int addressId, boolean netBookingOnly) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((ProductPriceBMPBean) entity).ejbFindMiscellaneousPrices(productId, timeframeId, addressId, netBookingOnly);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findMiscellaneousPrices(int productId, int timeframeId, int addressId, boolean netBookingOnly, int currencyId) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((ProductPriceBMPBean) entity).ejbFindMiscellaneousPrices(productId, timeframeId, addressId, netBookingOnly, currencyId);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findProductPrices(int productId, int timeframeId, int addressId, boolean netBookingOnly, int countAsPersonStatus, int currencyId, String key) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((ProductPriceBMPBean) entity).ejbFindProductPrices(productId, timeframeId, addressId, netBookingOnly, countAsPersonStatus, currencyId, key);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findProductPrices(int productId, int timeframeId, int addressId, int countAsPersonStatus, int currencyId, int visibility, String key) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((ProductPriceBMPBean) entity).ejbFindProductPrices(productId, timeframeId, addressId, countAsPersonStatus, currencyId, visibility, key);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findProductPrices(int productId, int timeframeId, int addressId, int countAsPersonStatus, int currencyId, int[] visibility, String key) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((ProductPriceBMPBean) entity).ejbFindProductPrices(productId, timeframeId, addressId, countAsPersonStatus, currencyId, visibility, key);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findProductPrices(int productId, int timeframeId, int addressId, int countAsPersonStatus, int currencyId, int priceCategoryId, Date exactDate) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((ProductPriceBMPBean) entity).ejbFindProductPrices(productId, timeframeId, addressId, countAsPersonStatus, currencyId, priceCategoryId, exactDate);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public int[] getCurrenciesInUse(int productId) {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		int[] theReturn = ((ProductPriceBMPBean) entity).ejbHomeGetCurrenciesInUse(productId);
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	public int[] getCurrenciesInUse(int productId, int visibility) {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		int[] theReturn = ((ProductPriceBMPBean) entity).ejbHomeGetCurrenciesInUse(productId, visibility);
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	public int[] getCurrenciesInUse(int productId, int[] visibility) {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		int[] theReturn = ((ProductPriceBMPBean) entity).ejbHomeGetCurrenciesInUse(productId, visibility);
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	public boolean hasProductPrices(int productId, int timeframeId, int addressId, boolean netBookingOnly, String key) throws FinderException, IDOException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		boolean theReturn = ((ProductPriceBMPBean) entity).ejbHomeHasProductPrices(productId, timeframeId, addressId, netBookingOnly, key);
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	public ProductPrice findByData(int productId, int timeframeId, int addressId, int currencyId, int priceCategoryId, Date date) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Object pk = ((ProductPriceBMPBean) entity).ejbFindByData(productId, timeframeId, addressId, currencyId, priceCategoryId, date);
		this.idoCheckInPooledEntity(entity);
		return this.findByPrimaryKey(pk);
	}

	public Collection findBySQL(String sql) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((ProductPriceBMPBean) entity).ejbFindBySQL(sql);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public ProductPrice findIdentical(ProductPrice price, int currencyID) throws FinderException, IDORelationshipException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Object pk = ((ProductPriceBMPBean) entity).ejbFindIdentical(price, currencyID);
		this.idoCheckInPooledEntity(entity);
		return this.findByPrimaryKey(pk);
	}

	public ProductPrice findSideProductPrice(
			int productId
	) throws IDORelationshipException, FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Object pk = ((ProductPriceBMPBean) entity).ejbFindSideProductPrice(
				productId
		);
		if(pk == null){
			return null;
		}
		this.idoCheckInPooledEntity(entity);
		return this.findByPrimaryKey(pk);
	}
	
}