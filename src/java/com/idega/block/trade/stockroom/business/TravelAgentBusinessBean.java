/*
 * $Id: TravelAgentBusinessBean.java,v 1.1 2006/02/28 13:43:35 gimmi Exp $ Created on Jan
 * 16, 2006
 * 
 * Copyright (C) 2006 Idega Software hf. All Rights Reserved.
 * 
 * This software is the proprietary information of Idega hf. Use is subject to
 * license terms.
 */
package com.idega.block.trade.stockroom.business;

import com.idega.business.IBOServiceBean;
import com.idega.user.data.User;

public class TravelAgentBusinessBean extends IBOServiceBean  implements TravelAgentBusiness{

	private static final String METADATA_DISCOUNT = "travel.discount";
	
	public double getDiscount(User travelAgent) {
		String dis = travelAgent.getMetaData(METADATA_DISCOUNT);
		if (dis != null) {
			return  Double.parseDouble(dis);
		}
		
		return 0;
	}
	
	public void setDiscount(User travelAgent, double discount) {
		travelAgent.setMetaData(METADATA_DISCOUNT, Double.toString(discount));
	}
	
	public String[] getMetaDataKeys() {
		return new String[]{METADATA_DISCOUNT};
	}
}
