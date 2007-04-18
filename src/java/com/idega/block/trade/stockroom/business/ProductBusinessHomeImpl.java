package com.idega.block.trade.stockroom.business;


import javax.ejb.CreateException;
import com.idega.business.IBOHomeImpl;

public class ProductBusinessHomeImpl extends IBOHomeImpl implements
		ProductBusinessHome {
	public Class getBeanInterfaceClass() {
		return ProductBusiness.class;
	}

	public ProductBusiness create() throws CreateException {
		return (ProductBusiness) super.createIBO();
	}
}