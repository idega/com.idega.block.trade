package com.idega.block.trade.stockroom.business;


import javax.ejb.CreateException;
import com.idega.business.IBOHomeImpl;

public class ProductPriceBusinessHomeImpl extends IBOHomeImpl implements ProductPriceBusinessHome {
	public Class getBeanInterfaceClass() {
		return ProductPriceBusiness.class;
	}

	public ProductPriceBusiness create() throws CreateException {
		return (ProductPriceBusiness) super.createIBO();
	}
}