/*
 * $Id: SupplierManagerBusinessHomeImpl.java,v 1.8 2006/02/28 13:44:29 gimmi Exp $
 * Created on Jan 16, 2006
 *
 * Copyright (C) 2006 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.trade.stockroom.business;

import com.idega.business.IBOHomeImpl;


/**
 * <p>
 * TODO gimmi Describe Type SupplierManagerBusinessHomeImpl
 * </p>
 *  Last modified: $Date: 2006/02/28 13:44:29 $ by $Author: gimmi $
 * 
 * @author <a href="mailto:gimmi@idega.com">gimmi</a>
 * @version $Revision: 1.8 $
 */
public class SupplierManagerBusinessHomeImpl extends IBOHomeImpl implements SupplierManagerBusinessHome {

	protected Class getBeanInterfaceClass() {
		return SupplierManagerBusiness.class;
	}

	public SupplierManagerBusiness create() throws javax.ejb.CreateException {
		return (SupplierManagerBusiness) super.createIBO();
	}
}
