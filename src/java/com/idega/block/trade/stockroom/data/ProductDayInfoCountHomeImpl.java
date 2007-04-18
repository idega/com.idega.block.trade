package com.idega.block.trade.stockroom.data;


import java.util.Collection;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import java.sql.Date;
import com.idega.data.IDOEntity;
import com.idega.data.IDOFactory;

public class ProductDayInfoCountHomeImpl extends IDOFactory implements
		ProductDayInfoCountHome {
	public Class getEntityInterfaceClass() {
		return ProductDayInfoCount.class;
	}

	public ProductDayInfoCount create() throws CreateException {
		return (ProductDayInfoCount) super.createIDO();
	}

	public ProductDayInfoCount findByPrimaryKey(Object pk)
			throws FinderException {
		return (ProductDayInfoCount) super.findByPrimaryKeyIDO(pk);
	}

	public ProductDayInfoCount findByProductIdAndDate(int productId, Date date)
			throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Object pk = ((ProductDayInfoCountBMPBean) entity)
				.ejbFindByProductIdAndDate(productId, date);
		this.idoCheckInPooledEntity(entity);
		return this.findByPrimaryKey(pk);
	}

	public ProductDayInfoCount find(int productId, Date date, int timeframeId,
			int addressId) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Object pk = ((ProductDayInfoCountBMPBean) entity).ejbFind(productId,
				date, timeframeId, addressId);
		this.idoCheckInPooledEntity(entity);
		return this.findByPrimaryKey(pk);
	}

	public ProductDayInfoCount find(int productId, Date date, int timeframeId,
			Collection addressIds) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Object pk = ((ProductDayInfoCountBMPBean) entity).ejbFind(productId,
				date, timeframeId, addressIds);
		this.idoCheckInPooledEntity(entity);
		return this.findByPrimaryKey(pk);
	}
}