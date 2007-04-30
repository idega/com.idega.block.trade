package com.idega.block.trade.data;


import com.idega.user.data.Group;
import java.util.Collection;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import com.idega.data.IDOEntity;
import com.idega.data.IDOFactory;

public class CreditCardInformationHomeImpl extends IDOFactory implements CreditCardInformationHome {

	public Class getEntityInterfaceClass() {
		return CreditCardInformation.class;
	}

	public CreditCardInformation create() throws CreateException {
		return (CreditCardInformation) super.createIDO();
	}

	public CreditCardInformation findByPrimaryKey(Object pk) throws FinderException {
		return (CreditCardInformation) super.findByPrimaryKeyIDO(pk);
	}

	public CreditCardInformation findByMerchant(String merchantPK, String merchantType) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Object pk = ((CreditCardInformationBMPBean) entity).ejbFindByMerchant(merchantPK, merchantType);
		this.idoCheckInPooledEntity(entity);
		return this.findByPrimaryKey(pk);
	}

	public Collection findBySupplierManager(Group supplierManager) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((CreditCardInformationBMPBean) entity).ejbFindBySupplierManager(supplierManager);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}
}