package com.idega.block.trade.data.dao;

import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idega.block.trade.data.bean.DebitCardInformation;
import com.idega.core.persistence.Param;
import com.idega.core.persistence.impl.GenericDaoImpl;
import com.idega.user.data.bean.Group;

@Transactional(readOnly=true)
@Service(DebitCardInformationDAO.BEAN_NAME)
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class DebitCardInformationDAOImpl extends GenericDaoImpl implements DebitCardInformationDAO{

	@Override
	public List<DebitCardInformation> findBySupplierManager(Group supplierManager) {
		return getResultList(DebitCardInformation.findBySupplierManager, DebitCardInformation.class, new Param(DebitCardInformation.supplierProp, supplierManager.getID()));
	}

	@Override
	public List<DebitCardInformation> findBySupplierManager(Integer supplierManager) {
		return getResultList(DebitCardInformation.findBySupplierManager, DebitCardInformation.class, new Param(DebitCardInformation.supplierProp, supplierManager));
	}

	@Override
	public DebitCardInformation findByMerchant(String merchantPK, String merchantType) {
		return getSingleResult(DebitCardInformation.findByMerchant, DebitCardInformation.class, new Param(DebitCardInformation.mpkProp, merchantPK), new Param(DebitCardInformation.typeProp, merchantType));
	}

	@Override
	public DebitCardInformation findByPrimaryKey(Integer pk) {
		return getSingleResult(DebitCardInformation.findByPrimaryKey, DebitCardInformation.class, new Param(DebitCardInformation.idProp, pk));
	}

	@Override
	public void store(DebitCardInformation info) {
		if (info.getId()!=null) merge(info);
		else persist(info);
	}

	@Override
	public void delete(DebitCardInformation info) {
		// TODO Auto-generated method stub
	}

}
