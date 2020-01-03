package com.idega.block.trade.stockroom.data;

import javax.ejb.FinderException;

import com.idega.data.GenericEntity;
import com.idega.data.IDOLegacyEntity;
import com.idega.data.query.Column;
import com.idega.data.query.MatchCriteria;
import com.idega.data.query.SelectQuery;
import com.idega.data.query.Table;

//TODO: rename to sideads and split to multiple
public class SideProductBMPBean extends GenericEntity implements SideProduct, IDOLegacyEntity {
	private static final long serialVersionUID = 4260205997684107323L;
	public static final String RELATION_PRODUCT = ProductBMPBean.getIdColumnName();
	public static final String RELATION_SIDE_PRODUCT = "SIDE_PRODUCT_ID"; 
	public static final String COLUMN_ORDER = "ORDER_NUMBER"; 
	
	public String getEntityName() {
		return "sr_side_product";
	}

	public void initializeAttributes() {
		this.addAttribute( getIDColumnName() );
		this.addManyToOneRelationship(RELATION_PRODUCT, Product.class);
		this.addManyToOneRelationship(RELATION_SIDE_PRODUCT, Product.class);
		this.addAttribute(COLUMN_ORDER, COLUMN_ORDER, true, true, Integer.class);
	}
	
	public void setDefaultValues() {
		Integer order = getOrder();
		if(order == null) {
			return;
		}
		if(order.intValue() == Integer.MAX_VALUE) {
			initializeColumnValue(COLUMN_ORDER,null);
		}
	}
	
	public Integer getOrder() {
		return (Integer) getColumnValue(COLUMN_ORDER);
	}
	
	public void setOrder(Integer order) {
		setColumn(COLUMN_ORDER, order);
	}
	
	public void setProduct(Integer id) {
		setColumn(RELATION_PRODUCT, id);
	}
	
	public void setSideProduct(Integer id) {
		setColumn(RELATION_SIDE_PRODUCT, id);
	}
	
	public void setProduct(Product id) {
		setColumn(RELATION_PRODUCT, id);
	}
	
	public void setSideProduct(Product id) {
		setColumn(RELATION_SIDE_PRODUCT, id);
	}
	
	public Product getProduct(){
		return (Product) getColumnValue(RELATION_PRODUCT);
	}
	public Product getSideProduct(){
		return (Product) getColumnValue(RELATION_SIDE_PRODUCT);
	}
	
	public Object ejbFindSideProduct(int productId,int sideProductId) throws FinderException {
		Table table = new Table(this);
		
		Column pk = new Column(table, getIDColumnName());
		SelectQuery query = new SelectQuery(table);
		query.addColumn(pk);
		query.addCriteria(new MatchCriteria(new Column(table, RELATION_PRODUCT), MatchCriteria.EQUALS, productId));
		query.addCriteria(new MatchCriteria(new Column(table, RELATION_SIDE_PRODUCT), MatchCriteria.EQUALS, sideProductId));
		return idoFindOnePKByQuery(query);
	}
	
	public void store() {
		Integer order = getOrder();
		if(order == null || order.intValue() < 1) {
			order = Integer.valueOf(Integer.MAX_VALUE);
		}
		super.store();
	}
	
}
