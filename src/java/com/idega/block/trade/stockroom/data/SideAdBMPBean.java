package com.idega.block.trade.stockroom.data;

import com.idega.data.GenericEntity;
import com.idega.data.IDOLegacyEntity;

//TODO: rename to sideads and split to multiple
public class SideAdBMPBean extends GenericEntity implements 
		IDOLegacyEntity,
		SideAd,
		ConnectedProduct,
		FileLinkAd{
	private static final long serialVersionUID = 4260205997684107323L;
	public static final String RELATION_PRODUCT = ProductBMPBean.getIdColumnName();
	public static final String COLUMN_TYPE = "TYPE";
	public static final String COLUMN_ORDER = "ORDER_NUMBER";
	
//	ConnectedProduct
	public static final String RELATION_SIDE_PRODUCT = "SIDE_PRODUCT_ID";
	
//	FileLinkAd
	public static final String FILE_URL = "FILE_URL"; 
	public static final String AD_URL = "AD_URL"; 
	
	public String getEntityName() {
		return "sr_side_ad";
	}

	public void initializeAttributes() {
		this.addAttribute( getIDColumnName() );
		this.addManyToOneRelationship(RELATION_PRODUCT, Product.class);
		this.addAttribute(COLUMN_ORDER, COLUMN_TYPE, true, true, Integer.class);
		this.addAttribute(COLUMN_ORDER, COLUMN_ORDER, true, true, Integer.class);
		
//		ConnectedProduct
		this.addManyToOneRelationship(RELATION_SIDE_PRODUCT, Product.class);
		
//		FileLinkAd
		this.addAttribute(FILE_URL, FILE_URL, true, true, String.class,1024);
		this.addAttribute(AD_URL, AD_URL, true, true, String.class,1024);
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
	
	public Integer getType() {
		return (Integer) getColumnValue(COLUMN_TYPE);
	}

	public void setType(Integer type) {
		setColumn(COLUMN_TYPE, type);
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
	
	public void setProduct(Product id) {
		setColumn(RELATION_PRODUCT, id);
	}
	
	public Product getProduct(){
		return (Product) getColumnValue(RELATION_PRODUCT);
	}
	
	public void store() {
		Integer order = getOrder();
		if(order == null || order.intValue() < 1) {
			order = Integer.valueOf(Integer.MAX_VALUE);
		}
		super.store();
	}
	
	
//	ConnectedProduct
	public Product getSideProduct(){
		return (Product) getColumnValue(RELATION_SIDE_PRODUCT);
	}
	
	public void setSideProduct(Integer id) {
		setColumn(RELATION_SIDE_PRODUCT, id);
	}
	
	public void setSideProduct(Product id) {
		setColumn(RELATION_SIDE_PRODUCT, id);
	}
	
//	FileLinkAd
	public String getAdUrl() {
		return (String) getColumnValue(AD_URL);
	}

	public void setAdUrl(String url) {
		setColumn(AD_URL, url);
		
	}
	
	public String getFileUrl() {
		return (String) getColumnValue(FILE_URL);
	}

	public void setFileUrl(String url) {
		setColumn(FILE_URL, url);
		
	}

}
