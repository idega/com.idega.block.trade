/*
 * $Id: SupplierHomeImpl.java,v 1.12 2006/12/15 09:28:59 gimmi Exp $
 * Created on 18.6.2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.trade.stockroom.data;

import java.util.Collection;

import javax.ejb.FinderException;

import com.idega.data.IDOFactory;
import com.idega.data.IDOLookup;
import com.idega.data.IDORelationshipException;
import com.idega.user.data.Group;


/**
 * 
 *  Last modified: $Date: 2006/12/15 09:28:59 $ by $Author: gimmi $
 * 
 * @author <a href="mailto:gimmi@idega.com">gimmi</a>
 * @version $Revision: 1.12 $
 */
public class SupplierHomeImpl extends IDOFactory implements SupplierHome {

	protected Class getEntityInterfaceClass() {
		return Supplier.class;
	}

	public Supplier create() throws javax.ejb.CreateException {
		return (Supplier) super.createIDO();
	}

	public Supplier findByPrimaryKey(Object pk) throws javax.ejb.FinderException {
		return (Supplier) super.findByPrimaryKeyIDO(pk);
	}

	public Supplier createLegacy() {
		try {
			return create();
		}
		catch (javax.ejb.CreateException ce) {
			throw new RuntimeException("CreateException:" + ce.getMessage());
		}
	}

	public Supplier findByPrimaryKey(int id) throws javax.ejb.FinderException {
		return (Supplier) super.findByPrimaryKeyIDO(id);
	}

	public Supplier findByPrimaryKeyLegacy(int id) throws java.sql.SQLException {
		try {
			return findByPrimaryKey(id);
		}
		catch (javax.ejb.FinderException fe) {
			throw new java.sql.SQLException("FinderException:" + fe.getMessage());
		}
	}

	public Collection findAll(Group supplierManager) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		java.util.Collection ids = ((SupplierBMPBean) entity).ejbFindAll(supplierManager);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findWithTPosMerchant() throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		java.util.Collection ids = ((SupplierBMPBean) entity).ejbFindWithTPosMerchant();
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findAllByGroupID(int groupID) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		java.util.Collection ids = ((SupplierBMPBean) entity).ejbFindAllByGroupID(groupID);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findByPostalCodes(Group supplierManager, String[] from, String[] to, Collection criterias, String supplierName)
			throws IDORelationshipException, FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		java.util.Collection ids = ((SupplierBMPBean) entity).ejbFindByPostalCodes(supplierManager, from, to, criterias, supplierName);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findAllWithoutCreditCardMerchant(Group supplierManager) throws IDORelationshipException, FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		java.util.Collection ids = ((SupplierBMPBean) entity).ejbFindAllWithoutCreditCardMerchant(supplierManager);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findAllWithCreditCardMerchant(Group supplierManager) throws IDORelationshipException, FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		java.util.Collection ids = ((SupplierBMPBean) entity).ejbFindAllWithCreditCardMerchant(supplierManager);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}
	
	public Supplier findByProductId(int productId) {
//		TODO: make query instead of this
		try {
			ProductHome productHome = (ProductHome)IDOLookup.getHome(
					Product.class
			);
			return productHome.findByPrimaryKey(productId).getSupplier();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
