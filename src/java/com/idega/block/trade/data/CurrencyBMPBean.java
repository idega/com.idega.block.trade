package com.idega.block.trade.data;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.FinderException;

import com.idega.data.IDOLookup;
import com.idega.data.query.Column;
import com.idega.data.query.InCriteria;
import com.idega.data.query.MatchCriteria;
import com.idega.data.query.MaxColumn;
import com.idega.data.query.SelectQuery;
import com.idega.data.query.Table;


/**
 * Title:        IW Trade
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      idega.is
 * @author 2000 - idega team - <br><a href="mailto:gummi@idega.is">Gudmundur Agust Saemundsson</a><br><a href="mailto:gimmi@idega.is">Grimur Jonsson</a>
 * @version 1.0
 */

public class CurrencyBMPBean extends com.idega.data.GenericEntity implements Currency{

  public CurrencyBMPBean() {
  }

  public CurrencyBMPBean(int id) throws SQLException{
    super(id);
  }

  public void initializeAttributes() {
    this.addAttribute(getIDColumnName());
    this.addAttribute(getColumnNameCurrencyName(),"Nafn",true,true,String.class,255);
    this.addAttribute(getColumnNameCurrencyAbbreviation(),"Skammstofun",true,true,String.class,20);
    addIndex("CURRENY_IDX_1", new String[]{"TR_CURRENCY_ID","BUY_VALUE","SELL_VALUE","MIDDLE_VALUE","CURRENCY_DATE"});
  }

  public String getEntityName() {
    return "TR_CURRENCY";
  }

  public String getName() {
    return getCurrencyName();
  }

  public String getCurrencyName() {
    return getStringColumnValue(getColumnNameCurrencyName());
  }

  public String getCurrencyAbbreviation() {
    return getStringColumnValue(getColumnNameCurrencyAbbreviation());
  }

  public void setCurrencyName(String name) {
    setColumn(getColumnNameCurrencyName(), name);
  }

  public void setCurrencyAbbreviation(String abbreviation) {
    setColumn(getColumnNameCurrencyAbbreviation(), abbreviation);
  }

  public Collection ejbHomeGetCurrenciesByAbbreviation(String currencyAbbreviation) throws FinderException{
    return this.idoFindAllIDsByColumnBySQL(getColumnNameCurrencyAbbreviation(), currencyAbbreviation);
  }

  public Currency ejbHomeGetCurrencyByAbbreviation(String currencyAbbreviation) throws FinderException, RemoteException {
    //Collection coll = this.idoFindAllIDsByColumnOrderedBySQL(getColumnNameCurrencyAbbreviation(), currencyAbbreviation, getColumnNameCurrencyAbbreviation() + " desc");
    Collection coll = ejbHomeGetCurrenciesByAbbreviation(currencyAbbreviation);
    Iterator iter = coll.iterator();
    if (iter.hasNext()) {
      return getHome().findByPrimaryKey(iter.next());
    }
    return null;
  }

  private CurrencyHome getHome() throws RemoteException{
    return (CurrencyHome) IDOLookup.getHome(Currency.class);
  }

  public static String getColumnNameCurrencyID(){return "TR_CURRENCY_ID";}
  public static String getColumnNameCurrencyName(){return"CURRENCY_NAME";}
  public static String getColumnNameCurrencyAbbreviation(){return"CURRENCY_ABBREVIATION";}

  public Collection ejbFindAll() throws FinderException {
	return this.idoFindAllIDsBySQL();  
  }

  /**
   * Finds a list of all the currencies that have been updated in the last 2 days
   * @return A Collection of Currency
   * @throws FinderException 
   */
  public Collection ejbFindAllInUse() throws FinderException {
	  Table table = new Table(this);
//	  Table valuesTable = new Table(CurrencyValuesBMPBean.class);

	  Column pk = new Column(table, getIDColumnName());
//	  Column column = new Column(valuesTable, CurrencyValuesBMPBean.getColumnNameTimestamp());
//	  IWTimestamp stamp = IWTimestamp.RightNow();
//	  stamp.addDays(-2);
	  SelectQuery subQue = new SelectQuery(table);
	  Column abbr = new Column(table, getColumnNameCurrencyAbbreviation());
	  subQue.addColumn(new MaxColumn(table, getIDColumnName()));
	  subQue.addGroupByColumn(abbr);
	  subQue.addCriteria(new MatchCriteria(abbr, MatchCriteria.NOTEQUALS, ""));
	  subQue.addCriteria(new MatchCriteria(abbr, true));
	  InCriteria IN = new InCriteria(pk, subQue);
	  
	  SelectQuery query = new SelectQuery(table);
	  query.addColumn(pk);
//	  query.addCriteria(new MatchCriteria(column, MatchCriteria.GREATER, stamp));
	  query.addCriteria(IN);
//	  System.out.println("[CurrencyBMPBean] "+query.toString());
	  return this.idoFindPKsByQuery(query);
  }
  
}
