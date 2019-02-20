package com.idega.block.trade.stockroom.data;


import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.idega.data.IDOCompositePrimaryKeyException;
import com.idega.data.IDOEntity;
import com.idega.data.IDOException;
import com.idega.data.IDOFactory;
import com.idega.data.IDORelationshipException;
import com.idega.util.IWTimestamp;

public class ProductHomeImpl extends IDOFactory implements ProductHome {
	public Class getEntityInterfaceClass() {
		return Product.class;
	}

	public Product create() throws CreateException {
		return (Product) super.createIDO();
	}

	public Product findByPrimaryKey(Object pk) throws FinderException {
		return (Product) super.findByPrimaryKeyIDO(pk);
	}

	public Product createLegacy() {
		try {
			return create();
		} catch (CreateException ce) {
			throw new RuntimeException(ce.getMessage());
		}
	}

	public Product findByPrimaryKey(int id) throws FinderException {
		return (Product) super.findByPrimaryKeyIDO(id);
	}

	public Product findByPrimaryKeyLegacy(int id) throws SQLException {
		try {
			return findByPrimaryKey(id);
		} catch (FinderException fe) {
			throw new SQLException(fe.getMessage());
		}
	}

	public Collection findProductsOrderedByProductCategory(int supplierId)
			throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((ProductBMPBean) entity)
				.ejbFindProductsOrderedByProductCategory(supplierId);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findProductsOrderedByProductCategory(int supplierId,
			IWTimestamp stamp) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((ProductBMPBean) entity)
				.ejbFindProductsOrderedByProductCategory(supplierId, stamp);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findProductsOrderedByProductCategory(int supplierId, int productCategoryId,
			IWTimestamp from, IWTimestamp to) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((ProductBMPBean) entity)
				.ejbFindProductsOrderedByProductCategory(supplierId, productCategoryId, from, to);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findProducts(int supplierId) throws FinderException {
		return findProducts(true, supplierId);
	}
	public Collection findProducts(boolean onlyValidProducts, int supplierId) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((ProductBMPBean) entity).ejbFindProducts(onlyValidProducts, supplierId);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findProducts(int supplierId, int productCategoryId, int firstEntity, int lastEntity) throws FinderException {
		return findProducts(true, supplierId, productCategoryId, firstEntity, lastEntity);
	}
	public Collection findProducts(boolean onlyValidProducts, int supplierId, int productCategoryId, int firstEntity, int lastEntity)
			throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((ProductBMPBean) entity).ejbFindProducts(onlyValidProducts, supplierId, productCategoryId, firstEntity, lastEntity);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findProducts(int supplierId, int firstEntity, int lastEntity) throws FinderException {
		return findProducts(true, supplierId, firstEntity, lastEntity);
	}
	public Collection findProducts(boolean onlyValidProducts, int supplierId, int firstEntity, int lastEntity) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((ProductBMPBean) entity).ejbFindProducts(onlyValidProducts, supplierId, firstEntity, lastEntity);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}
	public Collection findProducts(boolean onlyValidProducts, int supplierId, int firstEntity, int lastEntity,boolean onlyEnabled) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((ProductBMPBean) entity).ejbFindProducts(onlyValidProducts, supplierId, firstEntity, lastEntity,onlyEnabled);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public int getProductCount(int supplierId) throws IDOException {
		return getProductCount(true, supplierId);
	}
	public int getProductCount(boolean onlyValidProducts, int supplierId) throws IDOException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		int theReturn = ((ProductBMPBean) entity).ejbHomeGetProductCount(onlyValidProducts, supplierId);
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}
	
	public int getProductCount(boolean onlyValidProducts, int supplierId,boolean onlyEnabled) throws IDOException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		int theReturn = ((ProductBMPBean) entity).ejbHomeGetProductCount(onlyValidProducts, supplierId,onlyEnabled);
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	public int getProductCount(int supplierId, int productCategoryId)
			throws IDOException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		int theReturn = ((ProductBMPBean) entity).ejbHomeGetProductCount(supplierId, productCategoryId);
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	public Collection findProducts(int supplierId, int productCategoryId,
			IWTimestamp from, IWTimestamp to) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((ProductBMPBean) entity).ejbFindProducts(supplierId,
				productCategoryId, from, to);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findProducts(int supplierId, int productCategoryId,
			IWTimestamp from, IWTimestamp to, String orderBy)
			throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((ProductBMPBean) entity).ejbFindProducts(supplierId,
				productCategoryId, from, to, orderBy);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public int getProductFilterNotConnectedToAnyProductCategory() {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		int theReturn = ((ProductBMPBean) entity)
				.ejbHomeGetProductFilterNotConnectedToAnyProductCategory();
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	public Collection findProducts(int supplierId, int productCategoryId,
			IWTimestamp from, IWTimestamp to, String orderBy, int localeId,
			int filter) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((ProductBMPBean) entity).ejbFindProducts(supplierId,
				productCategoryId, from, to, orderBy, localeId, filter);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findProducts(int supplierId, int productCategoryId,
			IWTimestamp from, IWTimestamp to, String orderBy, int localeId,
			int filter, boolean useTimeframes) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((ProductBMPBean) entity).ejbFindProducts(supplierId,
				productCategoryId, from, to, orderBy, localeId, filter,
				useTimeframes);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findBySupplyPool(SupplyPool pool)
			throws IDORelationshipException, FinderException,
			IDOCompositePrimaryKeyException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((ProductBMPBean) entity).ejbFindBySupplyPool(pool);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findByPriceCategory(PriceCategory priceCategory)
			throws IDORelationshipException, FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((ProductBMPBean) entity)
				.ejbFindByPriceCategory(priceCategory);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findOtherProductsByName(
			int current, 
			String term, 
			int start,
			int max
	) throws IDOException {
		try{
			IDOEntity entity = this.idoCheckOutPooledEntity();
			Collection ids = ((ProductBMPBean) entity)
					.ejbFindOtherProductsByName(current, term, start, max);
			this.idoCheckInPooledEntity(entity);
			return this.getEntityCollectionForPrimaryKeys(ids);
		}catch (FinderException e) {}
		return Collections.EMPTY_LIST;
	}
	
	public int countOtherProductsByName(
			int current, 
			String term
	) throws IDOException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		return((ProductBMPBean) entity)
				.countOtherProductsByName(current, term);
	}
	public Collection findSideProducts(int productId) throws IDORelationshipException{
		try{
			IDOEntity entity = this.idoCheckOutPooledEntity();
			Collection ids = ((ProductBMPBean) entity)
					.ejbFindSideProducts(productId);
			this.idoCheckInPooledEntity(entity);
			return this.getEntityCollectionForPrimaryKeys(ids);
		}catch (FinderException e) {}
		return Collections.EMPTY_LIST;
	}
	
}