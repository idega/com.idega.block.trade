package com.idega.block.trade.stockroom.data;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.FinderException;

import com.idega.data.IDOException;
import com.idega.data.IDORelationshipException;
import com.idega.data.query.Column;
import com.idega.data.query.CountColumn;
import com.idega.data.query.Criteria;
import com.idega.data.query.MatchCriteria;
import com.idega.data.query.SelectQuery;
import com.idega.data.query.Table;
import com.idega.data.query.WildCardColumn;
import com.idega.data.query.output.Output;
import com.idega.util.IWTimestamp;

public class DiscountCodeBMPBean extends com.idega.data.GenericEntity implements DiscountCode{
	private static final long serialVersionUID = 6072873614458635567L;


	public static final String TABLE_NAME = "sr_discount_code";
	
	
	public static final String COLUMN_CODE = "code";
	public static final String COLUMN_DISCOUNT_CODE_GROUP = "sr_dc_group_id";
	public static final String COLUMN_TIMES_USED = "times_used";
	public static final String COLUMN_VALID = "valid";
	public static final String COLUMN_CREATION_DATE = "CREATION_DATE";
	public static final String COLUMN_MODIFICATION_DATE = "MODIFICATION_DATE";

	public String getCode() {
		return getStringColumnValue(COLUMN_CODE);
	}

	public void setCode(String code) {
		setColumn(COLUMN_CODE, code);
	}

	public String getEntityName() {
		return TABLE_NAME;
	}

	public void initializeAttributes() {
		addAttribute(getIDColumnName());
		addAttribute(COLUMN_TIMES_USED, COLUMN_TIMES_USED, true, true, Integer.class);
		addAttribute(COLUMN_CODE, COLUMN_CODE, true, true, String.class);
		addOneToOneRelationship(COLUMN_DISCOUNT_CODE_GROUP, DiscountCodeGroup.class);
		this.addAttribute(COLUMN_VALID, COLUMN_VALID, true, true, Boolean.class );
		this.addAttribute( COLUMN_CREATION_DATE, COLUMN_CREATION_DATE, true, true, Timestamp.class );
	    this.addAttribute( COLUMN_MODIFICATION_DATE, COLUMN_MODIFICATION_DATE, true, true, Timestamp.class );
	}
	
	public boolean isUsed() {
		return getTimesUsed() > 0;
	}

	
	public void setUsed(boolean used) {
		setTimesUsed(getTimesUsed() + 1);
	}
	
	public int getTimesUsed() {
		return getIntColumnValue(COLUMN_TIMES_USED, 0);
	}

	
	public void setTimesUsed(int times) {
		setColumn(COLUMN_TIMES_USED, times);
	}
	
	public Object getByCode(String code) throws FinderException{
		Table table = new Table(this);
		
		SelectQuery query = new SelectQuery(table);
		query.addColumn(new WildCardColumn(table));
		query.addCriteria(new MatchCriteria(table, COLUMN_CODE, 
				MatchCriteria.EQUALS, 
				code));
		query.addCriteria(new MatchCriteria(table, COLUMN_VALID, 
				MatchCriteria.EQUALS, 
				"Y"));
		return idoFindOnePKByQuery(query);
	}
	public Object getByCodeGroupAndCode(Object codeGroupPk, String code) throws FinderException{
		Table table = new Table(this);
		
		SelectQuery query = new SelectQuery(table);
		query.addColumn(new WildCardColumn(table));
		query.addCriteria(new MatchCriteria(table, COLUMN_CODE, 
				MatchCriteria.EQUALS, 
				code));
		query.addCriteria(new MatchCriteria(table, COLUMN_DISCOUNT_CODE_GROUP, 
				MatchCriteria.EQUALS, 
				codeGroupPk));
		query.addCriteria(new MatchCriteria(table, COLUMN_VALID, 
				MatchCriteria.EQUALS, 
				"Y"));
		return idoFindOnePKByQuery(query);
	}
	
	public Object getByCodeAndProduct(String code,Object productId) throws IDORelationshipException, FinderException{
		Table table = new Table(this);
		Table dcGroup = new Table(DiscountCodeGroup.class);
		Table product = new Table(Product.class);
		Table departures = new Table(TravelAddress.class);
		
		SelectQuery query = new SelectQuery(table);
		query.addJoin(table, dcGroup);
		query.addJoin(dcGroup, product);
		query.addColumn(new WildCardColumn(table));
		MatchCriteria productMatch = new MatchCriteria(product, ProductBMPBean.getIdColumnName(), 
				MatchCriteria.EQUALS, 
				productId);
		
		query.addCriteria(productMatch);
		query.addCriteria(new MatchCriteria(table, COLUMN_CODE, 
				MatchCriteria.EQUALS, 
				code));
		query.addCriteria(new MatchCriteria(table, COLUMN_VALID, 
				MatchCriteria.EQUALS, 
				"Y"));
		query.addCriteria(new MatchCriteria(dcGroup, DiscountCodeGroupBMPBean.COLUMN_VALID, 
				MatchCriteria.EQUALS, 
				"Y"));
		try{
			return idoFindOnePKByQuery(query);
		}catch (FinderException e) {
			query = new SelectQuery(table);
			query.addJoin(table, dcGroup);
			query.addJoin(product, departures);
			query.addJoin(departures, dcGroup);
			query.addColumn(new WildCardColumn(table));
			query.addCriteria(new MatchCriteria(product, ProductBMPBean.getIdColumnName(), 
					MatchCriteria.EQUALS, 
					productId));
			query.addCriteria(new MatchCriteria(table, COLUMN_CODE, 
					MatchCriteria.EQUALS, 
					code));
			query.addCriteria(new MatchCriteria(table, COLUMN_VALID, 
					MatchCriteria.EQUALS, 
					"Y"));
			query.addCriteria(new MatchCriteria(dcGroup, DiscountCodeGroupBMPBean.COLUMN_VALID, 
					MatchCriteria.EQUALS, 
					"Y"));
			return idoFindOnePKByQuery(query);
		}
	}
	public Object getByCodeAndProductAndDeparture(String code,
			Object productId, Object departureId)  throws IDORelationshipException, FinderException{
		Table table = new Table(this);
		Table dcGroup = new Table(DiscountCodeGroup.class);
		Table product = new Table(Product.class);
		Table departures = new Table(TravelAddress.class);
		
		SelectQuery query = new SelectQuery(table);
		query.addJoin(table, dcGroup);
		query.addJoin(dcGroup, product);
		query.addColumn(new WildCardColumn(table));
		MatchCriteria productMatch = new MatchCriteria(product, ProductBMPBean.getIdColumnName(), 
				MatchCriteria.EQUALS, 
				productId);
		
		query.addCriteria(productMatch);
		query.addCriteria(new MatchCriteria(table, COLUMN_CODE, 
				MatchCriteria.EQUALS, 
				code));
		query.addCriteria(new MatchCriteria(table, COLUMN_VALID, 
				MatchCriteria.EQUALS, 
				"Y"));
		query.addCriteria(new MatchCriteria(dcGroup, DiscountCodeGroupBMPBean.COLUMN_VALID, 
				MatchCriteria.EQUALS, 
				"Y"));
		try{
			return idoFindOnePKByQuery(query);
		}catch (FinderException e) {
			query = new SelectQuery(table);
			query.addJoin(table, dcGroup);
			query.addJoin(product, departures);
			query.addJoin(departures, dcGroup);
			query.addColumn(new WildCardColumn(table));
			query.addCriteria(new MatchCriteria(product, ProductBMPBean.getIdColumnName(), 
					MatchCriteria.EQUALS, 
					productId));
			query.addCriteria(new MatchCriteria(table, COLUMN_CODE, 
					MatchCriteria.EQUALS, 
					code));
			query.addCriteria(new MatchCriteria(departures, "SR_ADDRESS_ID", 
					MatchCriteria.EQUALS, 
					departureId));
			query.addCriteria(new MatchCriteria(table, COLUMN_VALID, 
					MatchCriteria.EQUALS, 
					"Y"));
			query.addCriteria(new MatchCriteria(dcGroup, DiscountCodeGroupBMPBean.COLUMN_VALID, 
					MatchCriteria.EQUALS, 
					"Y"));
			return idoFindOnePKByQuery(query);
		}
	}

	public int countByProduct(Object productId) throws IDOException{
		Table table = new Table(this);
		Table dcGroup = new Table(DiscountCodeGroup.class);
		Table product = new Table(Product.class);
		
		SelectQuery query = new SelectQuery(table);
		query.addJoin(table, dcGroup);
		query.addJoin(dcGroup, product);
		query.addColumn(new CountColumn("*"));
		query.addCriteria(new MatchCriteria(product, ProductBMPBean.getIdColumnName(), 
				MatchCriteria.EQUALS, 
				productId));
		query.addCriteria(new MatchCriteria(table, COLUMN_VALID, 
				MatchCriteria.EQUALS, 
				"Y"));
		query.addCriteria(new MatchCriteria(dcGroup, DiscountCodeGroupBMPBean.COLUMN_VALID, 
				MatchCriteria.EQUALS, 
				"Y"));
		return idoGetNumberOfRecords(query);
	}
	
	public int countByProductDepartures(Object productId) throws IDOException{
		Table table = new Table(this);
		Table dcGroup = new Table(DiscountCodeGroup.class);
		Table product = new Table(Product.class);
		Table departures = new Table(TravelAddress.class);
		
		SelectQuery query = new SelectQuery(table);
		query.addJoin(table, dcGroup);
		query.addJoin(product, departures);
		query.addJoin(departures, dcGroup);
		query.addColumn(new CountColumn("distinct " + getIDColumnName()));
		query.addCriteria(new MatchCriteria(product, ProductBMPBean.getIdColumnName(), 
				MatchCriteria.EQUALS, 
				productId));
		query.addCriteria(new MatchCriteria(table, COLUMN_VALID, 
				MatchCriteria.EQUALS, 
				"Y"));
		query.addCriteria(new MatchCriteria(dcGroup, DiscountCodeGroupBMPBean.COLUMN_VALID, 
				MatchCriteria.EQUALS, 
				"Y"));
		return idoGetNumberOfRecords(query);
	}
	public int countByProductDeparture(Object productId,Object departureId) throws IDOException{
		Table table = new Table(this);
		Table dcGroup = new Table(DiscountCodeGroup.class);
		Table product = new Table(Product.class);
		Table departures = new Table(TravelAddress.class);
		
		SelectQuery query = new SelectQuery(table);
		query.addJoin(table, dcGroup);
		query.addJoin(product, departures);
		query.addJoin(departures, dcGroup);
		query.addColumn(new CountColumn("distinct " + getIDColumnName()));
		query.addCriteria(new MatchCriteria(departures, "SR_ADDRESS_ID", 
				MatchCriteria.EQUALS, 
				departureId));
		query.addCriteria(new MatchCriteria(table, COLUMN_VALID, 
				MatchCriteria.EQUALS, 
				"Y"));
		query.addCriteria(new MatchCriteria(product, ProductBMPBean.getIdColumnName(), 
				MatchCriteria.EQUALS, 
				productId));
		query.addCriteria(new MatchCriteria(dcGroup, DiscountCodeGroupBMPBean.COLUMN_VALID, 
				MatchCriteria.EQUALS, 
				"Y"));
		return idoGetNumberOfRecords(query);
	}
	
	public int countBySupplierAndCode(Object supplierPk, String code) throws IDOException{
		Table table = new Table(this);
		Table dcGroup = new Table(DiscountCodeGroup.class);
		
		SelectQuery query = new SelectQuery(table);
		query.addJoin(table, dcGroup);
		query.addColumn(new CountColumn("*"));
		query.addCriteria(new MatchCriteria(table, COLUMN_CODE, 
				MatchCriteria.EQUALS, 
				code));
		query.addCriteria(new MatchCriteria(dcGroup, DiscountCodeGroupBMPBean.COLUMN_SUPPLIER, 
				MatchCriteria.EQUALS, 
				supplierPk));
		query.addCriteria(new MatchCriteria(table, COLUMN_VALID, 
				MatchCriteria.EQUALS, 
				"Y"));
		query.addCriteria(new MatchCriteria(dcGroup, DiscountCodeGroupBMPBean.COLUMN_VALID, 
				MatchCriteria.EQUALS, 
				"Y"));
		return idoGetNumberOfRecords(query);
	}
	public int countByCodeGroupAndCode(Object codeGroupPk, String code) throws IDOException{
		Table table = new Table(this);
		
		SelectQuery query = new SelectQuery(table);
		query.addColumn(new CountColumn("*"));
		query.addCriteria(new MatchCriteria(table, COLUMN_CODE, 
				MatchCriteria.EQUALS, 
				code));
		query.addCriteria(new MatchCriteria(table, COLUMN_DISCOUNT_CODE_GROUP, 
				MatchCriteria.EQUALS, 
				codeGroupPk));
		query.addCriteria(new MatchCriteria(table, COLUMN_VALID, 
				MatchCriteria.EQUALS, 
				"Y"));
		return idoGetNumberOfRecords(query);
	}
	
	public Collection getBySupplier(Object supplierPK,int start, int max) throws FinderException, IDOException{
		Table table = new Table(this);
		Table dcGroup = new Table(DiscountCodeGroup.class);
		
		SelectQuery query = new SelectQuery(table);
		query.addJoin(table, dcGroup);
		
		query.addColumn(new WildCardColumn(table));
		query.addCriteria(new MatchCriteria(dcGroup, DiscountCodeGroupBMPBean.COLUMN_SUPPLIER, 
				MatchCriteria.EQUALS, 
				supplierPK));
		query.addCriteria(new MatchCriteria(table, COLUMN_VALID, 
				MatchCriteria.EQUALS, 
				"Y"));
		query.addCriteria(new MatchCriteria(dcGroup, DiscountCodeGroupBMPBean.COLUMN_VALID, 
				MatchCriteria.EQUALS, 
				"Y"));
		return idoFindPKsByQuery(query, max, start);
	}
	public Collection getBySupplierNotUsed(Object supplierPK,int start, int max) throws FinderException, IDOException{
		Table table = new Table(this);
		Table dcGroup = new Table(DiscountCodeGroup.class);
		
		SelectQuery query = new SelectQuery(table);
		query.addJoin(table, dcGroup);
		query.addColumn(new WildCardColumn(table));
		query.addCriteria(new MatchCriteria(dcGroup, DiscountCodeGroupBMPBean.COLUMN_SUPPLIER, 
				MatchCriteria.EQUALS, 
				supplierPK));
		query.addCriteria(new MatchCriteria(table, COLUMN_VALID, 
				MatchCriteria.EQUALS, 
				"Y"));
		query.addCriteria(new MatchCriteria(dcGroup, DiscountCodeGroupBMPBean.COLUMN_VALID, 
				MatchCriteria.EQUALS, 
				"Y"));
		query.addCriteria(new MatchCriteria(dcGroup, DiscountCodeGroupBMPBean.COLUMN_VALID, 
				MatchCriteria.EQUALS, 
				"Y"));
		query.addCriteria(new ColumnMatchCriteria(dcGroup.getColumn(DiscountCodeGroupBMPBean.COLUMN_MAX_USAGE), 
				table.getColumn(COLUMN_TIMES_USED),MatchCriteria.GREATER));
		return idoFindPKsByQuery(query, max, start);
	}
	public Collection getByProductId(Object productId,int start, int max) throws FinderException, IDORelationshipException{
		Table table = new Table(this);
		Table dcGroup = new Table(DiscountCodeGroup.class);
		Table product = new Table(Product.class);
		
		SelectQuery query = new SelectQuery(table);
		query.addJoin(table, dcGroup);
		query.addJoin(dcGroup, product);
		query.addColumn(new WildCardColumn(table));
		query.addCriteria(new MatchCriteria(product, ProductBMPBean.getIdColumnName(), 
				MatchCriteria.EQUALS, 
				productId));
		query.addCriteria(new MatchCriteria(table, COLUMN_VALID, 
				MatchCriteria.EQUALS, 
				"Y"));
		query.addCriteria(new MatchCriteria(dcGroup, DiscountCodeGroupBMPBean.COLUMN_VALID, 
				MatchCriteria.EQUALS, 
				"Y"));
		return idoFindPKsByQuery(query, max, start);
	}
	
	public class ColumnMatchCriteria extends Criteria{

		private Column column1;
		private Column column2;
		private String matchType;
		public ColumnMatchCriteria(Column column1,Column column2,String matchType){
			this.column1 = column1;
			this.column2 = column2;
			this.matchType = matchType;
		}
		public void write(Output out) {
			out.print(this.column1).print(' ').print(this.matchType).print(' ').print(this.column2);
		}

		public Set getTables() {
			Set s = new HashSet();
			s.add(this.column1.getTable());
			s.add(this.column2.getTable());
			return s;
		}

	}
	
	public boolean isValid(){
		return getBooleanColumnValue(COLUMN_VALID);
	}
	
	public void setDefaultValues(){
		setValid(true);
		setTimesUsed(0);
	}
	public void setValid(boolean valid){
		setColumn(COLUMN_VALID, valid);
	}

	public DiscountCodeGroup getDiscountCodeGroup() {
		return (DiscountCodeGroup) getColumnValue(COLUMN_DISCOUNT_CODE_GROUP);
	}

	public void setDiscountCodeGroup(DiscountCodeGroup discountCodeGroup) {
		setColumn(COLUMN_DISCOUNT_CODE_GROUP, discountCodeGroup);
	}
	public void setDiscountCodeGroupPk(Object discountCodeGroupPk) {
		setColumn(COLUMN_DISCOUNT_CODE_GROUP, discountCodeGroupPk);
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

}
