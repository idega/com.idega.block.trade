package com.idega.block.trade.stockroom.data;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.logging.Level;

import javax.ejb.FinderException;

import com.idega.data.IDOAddRelationshipException;
import com.idega.data.IDOException;
import com.idega.data.IDORelationshipException;
import com.idega.data.query.MatchCriteria;
import com.idega.data.query.SelectQuery;
import com.idega.data.query.Table;
import com.idega.data.query.WildCardColumn;
import com.idega.util.IWTimestamp;

public class DiscountCodeGroupBMPBean extends com.idega.data.GenericEntity implements DiscountCodeGroup{
	
	private static final long serialVersionUID = -5377085698356733943L;
	
	public static final String TABLE_NAME = "sr_dc_group";
	
	public static final String COLUMN_DISCOUNT = "discount";
	public static final String COLUMN_SUPPLIER = "SR_SUPPLIER_ID";
	public static final String COLUMN_VALID = "valid";
	public static final String COLUMN_MAX_USAGE = "max_usage";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_CREATION_DATE = "CREATION_DATE";
	public static final String COLUMN_MODIFICATION_DATE = "MODIFICATION_DATE";
	
	public String getEntityName() {
		return TABLE_NAME;
	}

	
	public void initializeAttributes() {
		addAttribute(getIDColumnName());
		addAttribute(COLUMN_NAME, COLUMN_NAME,true,true,String.class);
		addAttribute(COLUMN_DISCOUNT, COLUMN_DISCOUNT,true,true,Float.class);
		addAttribute(COLUMN_MAX_USAGE, COLUMN_MAX_USAGE,true,true,Integer.class);
		this.addAttribute(COLUMN_VALID, COLUMN_VALID, true, true, Boolean.class );
		addOneToOneRelationship(COLUMN_SUPPLIER,Supplier.class);
		this.addAttribute( COLUMN_CREATION_DATE, COLUMN_CREATION_DATE, true, true, Timestamp.class );
	    this.addAttribute( COLUMN_MODIFICATION_DATE, COLUMN_MODIFICATION_DATE, true, true, Timestamp.class );
	}
	
	public float getDiscount() {
		return getFloatColumnValue(COLUMN_DISCOUNT,0);
	}


	public void setDiscount(float discount) {
		setColumn(COLUMN_DISCOUNT, discount);
	}

	public void setSupplier(Supplier supplier){
		try {
			idoAddTo(supplier);
		} catch (IDOAddRelationshipException e) {
			getLogger().log(Level.WARNING, "Falied adding supplier "+ getPrimaryKey(), e);
		}
	}
	
	public void setSupplierId(Object supplierId){
		setColumn(COLUMN_SUPPLIER, supplierId);
	}
	
	public boolean isValid(){
		return getBooleanColumnValue(COLUMN_VALID);
	}
	
	public void setDefaultValues(){
		setValid(true);
		setMaxUsage(1);
	}
	public void setValid(boolean valid){
		setColumn(COLUMN_VALID, valid);
	}
	
	public void setMaxUsage(int maxUsage){
		setColumn(COLUMN_MAX_USAGE, maxUsage);
	}


	public String getDiscountCodeGroupName() {
		return getStringColumnValue(COLUMN_NAME);
	}


	public void setDiscountCodeGroupName(String name) {
		setColumn(COLUMN_NAME, name);
	}
	private void setCreationDate( Timestamp timestamp ) {
	    setColumn( COLUMN_CREATION_DATE, timestamp );
	 }

	private void setModificationDate( Timestamp timestamp ) {
	    setColumn( COLUMN_MODIFICATION_DATE, timestamp );
	}
	public void store(){
		Timestamp current = IWTimestamp.getTimestampRightNow();
		if(getPrimaryKey() == null){
			setCreationDate(current);
		}
		setModificationDate(current);
		super.store();
	}
	
	public Collection getBySupplier(Object supplierPK,int start, int max) throws FinderException, IDOException{
		Table table = new Table(this);
		
		SelectQuery query = new SelectQuery(table);
		
		query.addColumn(new WildCardColumn(table));
		query.addCriteria(new MatchCriteria(table, COLUMN_SUPPLIER, 
				MatchCriteria.EQUALS, 
				supplierPK));
		query.addCriteria(new MatchCriteria(table, COLUMN_VALID, 
				MatchCriteria.EQUALS, 
				"Y"));
		return idoFindPKsByQuery(query, max, start);
	}
	public Collection getByProductId(Object productId,int start, int max) throws FinderException, IDORelationshipException{
		Table table = new Table(this);
		Table product = new Table(Product.class);
		
		SelectQuery query = new SelectQuery(table);
		query.addJoin(product, table);
		query.addColumn(new WildCardColumn(table));
		query.addCriteria(new MatchCriteria(product, ProductBMPBean.getIdColumnName(), 
				MatchCriteria.EQUALS, 
				productId));
		query.addCriteria(new MatchCriteria(table, COLUMN_VALID, 
				MatchCriteria.EQUALS, 
				"Y"));
		return idoFindPKsByQuery(query, max, start);
	}

}
