package com.idega.block.trade.stockroom.data;

import java.sql.*;
import com.idega.data.*;
import com.idega.core.data.*;

import com.idega.util.idegaTimestamp;

/**
 * Title:        IW Travel
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      idega.is
 * @author 2000 - idega team - <br><a href="mailto:gummi@idega.is">Gu�mundur �g�st S�mundsson</a><br><a href="mailto:gimmi@idega.is">Gr�mur J�nsson</a>
 * @version 1.0
 */

public class Timeframe extends GenericEntity{

  public Timeframe(){
          super();
  }
  public Timeframe(int id)throws SQLException{
          super(id);
  }
  public void initializeAttributes(){
    addAttribute(getIDColumnName());
    addAttribute(getNameColumnName(), "Name", true, true, String.class, 255);
    addAttribute(getTimeframeToColumnName(), "Til", true, true, java.sql.Timestamp.class);
    addAttribute(getTimeframeFromColumnName(), "Fr�", true, true, java.sql.Timestamp.class);
    addAttribute(getYearlyColumnName(), "�rlegt", true, true, Boolean.class);
  }


  public void setDefaultValue() {
    setName("");
  }

  public String getEntityName(){
    return getTimeframeTableName();
  }
  public String getName(){
    String stampTxt1 = new idegaTimestamp(this.getFrom()).getLocaleDate(idegaTimestamp.getIceland());
    String stampTxt2 = new idegaTimestamp(this.getTo()).getLocaleDate(idegaTimestamp.getIceland());
    return stampTxt1+" - "+stampTxt2;
  }

  public void setName(String name){
    setColumn(getNameColumnName(),name);
  }

  public Timestamp getFrom() {
    return (Timestamp) getColumnValue(getTimeframeFromColumnName());
  }

  public void setFrom(Timestamp timestamp) {
    setColumn(getTimeframeFromColumnName(), timestamp);
  }

  public Timestamp getTo() {
    return (Timestamp) getColumnValue(getTimeframeToColumnName());
  }

  public void setTo(Timestamp timestamp) {
    setColumn(getTimeframeToColumnName(), timestamp);
  }

  public boolean getIfYearly() {
    return getYearly();
  }

  public boolean getYearly() {
    return getBooleanColumnValue(getYearlyColumnName());
  }

  public void setIfYearly(boolean yearly) {
    setYearly(yearly);
  }

  public void setYearly(boolean yearly) {
    setColumn(getYearlyColumnName(),yearly);
  }

  public static String getTimeframeTableName(){return "TB_TIMEFRAME";}
  public static String getNameColumnName() {return "NAME";}
  public static String getTimeframeToColumnName() {return "TIMEFRAME_TO";}
  public static String getTimeframeFromColumnName() {return "TIMEFRAME_FROM";}
  public static String getYearlyColumnName() {return "YEARLY";}

}
