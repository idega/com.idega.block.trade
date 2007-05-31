package com.idega.block.trade.stockroom.data;


import java.util.Collection;
import javax.ejb.CreateException;
import java.sql.SQLException;
import javax.ejb.FinderException;
import com.idega.data.IDOEntity;
import com.idega.data.IDOFactory;

public class PriceCategoryHomeImpl extends IDOFactory implements
		PriceCategoryHome {
	public Class getEntityInterfaceClass() {
		return PriceCategory.class;
	}

	public PriceCategory create() throws CreateException {
		return (PriceCategory) super.createIDO();
	}

	public PriceCategory findByPrimaryKey(Object pk) throws FinderException {
		return (PriceCategory) super.findByPrimaryKeyIDO(pk);
	}

	public PriceCategory createLegacy() {
		try {
			return create();
		} catch (CreateException ce) {
			throw new RuntimeException(ce.getMessage());
		}
	}

	public PriceCategory findByPrimaryKey(int id) throws FinderException {
		return (PriceCategory) super.findByPrimaryKeyIDO(id);
	}

	public PriceCategory findByPrimaryKeyLegacy(int id) throws SQLException {
		try {
			return findByPrimaryKey(id);
		} catch (FinderException fe) {
			throw new SQLException(fe.getMessage());
		}
	}

	public Collection findGroupedCategories(PriceCategory cat)
			throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((PriceCategoryBMPBean) entity)
				.ejbFindGroupedCategories(cat);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public PriceCategory findByKey(String key) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Object pk = ((PriceCategoryBMPBean) entity).ejbFindByKey(key);
		this.idoCheckInPooledEntity(entity);
		return this.findByPrimaryKey(pk);
	}

	public Collection findBySupplierAndCountAsPerson(int supplierID,
			boolean countAsPerson) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((PriceCategoryBMPBean) entity)
				.ejbFindBySupplierAndCountAsPerson(supplierID, countAsPerson);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}
}