package com.idega.block.trade.data;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.CreateException;

import com.idega.data.IDOFactory;

public class VoucherAdHomeImpl extends IDOFactory implements VoucherAdHome{

	private static final long serialVersionUID = 8742292820815239305L;

	protected Class getEntityInterfaceClass() {
		return VoucherAd.class;
	}

	private Logger getLogger(){
		return Logger.getLogger(VoucherAdHomeImpl.class.getName());
	}
	public VoucherAd createVoucherAdd() {
		try {
			return (VoucherAd) createEntity();
		} catch (CreateException e) {
			getLogger().log(Level.WARNING, "Failed creating " + getEntityInterfaceClass().getName(), e);
		}
		return null;
	}
}
