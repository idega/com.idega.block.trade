/*
 * Created on Nov 17, 2004
 *
 */
package com.idega.block.trade.stockroom.data;

import java.sql.Date;
import java.util.Collection;
import java.util.Vector;

import javax.ejb.FinderException;

import com.idega.data.GenericEntity;
import com.idega.data.query.Column;
import com.idega.data.query.InCriteria;
import com.idega.data.query.MatchCriteria;
import com.idega.data.query.OR;
import com.idega.data.query.SelectQuery;
import com.idega.data.query.Table;


/**
 * @author birna
 *
 */
public class ProductDayInfoCountBMPBean extends GenericEntity implements ProductDayInfoCount {
	
	private static final String ADDRESS_ID = "TRAVEL_ADDRESS_ID"; 
	private static final String TIMEFRAME_ID = "TIMEFRAME_ID";

	public void initializeAttributes() {
		addAttribute(getIDColumnName(), "id", true, true, Integer.class);
		addAttribute(getColumnNameProductId(), "product id", true, true, Integer.class);
		addAttribute(getColumnNameProductInfoDate(), "product date", true, true, Date.class);
		addAttribute(getColumnNameProductInfoCount(), "product count", true, true, Integer.class);
		addManyToOneRelationship(getColumnNameProductId(), Product.class);
		addManyToOneRelationship(ADDRESS_ID, TravelAddress.class);
		addManyToOneRelationship(TIMEFRAME_ID, Timeframe.class);
	}
	
	public static String getEntityTableName() { return "sr_pr_info_count"; }
	public static String getColumnNameProductId() { return "sr_product_id"; }
	public static String getColumnNameProductInfoDate() { return "sr_pr_info_date"; }
	public static String getColumnNameProductInfoCount() { return "sr_pr_info_count"; }

	public String getEntityName() {
		return getEntityTableName();
	}
	
	public int getProductId() {
		return getIntColumnValue(getColumnNameProductId());
	}
	
	public Date getDate() {
		return getDateColumnValue(getColumnNameProductInfoDate());
	}
	
	public int getCount() {
		return getIntColumnValue(getColumnNameProductInfoCount());
	}
	
	public void setProductId(int productId) {
		setColumn(getColumnNameProductId(), productId);
	}
	
	public void setDate(Date date) {
		setColumn(getColumnNameProductInfoDate(), date);
	}
	
	public void setCount(int count) {
		setColumn(getColumnNameProductInfoCount(), count);
	}
	
	public void setTimeframeId(int timeframeId) {
		setColumn(TIMEFRAME_ID, timeframeId);
	}
	
	public int getTimeframeId() {
		return getIntColumnValue(TIMEFRAME_ID);
	}
	
	public void setAddressId(int addressId) {
		setColumn(ADDRESS_ID, addressId);
	}
	
	public int getAddressId() {
		return getIntColumnValue(ADDRESS_ID);
	}
	
	public Object ejbFindByProductIdAndDate(int productId, Date date)throws FinderException {
		return ejbFind(productId, date, -1, null);
	}

	public Object ejbFind(int productId, Date date, int timeframeId, int addressId) throws FinderException {
		Vector v = new Vector();
		if (addressId > 0) {
			v.add(new Integer(addressId));
		}
		return ejbFind(productId, date, timeframeId, v);
	}
	public Object ejbFind(int productId, Date date, int timeframeId, Collection addressIds) throws FinderException {
		Table table = new Table(this);
		
		Column prodCol = new Column(table, getColumnNameProductId());
		Column dateCol = new Column(table, getColumnNameProductInfoDate());
		Column frameCol = new Column(table, TIMEFRAME_ID);
		Column addCol = new Column(table, ADDRESS_ID);
		
		SelectQuery query = new SelectQuery(table);
		query.addColumn(table, getIDColumnName());
		if (productId > 0) {
			query.addCriteria(new MatchCriteria(prodCol, MatchCriteria.EQUALS, productId));
		}
		if (date != null) {
			query.addCriteria(new MatchCriteria(dateCol, MatchCriteria.EQUALS, date));
		}
		if (timeframeId > 0) {
			OR or = new OR(new MatchCriteria(frameCol, MatchCriteria.EQUALS, timeframeId), new MatchCriteria(frameCol, MatchCriteria.IS, MatchCriteria.NULL));
			query.addCriteria(or);
		} else {
		}

		if (addressIds != null && !addressIds.isEmpty() && !addressIds.contains(new Integer(-1))) {
			OR or = new OR(new InCriteria(addCol, addressIds), new MatchCriteria(addCol, MatchCriteria.IS, MatchCriteria.NULL));
			query.addCriteria(or);
		} else {
		}
		return idoFindOnePKByQuery(query);
		
	}
}
