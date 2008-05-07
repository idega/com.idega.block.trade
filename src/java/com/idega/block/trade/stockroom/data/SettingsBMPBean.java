package com.idega.block.trade.stockroom.data;

import java.sql.SQLException;

import com.idega.block.trade.business.CurrencyBusiness;
import com.idega.data.GenericEntity;
import com.idega.data.IDOAddRelationshipException;


/**
 * Title:        idegaWeb TravelBooking
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      idega
 * @author <a href="mailto:gimmi@idega.is">Grimur Jonsson</a>
 * @version 1.0
 */

public class SettingsBMPBean extends GenericEntity implements Settings{

  private static final String COLUMN_NAME_DOUBLE_CONFIRMATION = "DOUBLE_CONFIRMATION";
  private static final String COLUMN_NAME_EMAIL_AFTER_ONLINE = "EMAIL_ONLINE";
  private static final String COLUMN_NAME_GLOBAL_ONLINE_DISCOUNT = "GLOBAL_ONLINE_DISCOUNT";
  private static final String COLUMN_NAME_CURRENCY_ID = "CURRENCY_ID";
  private static final String ENTITY_NAME = "TR_SETTINGS";


  public SettingsBMPBean(){
  }

  public SettingsBMPBean(int id)throws SQLException{
    super(id);
  }

  public void initializeAttributes() {
    this.addAttribute(getIDColumnName());
    this.addAttribute(COLUMN_NAME_DOUBLE_CONFIRMATION, "double confirmation", true, true, Boolean.class);
    this.addAttribute(COLUMN_NAME_EMAIL_AFTER_ONLINE, "receive email after online booking", true, true, Boolean.class);
    this.addAttribute(COLUMN_NAME_CURRENCY_ID, "currency id", true, true, Integer.class);
    this.addAttribute(COLUMN_NAME_GLOBAL_ONLINE_DISCOUNT, "online discount", true, true, Float.class);

    this.addManyToManyRelationShip(Supplier.class);
    this.addManyToManyRelationShip(Reseller.class);
  }

  public String getEntityName() {
    return ENTITY_NAME;
  }

  public void setDefaultValues() {
    this.setIfDoubleConfirmation(true);
    this.setIfEmailAfterOnlineBooking(false);
  }
  
  public void setSupplier(Supplier supplier) throws IDOAddRelationshipException {
	  idoAddTo(supplier);
  }
  
  public void setReseller(Reseller reseller) throws IDOAddRelationshipException {
	  this.idoAddTo(reseller);
  }

  /** Getters */
  public boolean getIfDoubleConfirmation() {
    return getBooleanColumnValue(COLUMN_NAME_DOUBLE_CONFIRMATION);
  }

  public boolean getIfEmailAfterOnlineBooking() {
    return getBooleanColumnValue(COLUMN_NAME_EMAIL_AFTER_ONLINE);
  }

  public float getGlobalOnlineDiscount() {
	  return getFloatColumnValue(COLUMN_NAME_GLOBAL_ONLINE_DISCOUNT, 0);
  }
  
  public int getCurrencyId() {
    int currId = getIntColumnValue(COLUMN_NAME_CURRENCY_ID);
    if (currId < 1) {
      currId = CurrencyBusiness.getCurrencyHolder("ISK").getCurrencyID();
      this.setCurrencyId(currId);
      this.store();
      System.out.println("[SettingBMPBean] Backwards compatability : setting currencyId = "+currId);
    }else if (currId == 1) {
      currId = CurrencyBusiness.getCurrencyHolder(CurrencyBusiness.defaultCurrency).getCurrencyID();
      this.setCurrencyId(currId);
      this.store();
      System.out.println("[SettingBMPBean] Backwards compatability : changing currencyId from 1 to "+currId);
    }
    return currId;
  }


  /** Setters */
  public void setIfDoubleConfirmation(boolean doubleConfirmation) {
    setColumn(COLUMN_NAME_DOUBLE_CONFIRMATION, doubleConfirmation);
  }

  public void setIfEmailAfterOnlineBooking(boolean emailAfterOnlineBooking) {
    setColumn(COLUMN_NAME_EMAIL_AFTER_ONLINE, emailAfterOnlineBooking);
  }

  public void setCurrencyId(int currencyId) {
    setColumn(COLUMN_NAME_CURRENCY_ID, currencyId);
  }
  
  public void setGlobalOnlineDiscount(float disc) {
	  setColumn(COLUMN_NAME_GLOBAL_ONLINE_DISCOUNT, disc);
  }

  /** Finders */
}