/**
 * TradeServiceSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package com.idega.block.trade.stockroom.webservice.server;

import com.idega.block.trade.stockroom.business.ProductBusiness;
import com.idega.block.trade.stockroom.business.ProductPriceBusiness;
import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.idegaweb.IWMainApplication;

public class TradeServiceSoapBindingImpl implements com.idega.block.trade.stockroom.webservice.server.TradeService_PortType{


	private final String SUCCESS = "iwtravel-ok";
	private final String FAILED  = "iwtravel-failed";

	public java.lang.String invalidateProductCache(java.lang.String productID, java.lang.String remoteCallingHostName) throws java.rmi.RemoteException {
		ProductBusiness pb;
		try {
			pb = (ProductBusiness) IBOLookup.getServiceInstance(IWMainApplication.getDefaultIWApplicationContext(), ProductBusiness.class);
			//String remoteCallingHostName = com.idega.axis.util.AxisUtil.getHttpServletRequest().getRemoteHost();
			boolean success = pb.invalidateProductCache(productID, remoteCallingHostName);
			if(success){
				return SUCCESS;
			}
			else{
				return FAILED;
			}
		}
		catch (IBOLookupException e) {
			e.printStackTrace();
			return "Service failed to decache product = "+productID+". The error message was :"+e.getMessage();
		}
	}

	public java.lang.String clearProductCache(java.lang.String supplierID, java.lang.String remoteCallingHostName) throws java.rmi.RemoteException {
		ProductBusiness pb;
		try {
			pb = (ProductBusiness) IBOLookup.getServiceInstance(IWMainApplication.getDefaultIWApplicationContext(), ProductBusiness.class);
			//String remoteCallingHostName = com.idega.axis.util.AxisUtil.getHttpServletRequest().getRemoteHost();
			boolean success = pb.clearProductCache(supplierID, remoteCallingHostName);
			if(success){
				return SUCCESS;
			}
			else{
				return FAILED;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return "Service failed to decache prodcct for supplier = "+supplierID+". The error message was :"+e.getMessage();
		}
	}

	public java.lang.String invalidatePriceCache(java.lang.String productID, java.lang.String remoteCallingHostName) throws java.rmi.RemoteException {
		ProductPriceBusiness pb;
		try {
			pb = (ProductPriceBusiness) IBOLookup.getServiceInstance(IWMainApplication.getDefaultIWApplicationContext(), ProductPriceBusiness.class);
			//String remoteCallingHostName = com.idega.axis.util.AxisUtil.getHttpServletRequest().getRemoteHost();
			boolean success = pb.invalidateCache(productID, remoteCallingHostName);
			if(success){
				return SUCCESS;
			}
			else{
				return FAILED;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return "Service failed to decache price for product = "+productID+". The error message was :"+e.getMessage();
		}
	}

}
