package com.idega.block.trade.stockroom.data;


public class SupplierHomeImpl extends com.idega.data.IDOFactory implements SupplierHome
{
 protected Class getEntityInterfaceClass(){
  return Supplier.class;
 }

 public Supplier create() throws javax.ejb.CreateException{
  return (Supplier) super.idoCreate();
 }

 public Supplier createLegacy(){
	try{
		return create();
	}
	catch(javax.ejb.CreateException ce){
		throw new RuntimeException("CreateException:"+ce.getMessage());
	}

 }

 public Supplier findByPrimaryKey(int id) throws javax.ejb.FinderException{
  return (Supplier) super.idoFindByPrimaryKey(id);
 }

 public Supplier findByPrimaryKey(Object pk) throws javax.ejb.FinderException{
  return (Supplier) super.idoFindByPrimaryKey(pk);
 }

 public Supplier findByPrimaryKeyLegacy(int id) throws java.sql.SQLException{
	try{
		return findByPrimaryKey(id);
	}
	catch(javax.ejb.FinderException fe){
		throw new java.sql.SQLException(fe.getMessage());
	}

 }


}