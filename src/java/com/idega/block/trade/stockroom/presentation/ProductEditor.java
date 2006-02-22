package com.idega.block.trade.stockroom.presentation;

import java.rmi.RemoteException;

import com.idega.presentation.Block;
import com.idega.presentation.IWContext;
/**
 * Title:        idegaWeb TravelBooking
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      idega
 * @author <a href="mailto:gimmi@idega.is">Grimur Jonsson</a>
 * @version 1.0
 */

public class ProductEditor extends Block {
  private static final String IW_BUNDLE_IDENTIFIER = "com.idega.block.trade";
  public static final String PRODUCT_ID = "prod_edit_prod_id";

  public ProductEditor() {
  }

  public void main(IWContext iwc) throws RemoteException{
    init(iwc);
  }

  public String getBundleIdentifier(){
    return IW_BUNDLE_IDENTIFIER;
  }

  private void init(IWContext iwc) throws RemoteException {
  }
}