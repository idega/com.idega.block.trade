/*
 * $Id: TravelAgentBusiness.java,v 1.1 2006/02/28 13:43:35 gimmi Exp $
 * Created on Jan 16, 2006
 *
 * Copyright (C) 2006 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.trade.stockroom.business;

import com.idega.business.IBOService;
import com.idega.user.data.User;


/**
 * <p>
 * TODO gimmi Describe Type TravelAgentBusiness
 * </p>
 *  Last modified: $Date: 2006/02/28 13:43:35 $ by $Author: gimmi $
 * 
 * @author <a href="mailto:gimmi@idega.com">gimmi</a>
 * @version $Revision: 1.1 $
 */
public interface TravelAgentBusiness extends IBOService {

	/**
	 * @see com.idega.block.trade.stockroom.business.TravelAgentBusinessBean#getDiscount
	 */
	public double getDiscount(User travelAgent) throws java.rmi.RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.TravelAgentBusinessBean#setDiscount
	 */
	public void setDiscount(User travelAgent, double discount) throws java.rmi.RemoteException;

	/**
	 * @see com.idega.block.trade.stockroom.business.TravelAgentBusinessBean#getMetaDataKeys
	 */
	public String[] getMetaDataKeys() throws java.rmi.RemoteException;
}
