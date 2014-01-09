package com.idega.block.trade.data.dao;

import java.util.List;

import com.idega.block.trade.data.bean.Currency;


public interface CurrencyDAO {
	public static final String BEAN_NAME = "currencyDAO";
	public Currency getById(Integer id);
	public List<Currency> getAll();
	public List<Currency> getDistinctCurrencies();
}
