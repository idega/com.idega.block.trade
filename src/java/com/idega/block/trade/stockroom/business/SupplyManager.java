package com.idega.block.trade.stockroom.business;

import java.sql.Timestamp;
import java.sql.SQLException;

/**
 * Title:        IW Trade
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      idega.is
 * @author 2000 - idega team - <br><a href="mailto:gummi@idega.is">Gudmundur Agust Saemundsson</a><br><b><a href="mailto:gimmi@idega.is">Grimur Jonsson</a></b>
 * @version 1.0
 */

public interface SupplyManager {

  public void addSupplies(int product_id, float amount);
  public void depleteSupplies(int product_id, float amount);
  public void setSupplyStatus(int product_id, float status);
  public float getSupplyStatus(int product_id) throws SQLException ;
  public float getSupplyStatus(int product_id, Timestamp time);
  public void setPrice(int product_id, Timestamp time);
  public float getPrice(int product_id, Timestamp time);
  public void createPriceCategory(  );

  public int createProduct(int supplierId, Integer fileId, String productName, String ProductDescription, boolean isValid) throws Exception;


} // interface SupplyManager

