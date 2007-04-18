/**
 * TradeService_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package com.idega.block.trade.stockroom.webservice.client;

public interface TradeService_PortType extends java.rmi.Remote {
    public java.lang.String invalidateProductCache(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;
    public java.lang.String clearProductCache(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;
    public java.lang.String invalidatePriceCache(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;
}
