package com.idega.block.trade.stockroom.data;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;

import javax.ejb.FinderException;

import com.idega.block.category.data.ICCategoryBMPBean;
import com.idega.block.text.business.ContentFinder;
import com.idega.block.text.business.ContentHelper;
import com.idega.block.text.business.TextFinder;
import com.idega.block.text.data.LocalizedText;
import com.idega.block.text.data.LocalizedTextBMPBean;
import com.idega.block.text.data.TxText;
import com.idega.block.trade.data.VoucherAd;
import com.idega.block.trade.stockroom.business.TimeframeComparator;
import com.idega.core.file.data.ICFile;
import com.idega.core.location.data.Address;
import com.idega.data.EntityControl;
import com.idega.data.EntityFinder;
import com.idega.data.GenericEntity;
import com.idega.data.IDOAddRelationshipException;
import com.idega.data.IDOCompositePrimaryKeyException;
import com.idega.data.IDOException;
import com.idega.data.IDOFinderException;
import com.idega.data.IDOLegacyEntity;
import com.idega.data.IDOLookup;
import com.idega.data.IDORelationshipException;
import com.idega.data.IDORemoveRelationshipException;
import com.idega.data.MetaData;
import com.idega.data.MetaDataCapable;
import com.idega.data.query.Column;
import com.idega.data.query.MatchCriteria;
import com.idega.data.query.SelectQuery;
import com.idega.data.query.Table;
import com.idega.data.query.WildCardColumn;
import com.idega.util.CoreConstants;
import com.idega.util.IWTimestamp;
import com.idega.util.StringUtil;

/**
 *  Title: IW Trade Description: Copyright: Copyright (c) 2001 Company: idega.is
 *
 *@author     2000 - idega team - <br>
 *      <a href="mailto:gummi@idega.is">Gu�mundur �g�st S�mundsson</a> <br>
 *      <a href="mailto:gimmi@idega.is">Gr�mur J�nsson</a>
 *@created    6. mars 2002
 *@version    1.0
 */

public class ProductBMPBean extends GenericEntity implements Product, IDOLegacyEntity, MetaDataCapable {

  public final static int DISCOUNT_TYPE_ID_AMOUNT = 0;
  public final static int DISCOUNT_TYPE_ID_PERCENT = 1;

  private final int FILTER_NOT_CONNECTED_TO_CATEGORY = 0;
  private static final String COLUMN_REFUNDABLE = "refundable";
  private static final String COLUMN_VOUCHER_COMMENT = "VOUCHER_COMMENT";
  private static final String COLUMN_DISABLED = "DISABLED";

  public static final String RELATION_DISCOUNT_CODE_GROUP = "sr_product_dc_group";

  /**
   *  Constructor for the Product object
   */
  public ProductBMPBean() {
    super();
  }

  /**
   *@param  id                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   *@deprecated
   */

  @Deprecated
public ProductBMPBean( int id ) throws SQLException {
    super( id );
  }

  /**
   *  Description of the Method
   */
  @Override
public void initializeAttributes() {
    this.addAttribute( getIDColumnName() );
    this.addAttribute( getColumnNameSupplierId(), "Birgi", true, true, Integer.class, "many_to_one", Supplier.class );
    this.addAttribute( getColumnNameFileId(), "Fylgiskjal(mynd)", true, true, Integer.class, "many_to_one", ICFile.class );
    this.addAttribute( getColumnNameIsValid(), "� notkun", true, true, Boolean.class );
    this.addAttribute( getDiscountTypeIdColumnName(), "discount type", true, true, Integer.class );
    this.addAttribute( getColumnNameNumber(), "n�mer", true, true, String.class );
    this.addAttribute( getColumnNameCreationDate(), "creation date", true, true, Timestamp.class );
    this.addAttribute( getColumnNameModificationDate(), "edit date", true, true, Timestamp.class );
    addAttribute( COLUMN_REFUNDABLE, "refundable", true, true, Boolean.class);
    addAttribute(getColumnNameAuthorizationCheck(),"authorization", true, true, Boolean.class);
    addAttribute( COLUMN_VOUCHER_COMMENT, "voucher_comment", true, true, String.class, 1000);

    this.addManyToManyRelationShip( ProductCategory.class, "SR_PRODUCT_PRODUCT_CATEGORY" );
    this.setNullable( getColumnNameFileId(), true );
    this.addManyToManyRelationShip( com.idega.block.text.data.LocalizedText.class, "SR_PRODUCT_LOCALIZED_TEXT" );
    this.addManyToManyRelationShip( Address.class, "SR_PRODUCT_IC_ADDRESS" );
    this.addManyToManyRelationShip( TxText.class );
    this.addManyToManyRelationShip( ICFile.class );
    this.addManyToManyRelationShip( DiscountCodeGroup.class, RELATION_DISCOUNT_CODE_GROUP );
    addMetaDataRelationship();

    addAttribute( COLUMN_DISABLED, "Disabled", true, true, Boolean.class );

    addIndex("IDX_PROD_1", new String[]{getColumnNameSupplierId(), getColumnNameIsValid()});
    addIndex("IDX_PROD_2", new String[]{getIDColumnName(), getColumnNameSupplierId(), getColumnNameIsValid()});
  }



  @Override
public void invalidate() throws IDOException{
    this.setIsValid( false );
    this.store();
  }

  /**
   * deprecated
   */
  @Override
public int getID() {
    return ((Integer) this.getPrimaryKey()).intValue();
  }

  /**
   *  Sets the defaultValues attribute of the Product object
   */
  @Override
public void setDefaultValues() {
    this.setIsValid( true );
    this.setDiscountTypeId( DISCOUNT_TYPE_ID_PERCENT );
    this.setCreationDate( IWTimestamp.getTimestampRightNow() );
    this.setModificationDate( IWTimestamp.getTimestampRightNow() );
    this.setRefundable(true);
  }

  /**
   *  Gets the entityName attribute of the Product object
   *
   *@return    The entityName value
   */
  @Override
public String getEntityName() {
    return getProductEntityName();
  }

  public static String getIdColumnName() { return ProductBMPBean.getProductEntityName()+"_ID";}
  public static String getProductEntityName() {    return "SR_PRODUCT";  }
  public static String getColumnNameSupplierId() {    return "SR_SUPPLIER_ID";  }
  public static String getColumnNameFileId() {    return "IC_FILE_ID";  }
  public static String getColumnNameProductName() {    return "PRODUCT_NAME";  }
  public static String getColumnNameProductDescription() {    return "PRODUCT_DESCRIPTION";  }
  public static String getColumnNameIsValid() {    return "IS_VALID";  }
  public static String getDiscountTypeIdColumnName() {    return "DISCOUNT_TYPE_ID";  }
  public static String getColumnNameNumber() {    return "PRODUCT_NUMBER";  }
  public static String getColumnNameCreationDate() {    return "CREATION_DATE";  }
  public static String getColumnNameModificationDate() {    return "MODIFICATION_DATE";  }
  public static String getColumnNameAuthorizationCheck() { return "AUTHORIZATION_CHECK"; }


  /*
   *  Setters
   */
  /**
   *  Sets the supplierId attribute of the Product object
   *
   *@param  id  The new supplierId value
   */
  @Override
public void setSupplierId( int id ) {
    this.setColumn( getColumnNameSupplierId(), id );
  }

  /**
   *  Sets the supplierId attribute of the Product object
   *
   *@param  id  The new supplierId value
   */
  @Override
public void setSupplierId( Integer id ) {
    this.setColumn( getColumnNameSupplierId(), id );
  }

  /**
   *  Sets the fileId attribute of the Product object
   *
   *@param  id  The new fileId value
   */
  @Override
public void setFileId( int id ) {
    this.setColumn( getColumnNameFileId(), id );
  }

  /**
   *  Sets the fileId attribute of the Product object
   *
   *@param  id  The new fileId value
   */
  @Override
public void setFileId( Integer id ) {
    if (id == null) {
      this.removeFromColumn(getColumnNameFileId());
    }else {
      this.setColumn( getColumnNameFileId(), id );
    }

  }


  /*
   *  public void setProductName(String name){
   *  this.setColumn(getColumnNameProductName(),name);
   *  }
   *  public void setProdcutDescription(String description){
   *  this.setColumn(getColumnNameProductDescription(),description);
   *  }
   */
  /**
   *  Sets the isValid attribute of the Product object
   *
   *@param  valid  The new isValid value
   */
  @Override
public void setIsValid( boolean valid ) {
    this.setColumn( getColumnNameIsValid(), valid );
  }

  @Override
public void setDisabled( boolean disabled ) {
	   this.setColumn( COLUMN_DISABLED, disabled );
  }

  /**
   *  Sets the discountTypeId attribute of the Product object
   *
   *@param  discountTypeId  The new discountTypeId value
   */
  @Override
public void setDiscountTypeId( int discountTypeId ) {
    setColumn( getDiscountTypeIdColumnName(), discountTypeId );
  }

  /**
   *  Sets the number attribute of the Product object
   *
   *@param  number  The new number value
   */
  @Override
public void setNumber( String number ) {
    setColumn( getColumnNameNumber(), number );
  }

  /**
   *  Sets the creationDate attribute of the Product object
   *
   *@param  timestamp  The new creationDate value
   */
  @Override
public void setCreationDate( IWTimestamp timestamp ) {
    setCreationDate( timestamp.getTimestamp() );
  }

  /**
   *  Sets the creationDate attribute of the Product object
   *
   *@param  timestamp  The new creationDate value
   */
  @Override
public void setCreationDate( Timestamp timestamp ) {
    setColumn( getColumnNameCreationDate(), timestamp );
  }

  /**
   *  Sets the modificationDate attribute of the Product object
   *
   *@param  timestamp  The new modificationDate value
   */
  private void setModificationDate( Timestamp timestamp ) {
    setColumn( getColumnNameModificationDate(), timestamp );
  }
  /**
   *  Set to true if the sale is to be set on hold
   *
   * @param saleOnHold true if the sale is set on hold while creditcard is checked, false otherwise
   *
   */
  @Override
public void setAuthorizationCheck(boolean saleOnHold) {
  		setColumn(getColumnNameAuthorizationCheck(), saleOnHold);
  }


  /*
   *  Getters
   */
  /**
   *  Gets the supplierId attribute of the Product object
   *
   *@return    The supplierId value
   */
  @Override
public int getSupplierId() {
    return this.getIntColumnValue( getColumnNameSupplierId() );
  }

  @Override
public Supplier getSupplier() {
  		return (Supplier) getColumnValue( getColumnNameSupplierId() );
  }

  /**
   *  Gets the fileId attribute of the Product object
   *
   *@return    The fileId value
   */
  @Override
public int getFileId() {
    return this.getIntColumnValue( getColumnNameFileId() );
  }

  /**
  *  Gets the fileId attribute of the Product object
  *
  *@return    The fileId value
  */
	@Override
	public ICFile getFile() {
	  return (ICFile) this.getColumnValue( getColumnNameFileId() );
	}

 /**
   *  Gets the isValid attribute of the Product object
   *
   *@return    The isValid value
   */
  @Override
public boolean getIsValid() {
    return this.getBooleanColumnValue( getColumnNameIsValid() );
  }

  @Override
public boolean getDisabled() {
	   return this.getBooleanColumnValue( COLUMN_DISABLED );
  }

  /**
   *  Gets the discountTypeId attribute of the Product object
   *
   *@return    The discountTypeId value
   */
  @Override
public int getDiscountTypeId() {
    return getIntColumnValue( getDiscountTypeIdColumnName() );
  }

  /**
   *  Gets the number attribute of the Product object
   *
   *@return    The number value
   */
  @Override
public String getNumber() {
    return getStringColumnValue( getColumnNameNumber() );
  }

  /**
   *  Gets the timeframes attribute of the Product object
   *
   *@return                   The timeframes value
   *@exception  SQLException  Description of the Exception
   */
  @Override
public Timeframe[] getTimeframes() throws SQLException {
    return getTimeframesOrdered( TimeframeComparator.FROMDATE );
  }

  /**
   *  Gets the timeframesOrdered attribute of the Product object
   *
   *@param  orderBy           Description of the Parameter
   *@return                   The timeframesOrdered value
   *@exception  SQLException  Description of the Exception
   */
  private Timeframe[] getTimeframesOrdered( int orderBy ) throws SQLException {
    if ( orderBy != -1 ) {
      try {
        Collection coll = this.idoGetRelatedEntities(Timeframe.class);
        //TimeframeHome tHome = (TimeframeHome) IDOLookup.getHome(Timeframe.class);
        List tFrames = new Vector();
        Iterator iter = coll.iterator();
        while (iter.hasNext()) {
          //Timeframe item = tHome.findByPrimaryKey(iter.next());
          tFrames.add(iter.next());
        }
        TimeframeComparator comparator = new TimeframeComparator( TimeframeComparator.FROMDATE );
        Collections.sort( tFrames, comparator );
        return ( Timeframe[] ) tFrames.toArray( new Timeframe[]{} );
      }catch (Exception e) {
        e.printStackTrace(System.err);
        throw new SQLException(e.getMessage());
      }
    } else {
      return ( Timeframe[] ) this.findRelated( GenericEntity.getStaticInstance( Timeframe.class ) );
    }
  }

  /**
   *  Gets the timeframe attribute of the Product object
   *
   *@return                   The timeframe value
   *@exception  SQLException  Description of the Exception
   */
  @Override
public Timeframe getTimeframe() throws SQLException {
    Timeframe[] temp = getTimeframes();
    if ( temp.length > 0 ) {
      return temp[0];
    } else {
      return null;
    }
  }

  /**
   *  Gets the creationDate attribute of the Product object
   *
   *@return    The creationDate value
   */
  @Override
public Timestamp getCreationDate() {
    return ( Timestamp ) getColumnValue( getColumnNameCreationDate() );
  }

  /**
   *  Gets the editDate attribute of the Product object
   *
   *@return    The editDate value
   */
  @Override
public Timestamp getEditDate() {
    return ( Timestamp ) getColumnValue( getColumnNameModificationDate() );
  }

  /**
   *  Gets the text attribute of the Product object
   *
   *@return                   The text value
   *@exception  SQLException  Description of the Exception
   */
  @Override
public TxText getText() throws SQLException {
		try {
			Collection coll = this.idoGetRelatedEntities( TxText.class);
			if (coll != null && (coll.size() > 0) ) {
				Iterator iter = coll.iterator();
				Object obj;
				// Returns the last object in Collection
				while (iter.hasNext()) {
					obj = iter.next();
					if (!iter.hasNext()) {
						return (TxText) obj;
					}
				}
			}else {
				return null;
			}
		} catch (IDORelationshipException e) {
			throw new SQLException(e.getMessage());
		}
		return null;
//    TxText[] texti = ( TxText[] ) this.findRelated( ( TxText ) com.idega.block.text.data.TxTextBMPBean.getStaticInstance( TxText.class ) );
//    if ( texti.length > 0 ) {
//      return texti[texti.length - 1];
//    } else {
//      return null;
//    }
  }
  /**
   * 	Gets the status of the saleOnHold attribute
   *
   * @return true if the sale of the product is set on hold, false otherwise
   */
  @Override
public boolean getAuthorizationCheck() {
  		return this.getBooleanColumnValue( getColumnNameAuthorizationCheck() );
  }

  /**
   *  Description of the Method
   *
   *@exception  SQLException  Description of the Exception
   */
  @Override
public void update() throws SQLException {
    setModificationDate( IWTimestamp.getTimestampRightNow() );
    super.update();
  }


  @Override
public Collection getProductCategories() throws IDORelationshipException {
    Collection coll = this.idoGetRelatedEntities(ProductCategory.class);
    return coll;
  }

  @Override
public void setProductCategories(int[] categoryIds) throws RemoteException, FinderException, IDORemoveRelationshipException{
    this.idoRemoveFrom(ProductCategory.class);
    ProductCategory pCat;
    ProductCategoryHome pCatHome = (ProductCategoryHome) IDOLookup.getHome(ProductCategory.class);
    for (int i = 0; i < categoryIds.length; i++) {
      pCat = pCatHome.findByPrimaryKey(categoryIds[i]);
      //pCat = ((com.idega.block.trade.stockroom.data.ProductCategoryHome)com.idega.data.IDOLookup.getHomeLegacy(ProductCategory.class)).findByPrimaryKeyLegacy(categoryIds[i]);
      addCategory(pCat);
    }
  }

  @Override
public boolean addCategory(ProductCategory productCategory) {
    try {
      productCategory.addTo(this);
      return true;
    }catch (Exception e) {
      return false;
    }
  }

  @Override
public void removeCategory(ProductCategory productCategory) throws IDORemoveRelationshipException{
    this.idoRemoveFrom(productCategory);
  }

  @Override
public void removeAllFrom(Class entityInterface) throws IDORemoveRelationshipException{
    this.idoRemoveFrom(entityInterface);
  }

  @Override
public void addTravelAddresses(int[] addressIds) throws RemoteException, FinderException, IDOAddRelationshipException{
    TravelAddress address;
    TravelAddressHome home = (TravelAddressHome) IDOLookup.getHome(TravelAddress.class);
    if(addressIds != null){
      for (int i = 0; i < addressIds.length; i++) {
        address = home.findByPrimaryKey(addressIds[i]);
        addTravelAddress(address);
//	  product.addTo(TravelAddress.class, addressIds[i]);
      }
    }
  }

  @Override
public void addTravelAddress(TravelAddress address)  {
    try {
      this.idoAddTo(address);
    }catch (IDOAddRelationshipException e) {
      debug("product already connencted to address");
    }
  }

  @Override
public void removeTravelAddress(TravelAddress address) throws IDORemoveRelationshipException {
    this.idoRemoveFrom(address);
  }

  @Override
public String getProductName(int localeId, String returnIfNull) {
    LocalizedText text = TextFinder.getLocalizedText(this, localeId);
  		if (text == null) {
  			return returnIfNull;
  		} else {
  			return text.getHeadline();
  		}
  }

  @Override
public String getProductName(int localeId) {
	  LocalizedText text = TextFinder.getLocalizedText(this, localeId);
	    if (text == null) {
				text = TextFinder.getLocalizedText(this, 1);
			}
	    String name = "";
	    if (text != null) {
	    	name = text.getHeadline();
		    if(!StringUtil.isEmpty(name)){
		    	return name;
		    }
		    try{
		  	    TxText productText = getText();
		  	    if(productText != null){
			  	    ContentHelper contentHelper = ContentFinder.getContentHelper(productText.getContentId(), localeId, getDatasource());
			  	    name = contentHelper.getLocalizedText().getHeadline();
		  	    }
		    }catch (Exception e) {
		    	getLogger().log(Level.WARNING, "failed getting name for product " + getPrimaryKey() + " and locale ID " + localeId, e);
		  	}
	    }
	    return name == null ? CoreConstants.EMPTY : name;
  }

  @Override
public String getProductName(int localeId, int localeIDIfNull, String returnIfNull) {
  		String text = getProductName(localeId, returnIfNull);
  		if (text == null) {
  			return getProductName(localeIDIfNull, returnIfNull);
  		}
  		return text;
  }

  @Override
public void setProductName(int localeId, String name) {
    LocalizedText locText = TextFinder.getLocalizedText(this,localeId);
    boolean newLocText = false;
    if ( locText == null ) {
      locText = ((com.idega.block.text.data.LocalizedTextHome)com.idega.data.IDOLookup.getHomeLegacy(LocalizedText.class)).createLegacy();
      newLocText = true;
    }

    locText.setHeadline(name);

    if ( newLocText ) {
      locText.setLocaleId(localeId);
      try {
	locText.insert();
	locText.addTo(this);
      }
      catch (SQLException e) {
	e.printStackTrace(System.err);
      }
    }
    else {
      try {
	locText.update();
      }
      catch (SQLException e) {
	e.printStackTrace(System.err);
      }
    }
  }


  @Override
public String getProductDescription(int localeId) {
    LocalizedText text = TextFinder.getLocalizedText(this, localeId);
    if (text == null) {
			text = TextFinder.getLocalizedText(this, 1);
		}
    String description = "";
    if (text != null) {
      description = text.getBody();
    }
    return description;
  }

  @Override
public void setProductDescription(int localeId, String description) {
    LocalizedText locText = TextFinder.getLocalizedText(this,localeId);
    boolean newLocText = false;
    if ( locText == null ) {
      locText = ((com.idega.block.text.data.LocalizedTextHome)com.idega.data.IDOLookup.getHomeLegacy(LocalizedText.class)).createLegacy();
      newLocText = true;
    }

    locText.setBody(description);

    if ( newLocText ) {
      locText.setLocaleId(localeId);
      try {
	locText.insert();
	locText.addTo(this);
      }
      catch (SQLException e) {
	e.printStackTrace(System.err);
      }
    }
    else {
      try {
	locText.update();
      }
      catch (SQLException e) {
	e.printStackTrace(System.err);
      }
    }
  }


  @Override
public String getProductTeaser(int localeId) {
    LocalizedText text = TextFinder.getLocalizedText(this, localeId);
    if (text == null) {
			text = TextFinder.getLocalizedText(this, 1);
		}
    String teaser = "";
    if (text != null) {
      teaser = text.getTitle();
      if (teaser == null) {
				teaser = "";
			}
    }
    return teaser;
  }

  @Override
public void setProductTeaser(int localeId, String teaser) {
    LocalizedText locText = TextFinder.getLocalizedText(this,localeId);
    boolean newLocText = false;
    if ( locText == null ) {
      locText = ((com.idega.block.text.data.LocalizedTextHome)com.idega.data.IDOLookup.getHomeLegacy(LocalizedText.class)).createLegacy();
      newLocText = true;
    }

    locText.setTitle(teaser);

    if ( newLocText ) {
      locText.setLocaleId(localeId);
      try {
	locText.insert();
	locText.addTo(this);
      }
      catch (SQLException e) {
	e.printStackTrace(System.err);
      }
    }
    else {
      try {
	locText.update();
      }
      catch (SQLException e) {
	e.printStackTrace(System.err);
      }
    }
  }

  public Collection ejbFindProductsOrderedByProductCategory(int supplierId) throws FinderException {
    return ejbFindProductsOrderedByProductCategory(supplierId, -1, null, null);
  }

  public Collection ejbFindProductsOrderedByProductCategory(int supplierId, IWTimestamp stamp) throws FinderException {
    return ejbFindProductsOrderedByProductCategory(supplierId, -1, stamp, null);
  }

  public Collection ejbFindProductsOrderedByProductCategory(int supplierId, int productCategoryId, IWTimestamp from, IWTimestamp to) throws FinderException {
    return ejbFindProducts(supplierId, productCategoryId, from, to, ICCategoryBMPBean.getEntityTableName()+"."+ICCategoryBMPBean.getColumnName(), -1, -1, false);
  }

  public Collection ejbFindProducts(boolean onlyValidProducts, int supplierId) throws FinderException {
	  return ejbFindProducts(onlyValidProducts, supplierId, -1, -1);
  }

  public Collection ejbFindProducts(boolean onlyValidProducts, int supplierId, int productCategoryId, int firstEntity, int lastEntity) throws FinderException {
	  StringBuffer SQL = getSQL(onlyValidProducts, supplierId, productCategoryId, null, null,null, -1, false);
	  return this.idoFindPKsBySQL(SQL.toString(), lastEntity-firstEntity, firstEntity);
  }

  public Collection ejbFindProducts(boolean onlyValidProducts, int supplierId, int firstEntity, int lastEntity) throws FinderException {
	  return ejbFindProducts(onlyValidProducts,supplierId,firstEntity,lastEntity,true);
  }
  public Collection ejbFindProducts(boolean onlyValidProducts, int supplierId, int firstEntity, int lastEntity,boolean onlyEnabled) throws FinderException {
	    String pTable = com.idega.block.trade.stockroom.data.ProductBMPBean.getProductEntityName();

	    StringBuffer sqlQuery = new StringBuffer();
	      sqlQuery.append("SELECT * FROM ").append(pTable);
	      sqlQuery.append(" WHERE ");
	      if (onlyValidProducts){
	    	  	if(onlyEnabled){
	    	  		// Do not need to check if disabled
	    	  		sqlQuery.append(pTable).append(".")
	  				.append(com.idega.block.trade.stockroom.data.ProductBMPBean.getColumnNameIsValid())
	  				.append(" = 'Y'");
	    	  	}else{
	    	  		// When deleting disabling too
	    	  		sqlQuery.append("((").append(pTable).append(".")
	  				.append(com.idega.block.trade.stockroom.data.ProductBMPBean.getColumnNameIsValid())
	  				.append(" = 'Y') OR (").append(pTable).append(".")
	  				.append(COLUMN_DISABLED)
	  				.append(" = 'Y'))");
	    	  	}
	      }
	      if (supplierId != -1) {
	    	  if (onlyValidProducts)
	    		  sqlQuery.append(" AND ");
	    	  sqlQuery.append(pTable).append(".").append(com.idega.block.trade.stockroom.data.ProductBMPBean.getColumnNameSupplierId()).append(" = ").append(supplierId);
	      }

	      sqlQuery.append(" order by ").append(com.idega.block.trade.stockroom.data.ProductBMPBean.getColumnNameNumber());

	    return this.idoFindPKsBySQL(sqlQuery.toString(), lastEntity-firstEntity, firstEntity);
	  }


  public int ejbHomeGetProductCount(int supplierId) throws IDOException {
	  return ejbHomeGetProductCount(true, supplierId, -1);
  }
  public int ejbHomeGetProductCount(boolean onlyValidProducts, int supplierId,boolean onlyEnabled) throws IDOException {
	  return ejbHomeGetProductCount(onlyValidProducts, supplierId, -1,onlyEnabled);
  }
  public int ejbHomeGetProductCount(boolean onlyValidProducts, int supplierId) throws IDOException {
	  return ejbHomeGetProductCount(onlyValidProducts, supplierId, -1);
  }
  public int ejbHomeGetProductCount(int supplierId, int productCategoryId) throws IDOException {
	  return ejbHomeGetProductCount(true, supplierId, productCategoryId);
  }
  public int ejbHomeGetProductCount(boolean onlyValidProducts, int supplierId, int productCategoryId) throws IDOException{
	  return ejbHomeGetProductCount(onlyValidProducts, supplierId, productCategoryId, true);
  }
  public int ejbHomeGetProductCount(boolean onlyValidProducts, int supplierId, int productCategoryId,boolean onlyEnabled) throws IDOException {
	    String pTable = com.idega.block.trade.stockroom.data.ProductBMPBean.getProductEntityName();
	    ProductCategory pCat = (ProductCategory) GenericEntity.getStaticInstance(ProductCategory.class);
	    String catMiddle = EntityControl.getManyToManyRelationShipTableName(ProductCategory.class,Product.class);

	    StringBuffer sqlQuery = new StringBuffer();
	      sqlQuery.append("SELECT count(*) FROM ").append(pTable).append(" p, ").append(catMiddle);
	      sqlQuery.append(" c WHERE ");
	      if (onlyValidProducts){
	    	  	if(onlyEnabled){
	    	  		// Do not need to check if disabled
	    	  		sqlQuery.append("p.")
	  				.append(com.idega.block.trade.stockroom.data.ProductBMPBean.getColumnNameIsValid())
	  				.append(" = 'Y'");
	    	  	}else{
	    	  		// When deleting disabling too
	    	  		sqlQuery.append("((").append("p.")
	  				.append(com.idega.block.trade.stockroom.data.ProductBMPBean.getColumnNameIsValid())
	  				.append(" = 'Y') OR (").append("p.")
	  				.append(COLUMN_DISABLED)
	  				.append(" = 'Y'))  AND ");
	    	  	}
	      }
    	  sqlQuery.append("c."+getIdColumnName() +" = p."+getIdColumnName());
	      if (supplierId != -1) {
					sqlQuery.append(" AND ").append("p.").append(com.idega.block.trade.stockroom.data.ProductBMPBean.getColumnNameSupplierId()).append(" = ").append(supplierId);
				}
	      if (productCategoryId != -1) {
	    	  sqlQuery.append(" AND ");
	    	  sqlQuery.append("c."+pCat.getIDColumnName() +" = "+productCategoryId);
	        }

	      return this.idoGetNumberOfRecords(sqlQuery.toString());
  }


  public Collection ejbFindProducts(int supplierId, int productCategoryId ,IWTimestamp from, IWTimestamp to) throws FinderException{
    return ejbFindProducts(supplierId, productCategoryId, from, to, null);
  }

  public Collection ejbFindProducts(int supplierId, int productCategoryId ,IWTimestamp from, IWTimestamp to, String orderBy) throws FinderException{
    return ejbFindProducts(supplierId, productCategoryId, from, to, orderBy, -1, -1);
  }

  public int ejbHomeGetProductFilterNotConnectedToAnyProductCategory() {
    return this.FILTER_NOT_CONNECTED_TO_CATEGORY;
  }

  public Collection ejbFindProducts(int supplierId, int productCategoryId ,IWTimestamp from, IWTimestamp to, String orderBy, int localeId, int filter) throws FinderException{
		return ejbFindProducts(supplierId, productCategoryId, from, to, orderBy, localeId, filter, true);
  }

  public Collection ejbFindProducts(int supplierId, int productCategoryId ,IWTimestamp from, IWTimestamp to, String orderBy, int localeId, int filter, boolean useTimeframes) throws FinderException{
    Collection coll;

    StringBuffer timeframeSQL = getSQL(true, supplierId, productCategoryId, from, to,
			orderBy, localeId, useTimeframes);

//    System.out.println(timeframeSQL.toString());
    coll = this.idoFindPKsBySQL(timeframeSQL.toString());
//    products = EntityFinder.getInstance().findAll(Product.class,timeframeSQL.toString());
    return coll;
  }

private StringBuffer getSQL(boolean onlyValidProducts, int supplierId, int productCategoryId,
		IWTimestamp from, IWTimestamp to, String orderBy, int localeId,
		boolean useTimeframes) throws FinderException {
	String orderString = null;
    if (from != null && to != null && useTimeframes) {
    	orderString = com.idega.block.trade.stockroom.data.TimeframeBMPBean.getTimeframeFromColumnName();
    }
    if (orderBy != null) {// && from
    	orderString = orderBy;
    }

    Timeframe timeframe = (Timeframe) GenericEntity.getStaticInstance(Timeframe.class);
    ProductCategory pCat = (ProductCategory) GenericEntity.getStaticInstance(ProductCategory.class);
    LocalizedText locText = (LocalizedText) GenericEntity.getStaticInstance(LocalizedText.class);
    //Service tService = (Service) is.idega.idegaweb.travel.data.ServiceBMPBean.getStaticInstance(Service.class);

    String middleTable = EntityControl.getManyToManyRelationShipTableName(Timeframe.class,Product.class);
    String Ttable = com.idega.block.trade.stockroom.data.TimeframeBMPBean.getTimeframeTableName();
    String Ptable = com.idega.block.trade.stockroom.data.ProductBMPBean.getProductEntityName();
    String catMiddle = EntityControl.getManyToManyRelationShipTableName(ProductCategory.class,Product.class);
    String catTable = pCat.getEntityName();

    String locMiddleTable = EntityControl.getManyToManyRelationShipTableName(Product.class, LocalizedText.class);
    String locTxtTable = LocalizedTextBMPBean.getEntityTableName();


    StringBuffer timeframeSQL = new StringBuffer();
      timeframeSQL.append("SELECT distinct "+Ptable+".*");
      if (orderString != null) {
    	  timeframeSQL.append(", ").append(orderString);
      }
      timeframeSQL.append(" FROM "+Ptable);
      if (from != null && to != null && useTimeframes) {
        timeframeSQL.append(", "+Ttable+", "+middleTable);
      }
      if (localeId != -1) {
        timeframeSQL.append(", "+locMiddleTable+", "+locTxtTable);
      }

      timeframeSQL.append(", "+catMiddle+", "+catTable);
      timeframeSQL.append(" WHERE ");

      if (onlyValidProducts) {
    	  timeframeSQL.append(Ptable+"."+com.idega.block.trade.stockroom.data.ProductBMPBean.getColumnNameIsValid()+" = 'Y'");
      } else {
    	  timeframeSQL.append(Ptable+"."+com.idega.block.trade.stockroom.data.ProductBMPBean.getIdColumnName() + " is not null");
      }

      if (from != null && to != null && useTimeframes) {
        timeframeSQL.append(" AND ");
        timeframeSQL.append(Ttable+"."+timeframe.getIDColumnName()+" = "+middleTable+"."+timeframe.getIDColumnName());
        timeframeSQL.append(" AND ");
        timeframeSQL.append(Ptable+"."+this.getIDColumnName()+" = "+middleTable+"."+this.getIDColumnName());
      }
      timeframeSQL.append(" AND ");
      timeframeSQL.append(Ptable+"."+this.getIDColumnName()+" = "+catMiddle+"."+this.getIDColumnName());

      timeframeSQL.append(" AND ");
      timeframeSQL.append(catMiddle+"."+pCat.getIDColumnName() +" = "+catTable+"."+pCat.getIDColumnName());
      if (productCategoryId != -1) {
        timeframeSQL.append(" AND ");
        timeframeSQL.append(catMiddle+"."+pCat.getIDColumnName() +" = "+productCategoryId);
      }

    // Hondla ef supplierId != -1
    Collection tempProducts = null;
    if (supplierId != -1) {
			tempProducts = ejbFindProducts(onlyValidProducts, supplierId);
		}
    if (tempProducts != null) {
			if (tempProducts.size() > 0) {
			  timeframeSQL.append(" AND ");
			  timeframeSQL.append(Ptable+"."+this.getIDColumnName()+" in (");
			  Iterator iter = tempProducts.iterator();
			  Object item;
			  int counter = 0;
			  while (iter.hasNext()) {
			    item = iter.next();
			    ++counter;
			    if (counter == 1) {
			      timeframeSQL.append(item.toString() );
			    }else {
			      timeframeSQL.append(","+item.toString() );
			    }
			  }
			  timeframeSQL.append(")");
			}
		}

    if (from != null && to != null && useTimeframes) {
      timeframeSQL.append(" AND ");
      timeframeSQL.append("(");
      timeframeSQL.append(" ("+com.idega.block.trade.stockroom.data.TimeframeBMPBean.getTimeframeFromColumnName()+" <= '"+from.toSQLDateString()+"' AND "+com.idega.block.trade.stockroom.data.TimeframeBMPBean.getTimeframeToColumnName()+" >= '"+from.toSQLDateString()+"')");
      timeframeSQL.append(" OR ");
      timeframeSQL.append(" ("+com.idega.block.trade.stockroom.data.TimeframeBMPBean.getTimeframeFromColumnName()+" <= '"+to.toSQLDateString()+"' AND "+com.idega.block.trade.stockroom.data.TimeframeBMPBean.getTimeframeToColumnName()+" >= '"+to.toSQLDateString()+"')");
      timeframeSQL.append(" OR ");
      timeframeSQL.append(" ("+com.idega.block.trade.stockroom.data.TimeframeBMPBean.getTimeframeFromColumnName()+" >= '"+from.toSQLDateString()+"' AND "+com.idega.block.trade.stockroom.data.TimeframeBMPBean.getTimeframeToColumnName()+" <= '"+to.toSQLDateString()+"')");
      timeframeSQL.append(")");
    }

    if (localeId != -1) {
      timeframeSQL.append(" AND ")
          .append(locMiddleTable+"."+locText.getIDColumnName()+ " = "+locTxtTable+"."+locText.getIDColumnName())
          .append(" AND ")
          .append(locMiddleTable+"."+ProductBMPBean.getIdColumnName()+" = "+Ptable+"."+ProductBMPBean.getIdColumnName())
          .append(" AND ")
          .append(locTxtTable+"."+LocalizedTextBMPBean.getColumnNameLocaleId()+" = "+localeId);
    }

    /**
     * @todo b�ta vi� filter supporti
    switch (filter) {
      case FILTER_NOT_CONNECTED_TO_CATEGORY :
        break;
      default:
        break;
    }
     */

    if (orderBy != null) {
      timeframeSQL.append(" ORDER BY "+orderString);
    }else if (from != null && to != null) {
      timeframeSQL.append(" ORDER BY "+orderString);
    }
	return timeframeSQL;
}

  @Override
public List getDepartureAddresses(boolean ordered) throws IDOFinderException  {
    List list = EntityFinder.getInstance().findRelated(this, TravelAddress.class, com.idega.block.trade.stockroom.data.TravelAddressBMPBean.getColumnNameAddressTypeId(), Integer.toString(com.idega.block.trade.stockroom.data.TravelAddressBMPBean.ADDRESS_TYPE_DEPARTURE) );
    if (list == null) {
      list = new Vector();
    }
    return list;
  }

  @Override
public void addArrivalAddress(Address address)  {
    try {
      this.idoAddTo(address);
/*
      StringBuffer sql = new StringBuffer();
        sql.append("INSERT INTO ").append(EntityControl.getManyToManyRelationShipTableName(Product.class, Address.class))
           .append(" (SR_PRODUCT_ID, IC_ADDRESS_ID, ").append(TravelAddressBMPBean.getColumnNameAddressTypeId())
           .append(") VALUES (")
           .append(this.getPrimaryKey().toString()).append(", ")
           .append(address.getID()).append(", ")
           .append(Integer.toString(com.idega.block.trade.stockroom.data.TravelAddressBMPBean.ADDRESS_TYPE_ARRIVAL))
           .append(")");
      this.idoExecuteTableUpdate(sql.toString());*/
    }catch (Exception e) {
//      e.printStackTrace();
      debug("product already connected to address");
    }
  }

  @Override
public List getArrivalAddresses() throws IDOFinderException  {
    List list = EntityFinder.getInstance().findRelated(this, Address.class);//, com.idega.block.trade.stockroom.data.TravelAddressBMPBean.getColumnNameAddressTypeId(), Integer.toString(com.idega.block.trade.stockroom.data.TravelAddressBMPBean.ADDRESS_TYPE_ARRIVAL) );
    if (list == null) {
      list = new Vector();
    }
    return list;
  }

  @Override
public Collection getICFile() throws IDORelationshipException {
    return this.idoGetRelatedEntities(ICFile.class);
  }

  @Override
public void removeICFile(ICFile file) throws IDORemoveRelationshipException{
    this.idoRemoveFrom(file);
  }

  @Override
public void addICFile(ICFile file) throws IDOAddRelationshipException{
    this.idoAddTo(file);
  }

  @Override
public Collection getDiscountCodeGroups(){
    try {
		return this.idoGetRelatedEntities(DiscountCodeGroup.class);
	} catch (IDORelationshipException e) {
		getLogger().log(Level.WARNING, "Failed getting discount code groups of product " + getPrimaryKey(), e);
	}
	return Collections.EMPTY_LIST;
  }

  @Override
public void removeDiscountCodeGroup(DiscountCodeGroup discountCodeGroup){
    try {
		this.idoRemoveFrom(discountCodeGroup);
	} catch (IDORemoveRelationshipException e) {
		getLogger().log(Level.WARNING, "Failed removing discount code group "+discountCodeGroup +" from product " + getPrimaryKey(), e);
	}
  }

  @Override
public void addDiscountCodeGroup(DiscountCodeGroup discountCodeGroup){
    try {
		this.idoAddTo(discountCodeGroup);
	} catch (IDOAddRelationshipException e) {
		getLogger().log(Level.WARNING, "Failed adding discount code group "+discountCodeGroup +" to product " + getPrimaryKey(), e);
	}
  }


  @Override
public void addTimeframe(Timeframe frame) throws IDOAddRelationshipException{
    this.idoAddTo(frame);
  }

  @Override
public void removeTimeframe(Timeframe frame) throws IDORemoveRelationshipException{
    this.idoRemoveFrom(frame);
  }

  @Override
public void addText(TxText text) throws IDOAddRelationshipException{
    this.idoAddTo(text);
  }

	/* (non-Javadoc)
	 * @see com.idega.data.GenericEntity#delete()
	 */
	@Override
	public void delete() throws SQLException {
		this.removeFrom(MetaData.class);
		super.delete();
	}

	@Override
	public void setRefundable(boolean refundable) {
		setColumn(COLUMN_REFUNDABLE, refundable);
	}

	@Override
	public boolean getRefundable() {
		return getBooleanColumnValue(COLUMN_REFUNDABLE, true);
	}

	@Override
	public void setVoucherComment(String comment) {
		setColumn(COLUMN_VOUCHER_COMMENT, comment);
	}

	@Override
	public String getVoucherComment() {
		return getStringColumnValue(COLUMN_VOUCHER_COMMENT);
	}

	public Collection ejbFindBySupplyPool(SupplyPool pool) throws IDORelationshipException, FinderException, IDOCompositePrimaryKeyException {

		Table table = new Table(this);
		Table poolTable = new Table(pool);

		Column poolID = new Column(poolTable, pool.getEntityDefinition().getPrimaryKeyDefinition().getField().getSQLFieldName());

		SelectQuery query = new SelectQuery(table);
		query.addColumn(new WildCardColumn());

		query.addManyToManyJoin(table, poolTable);

		query.addCriteria(new MatchCriteria(new Column(table, getColumnNameIsValid()), MatchCriteria.EQUALS, true));
		query.addCriteria(new MatchCriteria(poolID, MatchCriteria.EQUALS, pool));

		return idoFindPKsByQuery(query);
	}

	public Collection ejbFindByPriceCategory(PriceCategory priceCategory) throws IDORelationshipException, FinderException {
		Table table = new Table(this);
		Table priceTable = new Table(ProductPrice.class);
		Table catTable = new Table(priceCategory);

		Column pk = new Column(table, getIDColumnName());
		pk.setAsDistinct();
		SelectQuery query = new SelectQuery(table);
		query.addColumn(pk);
		query.addJoin(priceTable, table);
		query.addJoin(priceTable, catTable);
		query.addCriteria(new MatchCriteria(new Column(catTable, "SR_PRICE_CATEGORY_ID"), MatchCriteria.EQUALS, priceCategory));
//		String sql = "select p.sr_product_id from sr_product p, SR_PRODUCT_PRODUCT_CATEGORY pc " +
//				"WHERE p.is_valid = 'Y' AND pc.sr_product_id = p.sr_product_id " +
//				"AND pc.sr_product_category_id = "+priceCategory.getPrimaryKey().toString();
//		return idoFindPKsBySQL(sql);
		return idoFindPKsByQuery(query);
	}

	@Override
	public Collection getVoucherAds()  throws IDORelationshipException {
		return idoGetRelatedEntities(VoucherAd.class);
	}
	@Override
	public void addVoucherAd(VoucherAd voucherAd) throws IDOAddRelationshipException {
		idoAddTo(voucherAd);
	}
}