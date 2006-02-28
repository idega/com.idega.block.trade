/*
 * $Id: TravelAgentBusinessHomeImpl.java,v 1.1 2006/02/28 13:43:35 gimmi Exp $
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
 * TODO gimmi Describe Type TravelAgentBusinessHomeImpl
 * </p>
 *  Last modified: $Date: 2006/02/28 13:43:35 $ by $Author: gimmi $
 * 
 * @author <a href="mailto:gimmi@idega.com">gimmi</a>
 * @version $Revision: 1.1 $
 */
public class TravelAgentBusinessHomeImpl extends IBOHomeImpl implements TravelAgentBusinessHome {

	protected Class getBeanInterfaceClass() {
		return TravelAgentBusiness.class;
	}

	public TravelAgentBusiness create() throws javax.ejb.CreateException {
		return (TravelAgentBusiness) super.createIBO();
	}
}
