package com.idega.block.trade.stockroom.data;

import com.idega.data.*;
import com.idega.core.data.*;
import com.idega.block.trade.stockroom.business.ResellerManager;
import com.idega.block.employment.data.EmployeeGroup;
import com.idega.core.accesscontrol.data.PermissionGroup;

import java.util.List;
import java.util.Vector;
import java.sql.SQLException;

/**
 * Title:        idegaWeb TravelBooking
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      idega
 * @author <a href="mailto:gimmi@idega.is">Grimur Jonsson</a>
 * @version 1.0
 */

public class ResellerBMPBean extends com.idega.data.TreeableEntityBMPBean implements com.idega.block.trade.stockroom.data.Reseller {
  private String newName;

  public ResellerBMPBean() {
    super();
  }

  public ResellerBMPBean(int id) throws SQLException{
    super(id);
  }

  public void initializeAttributes() {
    addAttribute(getIDColumnName());
    addAttribute(getColumnNameName(), "name", true, true, String.class);
    addAttribute(getColumnNameDescription(), "L�sing", true, true, String.class);
    addAttribute(getColumnNameGroupID(),"H�pur", true, true, Integer.class, "many_to_one", ResellerStaffGroup.class);
    addAttribute(getColumnNameIsValid(),"is valid", true, true, Boolean.class);
    addAttribute(getColumnNameReferenceNumber(), "Tilvisunarn�mer", true, true, String.class);

    this.addManyToManyRelationShip(Address.class);
    this.addManyToManyRelationShip(Email.class);
    this.addManyToManyRelationShip(Phone.class);

  }
  public String getEntityName() {
    return getResellerTableName();
  }

  public void setDefaultValues() {
    this.setIsValid(true);
  }

  public String getName() {
    return getStringColumnValue(getColumnNameName());
  }

  public void setName(String name) {
    newName = name;

    setColumn(getColumnNameName(), name);
  }

  public String getDescription() {
    return getStringColumnValue(getColumnNameDescription());
  }

  public void setDescription(String description) {
    setColumn(getColumnNameDescription(), description);
  }

  public void setGroupId(int id){
    setColumn(getColumnNameGroupID(), id);
  }

  public int getGroupId(){
    return getIntColumnValue(getColumnNameGroupID());
  }

  public List getPhones() throws SQLException {
    return EntityFinder.findRelated(this,com.idega.core.data.PhoneBMPBean.getStaticInstance(Phone.class));
  }

  public List getPhones(int PhoneTypeId) throws SQLException{
    Vector phones = new Vector();
    List allPhones = getPhones();
    if (allPhones != null) {
      Phone temp = null;
      for (int i = 0; i < allPhones.size(); i++) {
        temp = (Phone) allPhones.get(i);
        if (temp.getPhoneTypeId() == PhoneTypeId) {
          phones.add(temp);
        }
      }
    }
    return phones;
  }

  public List getEmails() throws SQLException {
    return EntityFinder.findRelated(this,com.idega.core.data.EmailBMPBean.getStaticInstance(Email.class));
  }

  public Email getEmail() throws SQLException{
    Email returner = null;
    List list = getEmails();
    if (list != null) {
      if (list.size() > 0) {
        returner = (Email) list.get(list.size() -1);
      }
    }
    return returner;
  }

  public void setIsValid(boolean isValid) {
    setColumn(getColumnNameIsValid(), isValid);
  }

  public boolean getIsValid() {
    return getBooleanColumnValue(getColumnNameIsValid());
  }

  public String getReferenceNumber() {
    return getStringColumnValue(getColumnNameReferenceNumber());
  }

  public void setReferenceNumber(String key) {
    setColumn(getColumnNameReferenceNumber(), key);
  }

  public Address getAddress() throws SQLException {
    Address address = null;
    List addr = getAddresses();
    if (addr !=null) {
      address = (Address) addr.get(addr.size() -1);
    }
    return address;
  }

  public List getAddresses() throws SQLException{
    return EntityFinder.findRelated(this,com.idega.core.data.AddressBMPBean.getStaticInstance(Address.class));
  }

  public List getHomePhone() throws SQLException {
    return getPhones(com.idega.core.data.PhoneBMPBean.getHomeNumberID());
  }

  public List getFaxPhone() throws SQLException {
    return getPhones(com.idega.core.data.PhoneBMPBean.getFaxNumberID());
  }

  public static Reseller[] getValidResellers() throws SQLException {
    return (Reseller[]) com.idega.block.trade.stockroom.data.ResellerBMPBean.getStaticInstance(Reseller.class).findAllByColumnOrdered(com.idega.block.trade.stockroom.data.ResellerBMPBean.getColumnNameIsValid(),"Y",com.idega.block.trade.stockroom.data.ResellerBMPBean.getColumnNameName());
  }

  public Reseller getParent() {
    return (Reseller) getParentEntity();
  }

  public void delete() throws SQLException{
    this.setIsValid(false);
    this.update();
  }

  public static String getResellerTableName()         {return "SR_RESELLER";}
  public static String getColumnNameName()            {return "NAME";}
  public static String getColumnNameDescription()     {return "DESCRIPTION";}
  public static String getColumnNameGroupID()         {return "IC_GROUP_ID";}
  public static String getColumnNameIsValid()         {return "IS_VALID";}
  public static String getColumnNameReferenceNumber() {return "REFERENCE_NUMBER";}

  public void update() throws SQLException {
    if (newName != null) {
      PermissionGroup pGroup = ResellerManager.getPermissionGroup(this);
        pGroup.setName(newName+"_"+this.getID()+ResellerManager.permissionGroupNameExtention);
        pGroup.update();
      ResellerStaffGroup sGroup = ResellerManager.getResellerStaffGroup(this);
        sGroup.setName(newName+"_"+this.getID());
        sGroup.update();
      setColumn(getColumnNameName(),newName);
      newName = null;
    }
    super.update();
  }

  public void insert() throws SQLException {
    if (newName != null) {
      setColumn(getColumnNameName(),newName);
    }
    super.insert();
  }
}