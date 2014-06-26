package com.idega.block.trade.stockroom.data;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.logging.Level;

import com.idega.core.location.data.Address;
import com.idega.data.GenericEntity;
import com.idega.data.IDOAddRelationshipException;
import com.idega.data.IDORelationshipException;
import com.idega.data.IDORemoveRelationshipException;
import com.idega.util.IWTimestamp;
import com.idega.util.text.TextSoap;

/**
 * Title:        IW Trade
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      idega.is
 * @author 2002 - <a href="mailto:gimmi@idega.is">Grimur Jonsson</a>
 * @version 1.0
 */

public class TravelAddressBMPBean extends GenericEntity implements TravelAddress {

  public static final int ADDRESS_TYPE_DEPARTURE = 0;
  public static final int ADDRESS_TYPE_ARRIVAL = 1;

  private static final String COLUMN_ORDER = "ORDER_NR";
  private static final String COLUMN_GROUP_NAME = "GROUP_NAME";
  public static final String RELATION_DISCOUNT_CODE_GROUP = "sr_address_dc_group";
  
  public TravelAddressBMPBean() {
    super();
  }

  public TravelAddressBMPBean(int id) throws SQLException {
    super(id);
  }

  public void initializeAttributes() {
    this.addAttribute(getIDColumnName());
//    this.addAttribute(getColumnNameAddressId(), "addressId", true, true, Integer.class);
    this.addAttribute(getColumnNameTime(), "time", true, true, Timestamp.class);
    this.addAttribute(getColumnNameAddressTypeId(), "addressutypa", true, true, Integer.class);
    this.addAttribute(getColumnNameRefillStock() , "fylla a byrgdir", true, true, Boolean.class);
    addAttribute(COLUMN_ORDER, "Order", Integer.class);
    addAttribute(COLUMN_GROUP_NAME, "group_name", String.class, 50);

    this.addOneToOneRelationship(getColumnNameAddressId(), Address.class);
    this.addManyToManyRelationShip( Product.class, "SR_PRODUCT_SR_ADDRESS" );
    this.addManyToManyRelationShip( DiscountCodeGroup.class, RELATION_DISCOUNT_CODE_GROUP );
  }

  public String getEntityName() {
    return getTravelAddressTableName();
  }

  public int getAddressId() {
    return getIntColumnValue(getColumnNameAddressId());
  }

  public Address getAddress() {
	  return (Address) getColumnValue(getColumnNameAddressId());
  }

  public String getTimeString() {
    IWTimestamp timestamp = new IWTimestamp(getTime());
    return TextSoap.addZero(timestamp.getHour())+":"+TextSoap.addZero(timestamp.getMinute());
  }
  
  public String getName() {
    return getStreetName()+" - "+getTimeString();
  }

  public String getStreetName() {
	  Address a = getAddress();
	  String tmp = "";
	  if (a != null) {
		  tmp = a.getStreetNameOriginal();
		  if (tmp == null) {
			  tmp = a.getStreetName();
		  }
	  }
	  return tmp;
  }

  public Timestamp getTime() {
    return (Timestamp) getColumnValue(getColumnNameTime());
  }

  public int getAddressType() {
    return getIntColumnValue(getColumnNameAddressTypeId());
  }

  public boolean getRefillStock() {
    return getBooleanColumnValue(getColumnNameRefillStock());
  }

  public void setAddressId(int addressId) {
    setColumn(getColumnNameAddressId(), addressId);
  }

  public void setAddress(Address address) {
    setAddressId(address.getID());
  }

  public void setTime(Timestamp stamp) {
    setColumn(getColumnNameTime(), stamp);
  }

  public void setTime(IWTimestamp stamp) {
    setTime(stamp.getTimestamp());
  }

  public void setAddressTypeId(int id) {
    setColumn(getColumnNameAddressTypeId(), id);
  }

  public void setRefillStock(boolean replenish) {
    setColumn(getColumnNameRefillStock(), replenish);
  }
  
  public void setGroupName(String groupName) {
	  setColumn(COLUMN_GROUP_NAME, groupName);
  }
  
  public String getGroupName() {
	  return getStringColumnValue(COLUMN_GROUP_NAME);
  }

  public void setOrder(int order) {
	  setColumn(COLUMN_ORDER, order);
  }
  
  public int getOrder() {
	  return getIntColumnValue(COLUMN_ORDER);
  }
  

  public static String getTravelAddressTableName() { return "SR_ADDRESS";}
  public static String getColumnNameAddressId() { return "IC_ADDRESS_ID";}
  public static String getColumnNameTime() { return "DP_AR_TIME";}
  public static String getColumnNameAddressTypeId() {return "SR_ADDRESS_TYPE_ID";}
  public static String getColumnNameRefillStock() {return "REFILL_STOCK";}

  	public Collection getDiscountCodeGroups(){
	    try {
			return this.idoGetRelatedEntities(DiscountCodeGroup.class);
		} catch (IDORelationshipException e) {
			getLogger().log(Level.WARNING, "Failed getting discount code groups of product " + getPrimaryKey(), e);
		}
		return Collections.EMPTY_LIST;
	  }

	  public void removeDiscountCodeGroup(DiscountCodeGroup discountCodeGroup){
	    try {
			this.idoRemoveFrom(discountCodeGroup);
		} catch (IDORemoveRelationshipException e) {
			getLogger().log(Level.WARNING, "Failed removing discount code group "+discountCodeGroup +" from product " + getPrimaryKey(), e);
		}
	  }

	  public void addDiscountCodeGroup(DiscountCodeGroup discountCodeGroup){
	    try {
			this.idoAddTo(discountCodeGroup);
		} catch (IDOAddRelationshipException e) {
			getLogger().log(Level.WARNING, "Failed adding discount code group "+discountCodeGroup +" to product " + getPrimaryKey(), e);
		}
	  }
}


