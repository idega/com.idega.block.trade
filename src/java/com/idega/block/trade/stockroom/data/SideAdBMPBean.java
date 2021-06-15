package com.idega.block.trade.stockroom.data;

import java.util.Collection;

import javax.ejb.FinderException;

import com.idega.data.GenericEntity;
import com.idega.data.IDOLegacyEntity;
import com.idega.data.query.Column;
import com.idega.data.query.MatchCriteria;
import com.idega.data.query.SelectQuery;
import com.idega.data.query.Table;

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
	public static final String AD_NAME = "AD_NAME";
	public static final String AD_INFO = "AD_INFO";
	public static final String AD_BOOKING_URL = "AD_BOOKING_URL";
	
	
	
	public String getEntityName() {
		return "sr_side_ad";
	}

	public void initializeAttributes() {
		this.addAttribute( getIDColumnName() );
		this.addManyToOneRelationship(RELATION_PRODUCT, Product.class);
		this.addAttribute(COLUMN_TYPE, COLUMN_TYPE, true, true, Integer.class);
		this.addAttribute(COLUMN_ORDER, COLUMN_ORDER, true, true, Integer.class);
		
//		ConnectedProduct
		this.addManyToOneRelationship(RELATION_SIDE_PRODUCT, Product.class);
		
//		FileLinkAd
		this.addAttribute(FILE_URL, FILE_URL, true, true, String.class,1024);
		this.addAttribute(AD_URL, AD_URL, true, true, String.class,1024);
		this.addAttribute(AD_NAME, AD_NAME, true, true, String.class,1024);
		this.addAttribute(AD_INFO, AD_INFO, true, true, String.class,1024);
		this.addAttribute(AD_BOOKING_URL, AD_BOOKING_URL, true, true, String.class,1024);
	}
	
	public Integer getType() {
		return (Integer) getColumnValue(COLUMN_TYPE);
	}

	public void setType(Integer type) {
		setColumn(COLUMN_TYPE, type);
	}
	
	public Integer getOrder() {
		Integer order = (Integer) getColumnValue(COLUMN_ORDER);
		if(order == null) {
			return null;
		}
		if(order.intValue() == Integer.MAX_VALUE) {
			return null;
		}
		return order;
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
	
	public Integer getProductId() {
		int id = getIntColumnValue(RELATION_PRODUCT);
		if(id > 0) {
			return new Integer(id);
		}
		return null;
	}
	
	public void store() {
		Integer order = getOrder();
		if(order == null || order.intValue() < 1) {
			this.setOrder(Integer.valueOf(Integer.MAX_VALUE));
		}
		super.store();
	}
	
	
//	ConnectedProduct
	public Product getSideProduct(){
		return (Product) getColumnValue(RELATION_SIDE_PRODUCT);
	}
	
	public Integer getSideProductId() {
		int id = getIntColumnValue(RELATION_SIDE_PRODUCT);
		if(id > 0) {
			return new Integer(id);
		}
		return null;
	}
	
	public void setSideProduct(Integer id) {
		setColumn(RELATION_SIDE_PRODUCT, id);
	}
	
	public void setSideProduct(Product id) {
		setColumn(RELATION_SIDE_PRODUCT, id);
	}
	
//	FileLinkAd
	public String getFileUrl() {
		return (String) getColumnValue(FILE_URL);
	}

	public void setFileUrl(String url) {
		setColumn(FILE_URL, url);
		
	}
	
	public String getAdUrl() {
		return (String) getColumnValue(AD_URL);
	}

	public void setAdUrl(String url) {
		setColumn(AD_URL, url);
		
	}
	
	public String getAdName() {
		return (String) getColumnValue(AD_NAME);
	}

	public void setAdName(String name) {
		setColumn(AD_NAME, name);
		
	}
	
	public String getAdInfo() {
		return (String) getColumnValue(AD_INFO);
	}

	public void setAdInfo(String info) {
		setColumn(AD_INFO, info);
	}
	
	public String getBookingUrl() {
		return (String) getColumnValue(AD_BOOKING_URL);
	}

	public void setBookingUrl(String url) {
		setColumn(AD_BOOKING_URL, url);
	}

	
	// Queries
	public Collection ejbFindSideAdsByProduct(int productId,int start, int max) throws FinderException {
		Table sideAds = new Table(this);
		
		Column pk = new Column(sideAds, getIDColumnName());
		Column order = new Column(sideAds, COLUMN_ORDER);
		
		SelectQuery query = new SelectQuery(sideAds);
		query.addColumn(pk);
		query.addColumn(order);
		
		
		query.addCriteria(new MatchCriteria(new Column(sideAds, RELATION_PRODUCT), MatchCriteria.EQUALS, productId));
		
		query.addOrder(sideAds, SideProductBMPBean.COLUMN_ORDER, true);
		
		if(max > 0) {
			if(start > 0) {
				return idoFindPKsByQuery(query,max,start);
			}else {
				return idoFindPKsByQuery(query,max);
			}
		}
		
		if(start > 0) {
			return idoFindPKsByQuery(query,-1,start);
		}
		return idoFindPKsByQuery(query);
	}

}
