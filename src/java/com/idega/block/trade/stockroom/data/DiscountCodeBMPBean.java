package com.idega.block.trade.stockroom.data;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

import javax.ejb.FinderException;

import com.idega.data.IDOAddRelationshipException;
import com.idega.data.IDOException;
import com.idega.data.IDORelationshipException;
import com.idega.data.IDORemoveRelationshipException;
import com.idega.data.query.Column;
import com.idega.data.query.CountColumn;
import com.idega.data.query.Criteria;
import com.idega.data.query.MatchCriteria;
import com.idega.data.query.SelectQuery;
import com.idega.data.query.Table;
import com.idega.data.query.WildCardColumn;
import com.idega.data.query.output.Output;

public class DiscountCodeBMPBean extends com.idega.data.GenericEntity implements DiscountCode{
	
	private static final long serialVersionUID = -5377085698356733943L;
	
	public static final String TABLE_NAME = "sr_discount_code";
	
	public static final String COLUMN_CODE = "code";
	public static final String COLUMN_TIMES_USED = "times_used";
	public static final String COLUMN_DISCOUNT = "discount";
	public static final String COLUMN_SUPPLIER = "SR_SUPPLIER_ID";
	
	public static final String COLUMN_DELETED = "deleted";
	
	public static final String RELATION_PRODUCT = "sr_product_dc";
	
	public static final String COLUMN_MAX_USAGE = "max_usage";

	public String getEntityName() {
		return TABLE_NAME;
	}

	
	public void initializeAttributes() {
		addAttribute(getIDColumnName());
		addAttribute(COLUMN_CODE, COLUMN_CODE, true, true, String.class, 20);
		addAttribute(COLUMN_TIMES_USED, COLUMN_TIMES_USED, true, true, Integer.class);
		addAttribute(COLUMN_DISCOUNT, COLUMN_DISCOUNT,true,true,Float.class);
		addAttribute(COLUMN_MAX_USAGE, COLUMN_MAX_USAGE,true,true,Integer.class);
		this.addAttribute(COLUMN_DELETED, COLUMN_DELETED, true, true, Boolean.class );
		addOneToOneRelationship(COLUMN_SUPPLIER,Supplier.class);
		addManyToManyRelationShip(Product.class, RELATION_PRODUCT);
	}
	
	public String getCode() {
		return getStringColumnValue(COLUMN_CODE);
	}

	
	public void setCode(String code) {
		setColumn(COLUMN_CODE, code);
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
		query.addCriteria(new MatchCriteria(table, COLUMN_DELETED, 
				MatchCriteria.NOTEQUALS, 
				"Y"));
		return idoFindOnePKByQuery(query);
	}
	
	public Object getByCodeAndProduct(String code,Object productId) throws IDORelationshipException, FinderException{
		Table table = new Table(this);
		Table product = new Table(Product.class);
		
		SelectQuery query = new SelectQuery(table);
		query.addJoin(table, product);
		query.addColumn(new WildCardColumn(table));
		query.addCriteria(new MatchCriteria(product, ProductBMPBean.getIdColumnName(), 
				MatchCriteria.EQUALS, 
				productId));
		query.addCriteria(new MatchCriteria(table, COLUMN_CODE, 
				MatchCriteria.EQUALS, 
				code));
		query.addCriteria(new MatchCriteria(table, COLUMN_DELETED, 
				MatchCriteria.NOTEQUALS, 
				"Y"));
		return idoFindOnePKByQuery(query);
	}

	public int countByProduct(Object productId) throws IDOException{
		Table table = new Table(this);
		Table product = new Table(Product.class);
		
		SelectQuery query = new SelectQuery(table);
		query.addJoin(table, product);
		query.addColumn(new CountColumn("*"));
		query.addCriteria(new MatchCriteria(product, ProductBMPBean.getIdColumnName(), 
				MatchCriteria.EQUALS, 
				productId));
		query.addCriteria(new MatchCriteria(table, COLUMN_DELETED, 
				MatchCriteria.NOTEQUALS, 
				"Y"));
		return idoGetNumberOfRecords(query);
	}
	
	public Collection getBySupplier(Object supplierPK,int start, int max) throws FinderException{
		Table table = new Table(this);
		
		SelectQuery query = new SelectQuery(table);
		query.addColumn(new WildCardColumn(table));
		query.addCriteria(new MatchCriteria(table, COLUMN_SUPPLIER, 
				MatchCriteria.EQUALS, 
				supplierPK));
		query.addCriteria(new MatchCriteria(table, COLUMN_DELETED, 
				MatchCriteria.NOTEQUALS, 
				"Y"));
		return idoFindPKsByQuery(query, max, start);
	}
	public Collection getBySupplierNotUsed(Object supplierPK,int start, int max) throws FinderException{
		Table table = new Table(this);
		
		SelectQuery query = new SelectQuery(table);
		query.addColumn(new WildCardColumn(table));
		query.addCriteria(new MatchCriteria(table, COLUMN_SUPPLIER, 
				MatchCriteria.EQUALS, 
				supplierPK));
		query.addCriteria(new MatchCriteria(table, COLUMN_DELETED, 
				MatchCriteria.NOTEQUALS, 
				"Y"));
		query.addCriteria(new ColumnMatchCriteria(table.getColumn(COLUMN_MAX_USAGE), 
				table.getColumn(COLUMN_TIMES_USED),MatchCriteria.GREATER));
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
	
	public Collection getByProductId(Object productId,int start, int max) throws FinderException, IDORelationshipException{
		Table table = new Table(this);
		Table product = new Table(Product.class);
		
		SelectQuery query = new SelectQuery(table);
		query.addJoin(table, product);
		query.addColumn(new WildCardColumn(table));
		query.addCriteria(new MatchCriteria(product, ProductBMPBean.getIdColumnName(), 
				MatchCriteria.EQUALS, 
				productId));
		query.addCriteria(new MatchCriteria(table, COLUMN_DELETED, 
				MatchCriteria.NOTEQUALS, 
				"Y"));
		return idoFindPKsByQuery(query, max, start);
	}
	

	public float getDiscount() {
		return getFloatColumnValue(COLUMN_DISCOUNT,0);
	}


	public void setDiscount(float discount) {
		setColumn(COLUMN_DISCOUNT, discount);
	}


	public void addProduct(Product product) {
		try {
			idoAddTo(product);
		} catch (IDOAddRelationshipException e) {
			getLogger().log(Level.WARNING, "Falied adding relation to DiscountCode " + getPrimaryKey(), e);
		}
	}


	public Collection getProducts() {
		try {
			Collection related = idoGetRelatedEntities(Product.class);
			if(related != null){
				return related;
			}
		} catch (IDORelationshipException e) {
			getLogger().log(Level.WARNING, "Falied getting related products of DiscountCode "+ getPrimaryKey(), e);
		}
		return Collections.emptyList();
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
	
	public boolean getDeleted(){
		return getBooleanColumnValue(COLUMN_DELETED);
	}
	
	public void setDefaultValues(){
		setTimesUsed(0);
		setDeleted(false);
		setMaxUsage(1);
	}
	public void setDeleted(boolean deleted){
		setColumn(COLUMN_DELETED, deleted);
	}
	
	public void setMaxUsage(int maxUsage){
		setColumn(COLUMN_MAX_USAGE, maxUsage);
	}


	public void removeProduct(Product product) {
		try {
			idoRemoveFrom(product);
		}  catch (IDORemoveRelationshipException e) {
			getLogger().log(Level.WARNING, "Falied removing product "+ product + " from discount code " + getPrimaryKey(), e);
		}
	}

}
