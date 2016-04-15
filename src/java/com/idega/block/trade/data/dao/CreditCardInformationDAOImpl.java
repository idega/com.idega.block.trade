package com.idega.block.trade.data.dao;

import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idega.block.trade.data.bean.CreditCardInformation;
import com.idega.block.trade.data.bean.Currency;
import com.idega.core.persistence.Param;
import com.idega.core.persistence.impl.GenericDaoImpl;
import com.idega.user.data.bean.Group;

@Transactional(readOnly=true)
@Service(CreditCardInformationDAO.BEAN_NAME)
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class CreditCardInformationDAOImpl extends GenericDaoImpl implements CreditCardInformationDAO{

	@Override
	public List<CreditCardInformation> findBySupplierManager(Group supplierManager) {
		return getResultList(CreditCardInformation.findBySupplierManager, CreditCardInformation.class, new Param(CreditCardInformation.supplierProp, supplierManager.getID()));
	}

	@Override
	public CreditCardInformation findByMerchant(String merchantPK, String merchantType) {
		return getSingleResult(CreditCardInformation.findByMerchant, CreditCardInformation.class, new Param(CreditCardInformation.mpkProp, merchantPK), new Param(CreditCardInformation.typeProp, merchantType));
	}

	@Override
	public CreditCardInformation findByPrimaryKey(Integer pk) {
		return getSingleResult(CreditCardInformation.findByPrimaryKey, CreditCardInformation.class, new Param(CreditCardInformation.idProp, pk));
	}

	@Override
	public void store(CreditCardInformation info) {
		if (info.getId()!=null) merge(info);
		else persist(info);
	}

	@Override
	public void delete(CreditCardInformation info) {
		// TODO Auto-generated method stub
	}

}
