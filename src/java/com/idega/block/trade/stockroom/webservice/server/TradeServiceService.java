/**
 * TradeServiceService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package com.idega.block.trade.stockroom.webservice.server;

public interface TradeServiceService extends javax.xml.rpc.Service {
    public java.lang.String getTradeServiceAddress();

    public com.idega.block.trade.stockroom.webservice.server.TradeService_PortType getTradeService() throws javax.xml.rpc.ServiceException;

    public com.idega.block.trade.stockroom.webservice.server.TradeService_PortType getTradeService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
