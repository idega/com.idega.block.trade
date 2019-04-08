package com.idega.block.trade.stockroom.data;


import java.sql.SQLException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.idega.data.IDOCompositePrimaryKeyException;
import com.idega.data.IDOException;
import com.idega.data.IDOHome;
import com.idega.data.IDORelationshipException;
import com.idega.util.IWTimestamp;

public interface ProductHome extends IDOHome {
	public Product create() throws CreateException;

	public Product findByPrimaryKey(Object pk) throws FinderException;

	public Product createLegacy();

	public Product findByPrimaryKey(int id) throws FinderException;

	public Product findByPrimaryKeyLegacy(int id) throws SQLException;

	public Collection findProductsOrderedByProductCategory(int supplierId)
			throws FinderException;

	public Collection findProductsOrderedByProductCategory(int supplierId,
			IWTimestamp stamp) throws FinderException;

	public Collection findProductsOrderedByProductCategory(int supplierId, int productCategoryId, 
			IWTimestamp from, IWTimestamp to) throws FinderException;

	public Collection findProducts(int supplierId) throws FinderException;
	public Collection findProducts(boolean onlyValidProducts, int supplierId) throws FinderException;

	public Collection findProducts(int supplierId, int productCategoryId, int firstEntity, int lastEntity) throws FinderException;
	public Collection findProducts(boolean onlyValidProducts, int supplierId, int productCategoryId, int firstEntity, int lastEntity) throws FinderException;

	public Collection findProducts(int supplierId, int firstEntity, int lastEntity) throws FinderException;
	public Collection findProducts(boolean onlyValidProducts, int supplierId, int firstEntity, int lastEntity) throws FinderException;

	public int getProductCount(int supplierId) throws IDOException;
	public int getProductCount(boolean onlyValidProducts, int supplierId) throws IDOException;
	public int getProductCount(boolean onlyValidProducts, int supplierId,boolean onlyEnabled) throws IDOException;

	public int getProductCount(int supplierId, int productCategoryId)
			throws IDOException;

	public Collection findProducts(int supplierId, int productCategoryId,
			IWTimestamp from, IWTimestamp to) throws FinderException;

	public Collection findProducts(int supplierId, int productCategoryId,
			IWTimestamp from, IWTimestamp to, String orderBy)
			throws FinderException;

	public int getProductFilterNotConnectedToAnyProductCategory();

	public Collection findProducts(int supplierId, int productCategoryId,
			IWTimestamp from, IWTimestamp to, String orderBy, int localeId,
			int filter) throws FinderException;

	public Collection findProducts(int supplierId, int productCategoryId,
			IWTimestamp from, IWTimestamp to, String orderBy, int localeId,
			int filter, boolean useTimeframes) throws FinderException;

	public Collection findBySupplyPool(SupplyPool pool)
			throws IDORelationshipException, FinderException,
			IDOCompositePrimaryKeyException;

	public Collection findByPriceCategory(PriceCategory priceCategory)
			throws IDORelationshipException, FinderException;

	public Collection findProducts(boolean onlyValidProducts, int supplierId,
			int firstEntity, int lastEntity, boolean onlyEnabled) throws FinderException;
	
	public Collection findOtherProductsByName(
			int current, 
			String term, 
			int start,
			int max
	) throws IDOException;
	public int countOtherProductsByName(
			int current, 
			String term
	) throws IDOException;
	
	public Collection findSideProducts(int productId,int start, int max) throws IDORelationshipException;
}