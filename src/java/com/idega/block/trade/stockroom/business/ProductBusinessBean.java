package com.idega.block.trade.stockroom.business;

import java.rmi.RemoteException;
import javax.ejb.FinderException;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.business.IBOLookup;
import com.idega.business.IBOServiceBean;
import com.idega.block.trade.stockroom.presentation.ProductCatalog;
import com.idega.presentation.*;
import com.idega.presentation.ui.*;
import com.idega.presentation.text.*;
import com.idega.block.text.data.*;
import com.idega.core.data.*;
import com.idega.core.localisation.business.ICLocaleBusiness;
import com.idega.core.localisation.presentation.ICLocalePresentation;
import com.idega.block.text.business.*;
import com.idega.block.trade.stockroom.data.*;
import com.idega.data.*;
import java.sql.SQLException;
import java.util.*;
import com.idega.util.*;

import com.idega.block.trade.stockroom.data.*;
/**
 * @todo losa vi� service;
 */
//import is.idega.idegaweb.travel.data.Service;
//import is.idega.idegaweb.travel.presentation.ServiceViewer;


/**
 * Title:        idegaWeb TravelBooking
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      idega
 * @author <a href="mailto:gimmi@idega.is">Grimur Jonsson</a>
 * @version 1.0
 */

public class ProductBusinessBean extends IBOServiceBean implements ProductBusiness {
  public static final String PRODUCT_ID = "pr_bus_prod_id";

  public static String uniqueDepartureAddressType = "TB_TRIP_DEPARTURE_ADDRESS";
  public static String uniqueArrivalAddressType = "TB_TRIP_ARRIVAL_ADDRESS";
  public static String uniqueHotelPickupAddressType = "TB_HOTEL_PICKUP_ADDRESS";

  public static String PARAMETER_LOCALE_DROP = "product_locale_drop";
  public static int defaultLocaleId = 1;

  private static String productsApplication = "productsApplication_";
  public HashMap products = new HashMap();

  public ProductBusinessBean() {
  }

  public int updateProduct(int productId, int supplierId, Integer fileId, String productName, String number, String productDescription, boolean isValid, int[] addressIds, int discountTypeId) throws Exception{
    return createProduct(productId,supplierId, fileId, productName, number, productDescription, isValid, addressIds, discountTypeId, -1);
  }

  public int updateProduct(int productId, Integer fileId, String productName, String number, String productDescription, boolean isValid, int localeId) throws Exception{
    return createProduct(productId,-1, fileId, productName, number, productDescription, isValid, null, -1, localeId);
  }

  public int createProduct(Integer fileId, String productName, String number, String productDescription, boolean isValid, int localeId) throws Exception{
    return createProduct(-1,-1, fileId, productName, number, productDescription, isValid, null, -1, localeId);
  }

  public int createProduct(Integer fileId, String productName, String number, String productDescription, boolean isValid) throws Exception{
    return createProduct(-1,-1, fileId, productName, number, productDescription, isValid, null, -1, -1);
  }

  public int createProduct(int supplierId, Integer fileId, String productName, String number, String productDescription, boolean isValid, int[] addressIds, int discountTypeId) throws Exception{
    return createProduct(-1,supplierId, fileId, productName, number, productDescription, isValid, addressIds, discountTypeId, -1);
  }

  public int createProduct(int productId, int supplierId, Integer fileId, String productName, String number, String productDescription, boolean isValid, int[] addressIds, int discountTypeId) throws Exception{
    return createProduct(productId, supplierId, fileId, productName, number, productDescription, isValid, addressIds, discountTypeId, -1);
  }

  public int createProduct(int productId, int supplierId, Integer fileId, String productName, String number, String productDescription, boolean isValid, int[] addressIds, int discountTypeId, int localeId) throws Exception{
    Product product= null;
    if (productId == -1) {
      product = getProductHome().create();
//      product = ((com.idega.block.trade.stockroom.data.ProductHome)com.idega.data.IDOLookup.getHomeLegacy(Product.class)).createLegacy();
    }else {
      product = getProduct(productId);// Product(productId);
    }

    if (supplierId != -1)
    product.setSupplierId(supplierId);
    if(fileId != null){
      product.setFileId(fileId);
    }
    product.setIsValid(isValid);
    if (discountTypeId != -1) {
      product.setDiscountTypeId(discountTypeId);
    }
    if (number == null) number = "";
    product.setNumber(number);


    if (productId == -1) {
      product.store();
    }else {
      updateProduct(product);
      //product.update();
    }

    if (localeId == -1) {
      product.setProductName(1, productName);
      product.setProductDescription(1, productDescription);
    }else {
      product.setProductName(localeId, productName);
      product.setProductDescription(localeId, productDescription);
    }

    product.addTravelAddresses(addressIds);

    clearProductCache(supplierId);
    return ((Integer) product.getPrimaryKey()).intValue();
  }

  public String getProductIdParameter() {
    return PRODUCT_ID;
  }

  public String getParameterLocaleDrop() {
    return PARAMETER_LOCALE_DROP;
  }


  public Product getProduct(Integer productId) throws RemoteException, FinderException{
    return getProduct(productId.intValue());
  }

  public Product getProduct(int productId) throws RemoteException, FinderException{
    Object obj = products.get(Integer.toString(productId));
    if (obj == null) {
      Product prod = getProductHome().findByPrimaryKey(new Integer(productId));
//      Product prod = ((com.idega.block.trade.stockroom.data.ProductHome)com.idega.data.IDOLookup.getHomeLegacy(Product.class)).findByPrimaryKeyLegacy(productId);
      products.put(Integer.toString(productId), prod);
      //System.err.println("ProductBusiness : creating product : "+productId);
      return prod;
    }else {
      //System.err.println("ProductBusiness : found product : "+productId);
      return (Product) obj;
    }
  }

  public void deleteProduct(Product product) throws RemoteException , IDOException {
    products.remove(product.getPrimaryKey().toString());
    product.invalidate();
  }

  public Product updateProduct(Product product) throws RemoteException, FinderException, IDOException {
    products.remove(product.getPrimaryKey().toString() );
    product.store();
    return getProduct( (Integer) product.getPrimaryKey() );
  }

  public ProductCategory getProductCategory(int categoryID) {
    try {
      return ((com.idega.block.trade.stockroom.data.ProductCategoryHome)com.idega.data.IDOLookup.getHomeLegacy(ProductCategory.class)).findByPrimaryKeyLegacy(categoryID);
    }
    catch (SQLException e) {
      return null;
    }
  }

  public String getProductNameWithNumber(Product product) throws RemoteException{
    return getProductNameWithNumber(product, true);
  }

  public String getProductNameWithNumber(Product product, int localeID) throws RemoteException{
    return getProductNameWithNumber(product, true, localeID);
  }

  public String getProductNameWithNumber(Product product, boolean numberInFront) throws RemoteException{
    return getProductNameWithNumber(product, numberInFront, -1);
  }

  public String getProductNameWithNumber(Product product, boolean numberInFront, int localeID) throws RemoteException{
    String returnString = "";

    String number = product.getNumber();
    String name = "";
    if (localeID == -1) {
      name = product.getProductName(defaultLocaleId);
    }else {
//      name = getProductName(product, localeID);
      name = product.getProductName(localeID);
    }

    if (numberInFront) {
      if (!number.equals("")) {
	returnString = number + " " + name;
      }else {
	returnString = name;
      }
    }else {
      if (!number.equals("")) {
	returnString = name + " " + number;
      }else {
	returnString = name;
      }
    }

    return returnString;
  }



  public int getSelectedLocaleId(IWContext iwc) {
    String sLocaleId = iwc.getParameter(PARAMETER_LOCALE_DROP);
    Locale currentLocale = iwc.getCurrentLocale(),chosenLocale;

    int iLocaleId = -1;
    if(sLocaleId!= null){
      iLocaleId = Integer.parseInt(sLocaleId);
      chosenLocale = TextFinder.getLocale(iLocaleId);
    }
    else{
      chosenLocale = currentLocale;
      iLocaleId = ICLocaleBusiness.getLocaleId(chosenLocale);
    }

    return iLocaleId;
  }

  public DropdownMenu getLocaleDropDown(IWContext iwc) {
    int iLocaleId = getSelectedLocaleId(iwc);
    DropdownMenu localeDrop = ICLocalePresentation.getLocaleDropdownIdKeyed(PARAMETER_LOCALE_DROP);
      localeDrop.setToSubmit();
      localeDrop.setSelectedElement(Integer.toString(iLocaleId));
    return localeDrop;
  }


  public void clearProductCache(int supplierId) {
    getIWApplicationContext().removeApplicationAttribute(productsApplication+supplierId);
    getIWApplicationContext().getApplication().getIWCacheManager().invalidateCache(ProductCatalog.CACHE_KEY);
  }

  public List getProducts(IWContext iwc, int supplierId) throws RemoteException{
    List temp = (List) iwc.getApplicationAttribute(productsApplication+supplierId);
    if (temp == null) {
      temp = getProducts(supplierId);
      iwc.setApplicationAttribute(productsApplication+supplierId, temp);
      return temp;
    }else {
      return temp;
    }
  }

  /**
   * @deprecated
   */
  public List getProducts(int supplierId) throws RemoteException{
    //return getProducts(supplierId, null);
    List list = new Vector();
    try {
      Collection coll = getProductHome().getProducts(supplierId);
      Product product;
      if (coll != null) {
        Iterator iter = coll.iterator();
        while (iter.hasNext()) {
          product = getProductHome().findByPrimaryKey( iter.next() );
          list.add(product);
        }
      }
    }catch (FinderException fe) {
      fe.printStackTrace(System.err);
    }
    return list;
  }


  public List getProducts() throws RemoteException, FinderException{
    return getProducts(-1, null);
  }

  public List getProducts(List productCategories) throws RemoteException, FinderException {
    List returner = new Vector();
    List temp;
    Product product;
    for (int i = 0; i < productCategories.size(); i++) {
      temp = getProducts((ICCategory) productCategories.get(i));
      for (int j = 0; j < temp.size(); j++) {
	product = (Product) temp.get(j);
	if (!returner.contains(product)) {
	  returner.add(product);
	}
      }
    }

    return returner;
  }

  public List getProducts(ICCategory category) throws RemoteException, FinderException{
    return getProducts(-1, category.getID(), null,null);
  }

  public List getProducts(ProductCategory productCategory) throws RemoteException, FinderException {
    return getProducts((ICCategory) productCategory);
  }

  public List getProducts(IWTimestamp stamp) throws RemoteException, FinderException{
    return getProducts(-1, stamp);
  }

  public List getProducts(IWTimestamp fromStamp, IWTimestamp toStamp) throws RemoteException, FinderException {
    return getProducts(-1, fromStamp, toStamp);
  }

  public List getProducts(int supplierId, IWTimestamp stamp) throws RemoteException, FinderException {
    if (stamp != null)
      return getProducts(supplierId, stamp, new IWTimestamp(stamp));
    else
      return getProducts(supplierId, null, null);
  }

  public List getProducts(int supplierId, IWTimestamp from, IWTimestamp to) throws RemoteException, FinderException{
    return getProducts(supplierId, -1, from, to);
  }

  /**
   * @deprecated
   */
  public List getProducts(int supplierId, int productCategoryId ,IWTimestamp from, IWTimestamp to) throws RemoteException, FinderException{
    Object obj = getIWApplicationContext().getApplicationAttribute(productsApplication+supplierId+productCategoryId+from+to);
    List products = null;
    if (obj != null) {
      products = (List) obj;
    }

    if (products == null) {
      products = new Vector();
      Collection coll = getProductHome().getProducts(supplierId, productCategoryId, from, to);
      if (coll != null) {
        Iterator iter = coll.iterator();
        Product product;
        while (iter.hasNext()) {
          product = getProductHome().findByPrimaryKey( iter.next() );
          products.add(product);
        }
      }
    }

    return products;
  }

  /**
   * @deprecated
   */
  public Timeframe getTimeframe(Product product, IWTimestamp stamp) throws RemoteException {
    return getTimeframe(product, stamp, -1);
  }

  public Timeframe getTimeframe(Product product, IWTimestamp stamp, int travelAddressId) throws RemoteException {
    Timeframe returner = null;
    try {
      Timeframe[] frames = product.getTimeframes();
      ProductPrice[] pPrices;
      for (int i = 0; i < frames.length; i++) {
	returner = frames[i];
        if (travelAddressId != -1) {
          pPrices = ProductPriceBMPBean.getProductPrices(((Integer) product.getPrimaryKey()).intValue() , frames[i].getID(), travelAddressId, false);
//          System.err.println("getting prices : length = "+pPrices.length);
          if (pPrices.length == 0) {
            continue;
          }
        }

	if (getStockroomBusiness().isInTimeframe( new IWTimestamp(returner.getFrom()) , new IWTimestamp(returner.getTo()), stamp, returner.getIfYearly() )) {
	  return returner;
	}
      }
    }catch (SQLException sql) {
      sql.printStackTrace(System.err);
    }
    return returner;
  }


  public List getDepartureAddresses(Product product, boolean ordered) throws RemoteException, IDOFinderException  {
    List list = product.getDepartureAddresses(ordered);
    if (ordered) {
      Collections.sort(list, new TravelAddressComparator(TravelAddressComparator.TIME));
    }
    return list;
  }

  public TravelAddress getDepartureAddress(Product product) throws RemoteException, IDOFinderException, SQLException{
      List tempAddresses = product.getDepartureAddresses(false);
      if (tempAddresses.size() > 0) {
	return ((com.idega.block.trade.stockroom.data.TravelAddressHome)com.idega.data.IDOLookup.getHomeLegacy(TravelAddress.class)).findByPrimaryKeyLegacy(((TravelAddress)tempAddresses.get(0)).getID() );
      }else {
	return null;
      }
  }

  public Address[] getArrivalAddresses(Product product) throws RemoteException, IDOFinderException {
    List addresses = product.getArrivalAddresses();
    return ( (Address[]) addresses.toArray(new Address[]{}) );
//    return (Address[]) (product.findRelated( (Address) com.idega.core.data.AddressBMPBean.getStaticInstance(Address.class), com.idega.core.data.AddressBMPBean.getColumnNameAddressTypeId(), Integer.toString(com.idega.core.data.AddressTypeBMPBean.getId(uniqueArrivalAddressType))));
  }

  public Address getArrivalAddress(Product product) throws RemoteException, IDOFinderException, SQLException{
    Address[] tempAddresses = getArrivalAddresses(product);
    if (tempAddresses.length > 0) {
      return ((com.idega.core.data.AddressHome)com.idega.data.IDOLookup.getHomeLegacy(Address.class)).findByPrimaryKeyLegacy(tempAddresses[0].getID());
    }else {
      return null;
    }
  }

  public DropdownMenu getDropdownMenuWithProducts(IWContext iwc, int supplierId) throws RemoteException {
    return getDropdownMenuWithProducts(iwc, supplierId, com.idega.block.trade.stockroom.data.ProductBMPBean.getProductEntityName());
  }

  public DropdownMenu getDropdownMenuWithProducts(IWContext iwc, int supplierId, String parameterName) throws RemoteException{
    List list = getProducts(iwc, supplierId);
    DropdownMenu menu = new DropdownMenu(parameterName);
    Product product;
    if (list != null && list.size() > 0) {
      for (int i = 0; i < list.size(); i++) {
	product = (Product) list.get(i);
	menu.addMenuElement(product.getPrimaryKey().toString() , getProductNameWithNumber(product));
      }
    }
    return menu;
  }

  /**
   * @deprecated
   */
  public List getProductCategories() throws IDOFinderException{
    return EntityFinder.getInstance().findAllOrdered(ProductCategory.class, com.idega.block.trade.stockroom.data.ProductCategoryBMPBean.getColumnName());
  }

  public List getProductCategories(Product product) throws RemoteException, IDORelationshipException{
    Collection coll = product.getProductCategories();
    List list = new Vector();
    if (coll != null) {
      Iterator iter = coll.iterator();
      ProductCategory pCat;
      while (iter.hasNext()) {
        pCat = (ProductCategory) iter.next();
        list.add(pCat);
      }
    }

    return new Vector(coll);
//    return EntityFinder.getInstance().findRelated(product, ProductCategory.class);
  }

  public ProductHome getProductHome() throws RemoteException {
    return (ProductHome) IDOLookup.getHome(Product.class);
  }

  protected StockroomBusiness getStockroomBusiness() throws RemoteException {
    return (StockroomBusiness) IBOLookup.getServiceInstance(getIWApplicationContext(), StockroomBusiness.class);
  }
}