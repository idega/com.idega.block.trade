package com.idega.block.trade.data.dao;

import java.util.List;

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

	@Override
	public List<Currency> getAll() {
		return getResultList(Currency.QUERY_GET_ALL, Currency.class);
	}
	
	public List<Currency> getDistinctCurrencies() {
		return getResultList(Currency.QUERY_GET_DISTINCT_CURRENCIES, Currency.class);
	}
	
}