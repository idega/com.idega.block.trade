package com.idega.block.trade.data;


import com.idega.user.data.Group;
import java.util.Collection;
import javax.ejb.CreateException;
import com.idega.data.IDOHome;
import javax.ejb.FinderException;

public interface CreditCardInformationHome extends IDOHome {

	public CreditCardInformation create() throws CreateException;

	public CreditCardInformation findByPrimaryKey(Object pk) throws FinderException;

	public CreditCardInformation findByMerchant(String merchantPK, String merchantType) throws FinderException;

	public Collection findBySupplierManager(Group supplierManager) throws FinderException;
}