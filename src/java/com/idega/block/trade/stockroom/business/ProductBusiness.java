package com.idega.block.trade.stockroom.business;


import com.idega.data.IDOException;
import com.idega.presentation.IWContext;
import com.idega.presentation.ui.DropdownMenu;
import com.idega.data.IDOFinderException;
import java.sql.SQLException;
import com.idega.block.trade.stockroom.data.ProductHome;
import java.rmi.RemoteException;
import com.idega.data.IDORelationshipException;
import java.util.Collection;
import com.idega.block.category.data.ICCategory;
import com.idega.block.trade.stockroom.data.Product;
import javax.ejb.FinderException;
import com.idega.block.trade.stockroom.data.ProductCategory;
import com.idega.util.IWTimestamp;
import com.idega.block.trade.stockroom.data.ProductPriceHome;
import javax.ejb.EJBException;
import com.idega.business.IBOService;
import java.util.List;
import com.idega.block.trade.stockroom.data.Timeframe;

public interface ProductBusiness extends IBOService {
	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#updateProduct
	 */
	public int updateProduct(int productId, int supplierId, Integer fileId,
			String productName, String number, String productDescription,
			boolean isValid, int discountTypeId) throws Exception,
			RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#updateProduct
	 */
	public int updateProduct(int productId, Integer fileId, String productName,
			String number, String productDescription, boolean isValid,
			int localeId) throws Exception, RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#createProduct
	 */
	public int createProduct(Integer fileId, String productName, String number,
			String productDescription, boolean isValid, int localeId)
			throws Exception, RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#createProduct
	 */
	public int createProduct(Integer fileId, String productName, String number,
			String productDescription, boolean isValid) throws Exception,
			RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#createProduct
	 */
	public int createProduct(int supplierId, Integer fileId,
			String productName, String number, String productDescription,
			boolean isValid, int discountTypeId) throws Exception,
			RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#createProduct
	 */
	public int createProduct(int productId, int supplierId, Integer fileId,
			String productName, String number, String productDescription,
			boolean isValid, int discountTypeId) throws Exception,
			RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#createProduct
	 */
	public int createProduct(int productId, int supplierId, Integer fileId,
			String productName, String number, String productDescription,
			boolean isValid, int discountTypeId, int localeId)
			throws Exception, RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#getProductIdParameter
	 */
	public String getProductIdParameter() throws RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#getParameterLocaleDrop
	 */
	public String getParameterLocaleDrop() throws RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#getProduct
	 */
	public Product getProduct(Integer productId) throws RemoteException,
			FinderException, RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#getProduct
	 */
	public Product getProduct(int productId) throws RemoteException,
			FinderException, RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#invalidateProductCache
	 */
	public boolean invalidateProductCache(String productID)
			throws RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#invalidateProductCache
	 */
	public boolean invalidateProductCache(String productID,
			String remoteDomainToExclude) throws RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#deleteProduct
	 */
	public void deleteProduct(Product product) throws RemoteException,
			IDOException, RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#updateProduct
	 */
	public Product updateProduct(Product product) throws RemoteException,
			FinderException, IDOException, RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#getProductCategory
	 */
	public ProductCategory getProductCategory(int categoryID)
			throws RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#getProductNameWithNumber
	 */
	public String getProductNameWithNumber(Product product)
			throws RemoteException, RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#getProductNameWithNumber
	 */
	public String getProductNameWithNumber(Product product, int localeID)
			throws RemoteException, RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#getProductNameWithNumber
	 */
	public String getProductNameWithNumber(Product product,
			boolean numberInFront) throws RemoteException, RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#getProductNameWithNumber
	 */
	public String getProductNameWithNumber(Product product,
			boolean numberInFront, int localeID) throws RemoteException,
			RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#getSelectedLocaleId
	 */
	public int getSelectedLocaleId(IWContext iwc) throws RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#getLocaleDropDown
	 */
	public DropdownMenu getLocaleDropDown(IWContext iwc) throws RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#clearProductCache
	 */
	public boolean clearProductCache(String supplierId) throws RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#clearProductCache
	 */
	public boolean clearProductCache(String supplierId,
			String remoteDomainToExclude) throws RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#getProducts
	 */
	public List getProducts(IWContext iwc, int supplierId)
			throws RemoteException, RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#getProducts
	 */
	public List getProducts(int supplierId) throws RemoteException, RemoteException;
	public List getProducts(boolean onlyValidProducts, int supplierId) throws RemoteException, RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#getProduct
	 */
	public Collection getProduct(int supplierId, int firstEntity, int lastEntity)
			throws FinderException, RemoteException, RemoteException;
	public Collection getProduct(boolean onlyValidProducts, int supplierId, int firstEntity, int lastEntity)
			throws FinderException, RemoteException, RemoteException;
	public Collection getProduct(boolean onlyValidProducts, int supplierId, int firstEntity, int lastEntity,boolean onlyEnabled)
	throws FinderException, RemoteException, RemoteException;
	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#getProducts
	 */
	public List getProducts() throws RemoteException, FinderException,
			RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#getProducts
	 */
	public List getProducts(List productCategories) throws RemoteException,
			FinderException, RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#getProducts
	 */
	public List getProducts(ICCategory category) throws RemoteException,
			FinderException, RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#getProducts
	 */
	public List getProducts(ProductCategory productCategory)
			throws RemoteException, FinderException, RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#getProducts
	 */
	public List getProducts(IWTimestamp stamp) throws RemoteException,
			FinderException, RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#getProducts
	 */
	public List getProducts(IWTimestamp fromStamp, IWTimestamp toStamp)
			throws RemoteException, FinderException, RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#getProducts
	 */
	public List getProducts(int supplierId, IWTimestamp stamp)
			throws RemoteException, FinderException, RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#getProducts
	 */
	public List getProducts(int supplierId, IWTimestamp from, IWTimestamp to)
			throws RemoteException, FinderException, RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#getProducts
	 */
	public List getProducts(int supplierId, int productCategoryId,
			IWTimestamp from, IWTimestamp to) throws RemoteException,
			FinderException, RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#getTimeframe
	 */
	public Timeframe getTimeframe(Product product, IWTimestamp stamp)
			throws RemoteException, EJBException, FinderException,
			RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#getTimeframes
	 */
	public Timeframe[] getTimeframes(Product product) throws SQLException,
			RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#getTimeframe
	 */
	public Timeframe getTimeframe(Product product) throws SQLException,
			RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#getTimeframe
	 */
	public Timeframe getTimeframe(Product product, IWTimestamp stamp,
			int travelAddressId) throws RemoteException, EJBException,
			FinderException, RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#getTimeframe
	 */
	public Timeframe getTimeframe(Product product, Timeframe[] timeframes,
			IWTimestamp stamp, int travelAddressId) throws RemoteException,
			EJBException, FinderException, RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#getDropdownMenuWithProducts
	 */
	public DropdownMenu getDropdownMenuWithProducts(IWContext iwc,
			int supplierId) throws RemoteException, RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#getDropdownMenuWithProducts
	 */
	public DropdownMenu getDropdownMenuWithProducts(IWContext iwc,
			int supplierId, String parameterName) throws RemoteException,
			RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#getDropdownMenuWithProducts
	 */
	public DropdownMenu getDropdownMenuWithProducts(IWContext iwc,
			List products, String parameterName) throws RemoteException,
			RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#getProductCategories
	 */
	public List getProductCategories() throws IDOFinderException,
			RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#getProductCategories
	 */
	public List getProductCategories(Product product) throws RemoteException,
			IDORelationshipException, RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#getProductHome
	 */
	public ProductHome getProductHome() throws RemoteException, RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#getProductPriceHome
	 */
	public ProductPriceHome getProductPriceHome() throws RemoteException,
			RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.ProductBusinessBean#getProductPriceBusiness
	 */
	public ProductPriceBusiness getProductPriceBusiness()
			throws RemoteException;
	 
	public boolean displayProductForPublic(Product product);

}