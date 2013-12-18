package com.idega.block.trade.data.dao;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idega.block.trade.data.bean.Currency;
import com.idega.core.persistence.Param;
import com.idega.core.persistence.impl.GenericDaoImpl;

@Transactional(readOnly=true)
@Service(CurrencyDAO.BEAN_NAME)
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class CurrencyDAOImpl extends GenericDaoImpl implements CurrencyDAO{
	
	@Override
	public Currency getById(Integer id){
		if(id == null){
			return null;
		}
		return getSingleResult(Currency.QUERY_GET_BY_ID, Currency.class, new Param(Currency.idProp, id));
	}
}
