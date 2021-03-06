package com.idega.block.trade.stockroom.business;

import java.rmi.RemoteException;
import java.text.Collator;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import javax.ejb.EJBException;
import javax.ejb.FinderException;

import com.idega.block.trade.stockroom.data.PriceCategory;
import com.idega.block.trade.stockroom.data.Product;
import com.idega.block.trade.stockroom.data.ProductCategory;
import com.idega.block.trade.stockroom.data.ProductPrice;
import com.idega.block.trade.stockroom.data.Timeframe;
import com.idega.business.IBOLookup;
import com.idega.core.localisation.business.ICLocaleBusiness;
import com.idega.data.IDORelationshipException;
import com.idega.presentation.IWContext;
import com.idega.util.IWTimestamp;
import com.idega.util.IsCollator;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      idega multimedia
 * @author       <a href="mailto:aron@idega.is">aron@idega.is</a>
 * @version 1.0
 */

public class ProductComparator implements Comparator {

  public static final int NAME = 1;
  public static final int NUMBER = 2;
  public static final int PRICE = 5;
  public static final int CREATION_DATE = 6;
  public static final int SUPPLIER = 7; 
  public static final int PRODUCT_CATEGORY = 8; 

  private int localeId = -1;
  private int sortBy;
  private StockroomBusiness stockroomBusiness;
	private ProductBusiness productBusiness;

  private PriceCategory priceCategoryToSortBy;
  private int currencyId;
  private IWTimestamp time;
  private Collator collator;
  
  public ProductComparator(int toSortBy, Locale locale, ProductBusiness pBus) {
      this.sortBy = toSortBy;
      this.localeId = ICLocaleBusiness.getLocaleId(locale);
      try {
      		this.collator = Collator.getInstance(locale);
      		if (this.collator == null) {
      			this.collator = IsCollator.getIsCollator();
      		}
      } catch (Exception e) {
      		this.collator = IsCollator.getIsCollator();
      }
  }

  public void sortBy(int toSortBy) {
      this.sortBy = toSortBy;
  }

  public int compare(Object o1, Object o2) {
      int result = 0;

      try {
        switch (this.sortBy) {
          case NAME     : result = nameSort(o1, o2);
          break;
          case NUMBER   : result = numberSort(o1, o2);
          break;
          case PRICE : result = priceSort(o1, o2);
          break;
          case CREATION_DATE : result = dateSort(o1, o2);
          break;
          case SUPPLIER : result = supplierSort(o1, o2);
          break;
          case PRODUCT_CATEGORY : result = productCategorySort(o1, o2);
        }
      }catch (RemoteException rme) {
        rme.printStackTrace(System.err);
      }

      return result;
  }
  
  private int productCategorySort(Object o1, Object o2) throws RemoteException{
	    Product p1 = (Product) o1;
	    Product p2 = (Product) o2;

	    Collection c1 = null;
	    Collection c2 = null;
		try {
			c1 = p1.getProductCategories();
			c2 = p2.getProductCategories();
		} catch (IDORelationshipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    if (c1 == null && c2 == null) {
	    	return nameSort(o1, o2);
	    }
	    
	    String one = ((ProductCategory)c1.iterator().next()).getCategoryType();
	    String two = ((ProductCategory)c2.iterator().next()).getCategoryType();
	    return this.collator.compare(one, two);
	  }

  private int nameSort(Object o1, Object o2) throws RemoteException{
    Product p1 = (Product) o1;
    Product p2 = (Product) o2;

    String one = p1.getProductName(this.localeId)!=null?p1.getProductName(this.localeId):"";
    String two = p2.getProductName(this.localeId)!=null?p2.getProductName(this.localeId):"";

    return this.collator.compare(one, two);
  }
  
  private int supplierSort(Object o1, Object o2) throws RemoteException {
  		Product p1 = (Product) o1;
    Product p2 = (Product) o2;

    String one = p1.getSupplier().getName() != null ? p1.getSupplier().getName(): "";
    String two = p2.getSupplier().getName() != null ? p2.getSupplier().getName(): "";
    return this.collator.compare(one, two);
  	
  }

  private int numberSort(Object o1, Object o2) throws RemoteException {
    Product p1 = (Product) o1;
    Product p2 = (Product) o2;

    String one = p1.getNumber()!=null?p1.getNumber():"";
    String two = p2.getNumber()!=null?p2.getNumber():"";

    return this.collator.compare(one,two);
  }

  private int priceSort(Object o1, Object o2) {
    try {
      Product p1 = (Product) o1;
      Product p2 = (Product) o2;
	
		  float pr1 = 0;
		  float pr2 = 0;

			if (this.priceCategoryToSortBy == null) {
      	ProductPrice price1 = getStockroomBusiness().getPrice(p1);
      	ProductPrice price2 = getStockroomBusiness().getPrice(p2);

	      if (price1 != null) {
					pr1 = price1.getPrice();
				}
      	if (price2 != null) {
					pr2 = price2.getPrice();
				}
			} else {
				Timeframe timeframe = getProductBusiness().getTimeframe(p1, this.time, -1);
				int timeframeId1 = -1;
				if (timeframe != null) {
					timeframeId1 = timeframe.getID();
				}
				timeframe = getProductBusiness().getTimeframe(p2, this.time, -1);
				int timeframeId2 = -1;
				if (timeframe != null) {
					timeframeId2 = timeframe.getID();
				}				
				try {
					pr1 = getStockroomBusiness().getPrice(-1, p1.getID(), Integer.parseInt(this.priceCategoryToSortBy.getPrimaryKey().toString()), this.currencyId, IWTimestamp.getTimestampRightNow(), timeframeId1, -1);
					//System.out.println("[ProductComparator] : price for p1 = "+pr1+" ("+p1.getProductName(IWContext.getInstance().getCurrentLocaleId())+"="+p1.getID()+")");
				}
				catch (ProductPriceException e) {
					//System.out.println("[ProductComparator] : cannot get priceCategory specific price ("+p1.getProductName(IWContext.getInstance().getCurrentLocaleId())+"="+p1.getID()+")");
				}
				catch (Exception e) {
					//System.out.println("[ProductComparator] : cannot get priceCategory specific price ("+p1.getProductName(IWContext.getInstance().getCurrentLocaleId())+"="+p1.getID()+")");
					e.printStackTrace();
				}
				try {
					pr2 = getStockroomBusiness().getPrice(-1, p2.getID(), Integer.parseInt(this.priceCategoryToSortBy.getPrimaryKey().toString()), this.currencyId, IWTimestamp.getTimestampRightNow(), timeframeId2, -1);
					//System.out.println("[ProductComparator] : price for p2 = "+pr2+" ("+p2.getProductName(IWContext.getInstance().getCurrentLocaleId())+"="+p2.getID()+")");
				}
				catch (ProductPriceException e) {
					//System.out.println("[ProductComparator] : cannot get priceCategory specific price ("+p2.getProductName(IWContext.getInstance().getCurrentLocaleId())+"="+p2.getID()+")");
				}
				catch (Exception e) {
					//System.out.println("[ProductComparator] : cannot get priceCategory specific price ("+p2.getProductName(IWContext.getInstance().getCurrentLocaleId())+"="+p2.getID()+")");
					e.printStackTrace();
				}
			}

      if (pr1 < pr2) {
				return -1;
			}
			else if (pr2 < pr1) {
				return 1;
			}
			else {
				return 0;
			}

	  }catch (RemoteException re) {
	    throw new RuntimeException(re.getMessage());
	  }
	catch (EJBException e) {
	    throw new RuntimeException(e.getMessage());
	}
	catch (FinderException e) {
	    throw new RuntimeException(e.getMessage());
	}
  }

  private int dateSort(Object o1, Object o2) throws RemoteException {
    Product p1 = (Product) o1;
    Product p2 = (Product) o2;

    IWTimestamp s1 = new IWTimestamp(p1.getCreationDate());
    IWTimestamp s2 = new IWTimestamp(p2.getCreationDate());

    if (s1.isLaterThan(s2)) {
      return -1;
    }
    else if (s2.isLaterThan(s1)){
      return 1;
    }
    else {
      return 0;
    }
  }

  public boolean equals(Object obj) {
    /**@todo: Implement this java.util.Comparator method*/
    throw new java.lang.UnsupportedOperationException("Method equals() not yet implemented.");
  }

  public Iterator sort(Product[] products, int toSortBy) {
      this.sortBy = toSortBy;
      List list = new LinkedList();
      for(int i = 0; i < products.length; i++) {
	  list.add(products[i]);
      }
      Collections.sort(list, this);
      return list.iterator();
  }

  public Iterator sort(Product[] products) {
      List list = new LinkedList();
      for(int i = 0; i < products.length; i++) {
	  list.add(products[i]);
      }
      Collections.sort(list, this);
      return list.iterator();
  }

  public Product[] sortedArray(Product[] products, int toSortBy) {
      this.sortBy = toSortBy;
      List list = new LinkedList();
      for(int i = 0; i < products.length; i++) {
	  list.add(products[i]);
      }
      Collections.sort(list, this);
      Object[] objArr = list.toArray();
      for(int i = 0; i < objArr.length; i++) {
	  products[i] = (Product) objArr[i];
      }
      return (products);
  }

   public Vector sortedArray(Vector list) {
      Collections.sort(list, this);
      return list;
  }


  public Product[] sortedArray(Product[] products) {
      List list = new LinkedList();
      for(int i = 0; i < products.length; i++) {
	  list.add(products[i]);
      }
      Collections.sort(list, this);
      Object[] objArr = list.toArray();
      for(int i = 0; i < objArr.length; i++) {
	  products[i] = (Product) objArr[i];
      }
      return (products);
  }

  public Product[] reverseSortedArray(Product[] products, int toSortBy) {
      this.sortBy = toSortBy;
      List list = new LinkedList();
      for(int i = 0; i < products.length; i++) {
	  list.add(products[i]);
      }
      Collections.sort(list, this);
      Collections.reverse(list);
      Object[] objArr = list.toArray();
      for(int i = 0; i < objArr.length; i++) {
	  products[i] = (Product) objArr[i];
      }
      return (products);
  }

  private StockroomBusiness getStockroomBusiness() {
    try {
      if (this.stockroomBusiness == null) {
        this.stockroomBusiness = (StockroomBusiness) IBOLookup.getServiceInstance(IWContext.getInstance(), StockroomBusiness.class);
      }
      return this.stockroomBusiness;
    }catch (RemoteException re) {
      throw new RuntimeException(re.getMessage());
    }
  }
  
  private ProductBusiness getProductBusiness() {
		try {
		  if (this.productBusiness == null) {
			this.productBusiness = (ProductBusiness) IBOLookup.getServiceInstance(IWContext.getInstance(), ProductBusiness.class);
		  }
		  return this.productBusiness;
		}catch (RemoteException re) {
		  throw new RuntimeException(re.getMessage());
		}
  }

	public void setPriceCategoryValues(PriceCategory priceCategory, int currencyId, IWTimestamp time) {
		this.priceCategoryToSortBy = priceCategory;
		this.currencyId = currencyId;
		this.time = time;
	}

}
