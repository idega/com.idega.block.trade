package com.idega.block.trade.data.dao;

import java.util.List;

import com.idega.block.trade.data.bean.CreditCardInformation;
import com.idega.core.persistence.GenericDao;
import com.idega.user.data.bean.Group;

public interface CreditCardInformationDAO extends GenericDao {

	public static final String BEAN_NAME = "CreditCardInformationDAO";

	public List<CreditCardInformation> findBySupplierManager(Group supplierManager);
	public CreditCardInformation findByMerchant(String merchantPK, String merchantType);
	public CreditCardInformation findByPrimaryKey(Integer pk);
	public void store(CreditCardInformation info);
	public void delete(CreditCardInformation info);

}