/**
 * TradeServiceSoapBindingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package com.idega.block.trade.stockroom.webservice.server;

public class TradeServiceSoapBindingSkeleton implements com.idega.block.trade.stockroom.webservice.server.TradeService_PortType, org.apache.axis.wsdl.Skeleton {
    private com.idega.block.trade.stockroom.webservice.server.TradeService_PortType impl;
    private static java.util.Map _myOperations = new java.util.Hashtable();
    private static java.util.Collection _myOperationsList = new java.util.ArrayList();

    /**
    * Returns List of OperationDesc objects with this name
    */
    public static java.util.List getOperationDescByName(java.lang.String methodName) {
        return (java.util.List)_myOperations.get(methodName);
    }

    /**
    * Returns Collection of OperationDescs
    */
    public static java.util.Collection getOperationDescs() {
        return _myOperationsList;
    }

    static {
        org.apache.axis.description.OperationDesc _oper;
        org.apache.axis.description.FaultDesc _fault;
        org.apache.axis.description.ParameterDesc [] _params;
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("invalidateProductCache", _params, new javax.xml.namespace.QName("", "invalidateProductCacheReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("idega:trade", "invalidateProductCache"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("invalidateProductCache") == null) {
            _myOperations.put("invalidateProductCache", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("invalidateProductCache")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("clearAddressMaps", _params, new javax.xml.namespace.QName("", "clearAddressMapsReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("idega:trade", "clearAddressMaps"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("clearAddressMaps") == null) {
            _myOperations.put("clearAddressMaps", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("clearAddressMaps")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("clearProductCache", _params, new javax.xml.namespace.QName("", "clearProductCacheReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("idega:trade", "clearProductCache"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("clearProductCache") == null) {
            _myOperations.put("clearProductCache", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("clearProductCache")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("invalidatePriceCache", _params, new javax.xml.namespace.QName("", "invalidatePriceCacheReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("idega:trade", "invalidatePriceCache"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("invalidatePriceCache") == null) {
            _myOperations.put("invalidatePriceCache", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("invalidatePriceCache")).add(_oper);
    }

    public TradeServiceSoapBindingSkeleton() {
        this.impl = new com.idega.block.trade.stockroom.webservice.server.TradeServiceSoapBindingImpl();
    }

    public TradeServiceSoapBindingSkeleton(com.idega.block.trade.stockroom.webservice.server.TradeService_PortType impl) {
        this.impl = impl;
    }
    public java.lang.String invalidateProductCache(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.invalidateProductCache(in0, in1);
        return ret;
    }

    public java.lang.String clearAddressMaps(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.clearAddressMaps(in0, in1);
        return ret;
    }

    public java.lang.String clearProductCache(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.clearProductCache(in0, in1);
        return ret;
    }

    public java.lang.String invalidatePriceCache(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.invalidatePriceCache(in0, in1);
        return ret;
    }

}
