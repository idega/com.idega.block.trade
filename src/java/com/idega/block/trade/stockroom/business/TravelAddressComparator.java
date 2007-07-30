package com.idega.block.trade.stockroom.business;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import com.idega.block.trade.stockroom.data.Timeframe;
import com.idega.block.trade.stockroom.data.TravelAddress;
import com.idega.util.IWTimestamp;


/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      idega multimedia
 * @author       <a href="mailto:aron@idega.is">aron@idega.is</a>
 * @version 1.0
 */

public class TravelAddressComparator implements Comparator {

  public static final int NAME = 1;
  public static final int TIME = 2;
  public static final int NAME_TIME = 3;
  public static final int TIME_GROUP_ORDER = 4;
  
  private int sortBy;
  private Locale locale;

  public TravelAddressComparator(Locale locale) {
      this.sortBy = NAME_TIME;
      this.locale = locale;
  }

  public TravelAddressComparator(Locale locale, int toSortBy) {
      this.sortBy = toSortBy;
      this.locale = locale;
  }

  public void sortBy(int toSortBy) {
      this.sortBy = toSortBy;
  }

  public int compare(Object o1, Object o2) {
      int result = 0;

      switch (this.sortBy) {
        case NAME     : result = nameSort(o1, o2);
        break;
        case TIME   : result = timeSort(o1,o2);
        break;
        case NAME_TIME   : result = nameTimeSort(o1,o2);
        break;
        case TIME_GROUP_ORDER   : result = timeGroupOrderSort(o1,o2);
        break;
      }

      return result;
  }

  private int nameSort(Object o1, Object o2) {
    TravelAddress p1 = (TravelAddress) o1;
    TravelAddress p2 = (TravelAddress) o2;

    String one = p1.getStreetName()!=null?p1.getStreetName():"";
    String two = p2.getStreetName()!=null?p2.getStreetName():"";

    return Collator.getInstance(locale).compare(one, two);
  }

  private int timeSort(Object o1, Object o2) {
    TravelAddress p1 = (TravelAddress) o1;
    TravelAddress p2 = (TravelAddress) o2;

    IWTimestamp t1 = new IWTimestamp(p1.getTime());
    IWTimestamp t2 = new IWTimestamp(p2.getTime());

    if (t1.isLaterThan(t2)) {
      return 1;
    }else if (t2.isLaterThan(t1)) {
      return -1;
    }else {
      return 0;
    }
  }

  private int groupSort(Object o1, Object o2) {
	    TravelAddress p1 = (TravelAddress) o1;
	    TravelAddress p2 = (TravelAddress) o2;

	    String one = p1.getGroupName()!=null?p1.getGroupName():"";
	    String two = p2.getGroupName()!=null?p2.getGroupName():"";

	    return Collator.getInstance(locale).compare(one, two);
	  }

  private int nameTimeSort(Object o1, Object o2) {
    int returner = 0;

    returner = nameSort(o1, o2);
    if (returner == 0) {
      returner = timeSort(o1, o2);
    }

    return returner;
  }
  
  private int timeGroupOrderSort(Object o1, Object o2) {
	  int ret = timeSort(o1, o2);
	  if (ret != 0) {
		  return ret;
	  }
	  
	  ret = groupSort(o1, o2);
	  if (ret != 0) {
		  return ret;
	  }
	  
	  TravelAddress t1 = (TravelAddress) o1;
	  TravelAddress t2 = (TravelAddress) o2;
	  
	  if (t1.getOrder() > t2.getOrder()) {
		  return 1;
	  } else if (t1.getOrder() < t2.getOrder()) {
		  return -1;
	  } else {
		  return 0;
	  }
	  
	  
  }

  public boolean equals(Object obj) {
    /**@todo: Implement this java.util.Comparator method*/
    throw new java.lang.UnsupportedOperationException("Method equals() not yet implemented.");
  }

  public Iterator sort(Timeframe[] tFrames, int toSortBy) {
      this.sortBy = toSortBy;
      List list = new LinkedList();
      for(int i = 0; i < tFrames.length; i++) {
          list.add(tFrames[i]);
      }
      Collections.sort(list, this);
      return list.iterator();
  }

  public Iterator sort(Timeframe[] tFrames) {
      List list = new LinkedList();
      for(int i = 0; i < tFrames.length; i++) {
          list.add(tFrames[i]);
      }
      Collections.sort(list, this);
      return list.iterator();
  }

  public Timeframe[] sortedArray(Timeframe[] tFrames, int toSortBy) {
      this.sortBy = toSortBy;
      List list = new LinkedList();
      for(int i = 0; i < tFrames.length; i++) {
          list.add(tFrames[i]);
      }
      Collections.sort(list, this);
      Object[] objArr = list.toArray();
      for(int i = 0; i < objArr.length; i++) {
          tFrames[i] = (Timeframe) objArr[i];
      }
      return (tFrames);
  }

   public Vector sortedArray(Vector list) {
      Collections.sort(list, this);
      return list;
  }


  public Timeframe[] sortedArray(Timeframe[] tFrame) {
      List list = new LinkedList();
      for(int i = 0; i < tFrame.length; i++) {
          list.add(tFrame[i]);
      }
      Collections.sort(list, this);
      Object[] objArr = list.toArray();
      for(int i = 0; i < objArr.length; i++) {
          tFrame[i] = (Timeframe) objArr[i];
      }
      return (tFrame);
  }

  public Timeframe[] reverseSortedArray(Timeframe[] tFrame, int toSortBy) {
      this.sortBy = toSortBy;
      List list = new LinkedList();
      for(int i = 0; i < tFrame.length; i++) {
          list.add(tFrame[i]);
      }
      Collections.sort(list, this);
      Collections.reverse(list);
      Object[] objArr = list.toArray();
      for(int i = 0; i < objArr.length; i++) {
          tFrame[i] = (Timeframe) objArr[i];
      }
      return (tFrame);
  }

}
