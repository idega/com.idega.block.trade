package com.idega.block.trade.stockroom.data;


import javax.ejb.CreateException;
import java.sql.SQLException;
import javax.ejb.FinderException;
import com.idega.data.IDOFactory;

public class TravelAddressHomeImpl extends IDOFactory implements
		TravelAddressHome {
	public Class getEntityInterfaceClass() {
		return TravelAddress.class;
	}

	public TravelAddress create() throws CreateException {
		return (TravelAddress) super.createIDO();
	}

	public TravelAddress findByPrimaryKey(Object pk) throws FinderException {
		return (TravelAddress) super.findByPrimaryKeyIDO(pk);
	}

	public TravelAddress createLegacy() {
		try {
			return create();
		} catch (CreateException ce) {
			throw new RuntimeException(ce.getMessage());
		}
	}

	public TravelAddress findByPrimaryKey(int id) throws FinderException {
		return (TravelAddress) super.findByPrimaryKeyIDO(id);
	}

	public TravelAddress findByPrimaryKeyLegacy(int id) throws SQLException {
		try {
			return findByPrimaryKey(id);
		} catch (FinderException fe) {
			throw new SQLException(fe.getMessage());
		}
	}
}