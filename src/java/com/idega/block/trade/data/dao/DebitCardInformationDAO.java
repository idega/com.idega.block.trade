package com.idega.block.trade.data.dao;

import java.util.List;

import com.idega.block.trade.data.bean.DebitCardInformation;
import com.idega.core.persistence.GenericDao;
import com.idega.user.data.bean.Group;

public interface DebitCardInformationDAO extends GenericDao {

	public static final String BEAN_NAME = "DebitCardInformationDAO";

	public List<DebitCardInformation> findBySupplierManager(Group supplierManager);
	public DebitCardInformation findByMerchant(String merchantPK, String merchantType);
	public DebitCardInformation findByPrimaryKey(Integer pk);
	public void store(DebitCardInformation info);
	public void delete(DebitCardInformation info);
	public List<DebitCardInformation> findBySupplierManager(Integer supplierManager);

}