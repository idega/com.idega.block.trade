package com.idega.block.trade.stockroom.data;


import javax.ejb.CreateException;
import javax.ejb.FinderException;
import com.idega.data.IDOFactory;

public class SettingsHomeImpl extends IDOFactory implements SettingsHome {
	public Class getEntityInterfaceClass() {
		return Settings.class;
	}

	public Settings create() throws CreateException {
		return (Settings) super.createIDO();
	}

	public Settings findByPrimaryKey(Object pk) throws FinderException {
		return (Settings) super.findByPrimaryKeyIDO(pk);
	}
}