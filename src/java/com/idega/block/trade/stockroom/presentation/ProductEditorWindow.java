package com.idega.block.trade.stockroom.presentation;

import java.sql.SQLException;
import com.idega.block.media.presentation.ImageInserter;
import com.idega.block.trade.stockroom.business.*;
import com.idega.block.trade.stockroom.data.Product;
import com.idega.idegaweb.*;
import com.idega.idegaweb.presentation.IWAdminWindow;
import com.idega.presentation.*;
import com.idega.presentation.ui.*;
import com.idega.presentation.text.*;

/**
 * Title:        idegaWeb TravelBooking
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      idega
 * @author <a href="mailto:gimmi@idega.is">Grimur Jonsson</a>
 * @version 1.0
 */

public class ProductEditorWindow extends IWAdminWindow {
  public static final String IW_BUNDLE_IDENTIFIER = "com.idega.block.trade";
  public static final String PRODUCT_ID = "prod_edit_prod_id";

  private static final String ACTION = "prod_edit_action";
  private static final String PAR_SAVE = "prod_edit_save";
  private static final String PAR_DELETE = "prod_edit_del";
  private static final String PAR_DEL_VERIFIED = "prod_edit_del_verified";
  private static final String PAR_CLOSE = "prod_edit_close";
  private static final String PAR_NUMBER = "prod_edit_number";
  private static final String PAR_NAME = "prod_edit_name";
  private static final String PAR_DESCRIPTION = "prod_edit_description";
  private static final String PAR_PRICE = "prod_edit_price";
  private static final String PAR_IMAGE = "prod_edit_image";

  private IWResourceBundle iwrb;
  private IWBundle bundle;

  private Product _product = null;
  private int _productId = -1;

  public ProductEditorWindow() {
    setUnMerged();
    setWidth(500);
    setTitle("Product Editor");
    setName("Product Editor");
  }


  public void main(IWContext iwc) {
    init(iwc);

    String action = iwc.getParameter(ACTION);
    if (action == null || action.equals("")) {
      displayForm(iwc);
    }else if (action.equals(this.PAR_SAVE)) {
      if (saveProduct(iwc)) {
        displayForm(iwc);
      }else {

      }
    }else if (action.equals(this.PAR_DELETE)) {
      verifyDelete(iwc);
    }else if (action.equals(this.PAR_DEL_VERIFIED)) {
      if (deleteProduct(iwc)) {
        closeWindow();
      }
    }else if (action.equals(this.PAR_CLOSE)) {
      closeWindow();
    }

  }

  public String getBundleIdentifier(){
    return IW_BUNDLE_IDENTIFIER;
  }

  private void init(IWContext iwc) {
    bundle = getBundle(iwc);
    iwrb = bundle.getResourceBundle(iwc);

    try {
      String sProductId = iwc.getParameter(this.PRODUCT_ID);
      _productId = Integer.parseInt(sProductId);
      if (_productId != -1) {
        _product = ProductBusiness.getProduct(_productId);
      }
    }catch (Exception e) {
      e.printStackTrace(System.err);
    }
  }

  public static Link getEditorLink(int productId) {
    Link link = new Link();
      link.setWindowToOpen(ProductEditorWindow.class);
      link.addParameter(ProductEditor.PRODUCT_ID, productId);
    return link;
  }

  private void displayForm(IWContext iwc) {
    simpleForm(iwc);

  }

  private void simpleForm(IWContext iwc) {
    TextInput number = new TextInput(PAR_NUMBER);
    TextInput name = new TextInput(PAR_NAME);
    TextArea description = new TextArea(PAR_DESCRIPTION);
    TextInput price = new TextInput(PAR_PRICE);
    ImageInserter imageInserter = new ImageInserter(PAR_IMAGE);
      name.setSize(50);
      description.setWidth(50);
      description.setHeight(5);

    if (_product != null) {
      number.setContent(_product.getNumber());
      name.setContent(ProductBusiness.getProductName(_product));
      description.setContent(ProductBusiness.getProductDescription(_product));
      int imageId = _product.getFileId();
      if (imageId != -1) {
        imageInserter = new ImageInserter(imageId, PAR_IMAGE);
      }
    }
    imageInserter.setHasUseBox(false);


    super.addHiddenInput(new HiddenInput(this.PRODUCT_ID, Integer.toString(_productId)));

    super.addLeft(iwrb.getLocalizedString("item_number","Item number"), number, true);
    super.addLeft(iwrb.getLocalizedString("name","Name"), name, true);
    super.addLeft(iwrb.getLocalizedString("description","Description"), description, true);
    super.addLeft(iwrb.getLocalizedString("price","Price"), price, true);


    super.addRight(iwrb.getLocalizedString("image","Image"), imageInserter, false);

    SubmitButton saveBtn = new SubmitButton(iwrb.getLocalizedImageButton("save","Save"), this.ACTION, this.PAR_SAVE);
    SubmitButton deleteBtn = new SubmitButton(iwrb.getLocalizedImageButton("delete","Delete"), this.ACTION, this.PAR_DELETE);
    SubmitButton closeBtn = new SubmitButton(iwrb.getLocalizedImageButton("close","Close"), this.ACTION, this.PAR_CLOSE);

    super.addSubmitButton(closeBtn);
    super.addSubmitButton(deleteBtn);
    super.addSubmitButton(saveBtn);
  }

  private boolean saveProduct(IWContext iwc) {
    String number = iwc.getParameter(PAR_NUMBER);
    String name = iwc.getParameter(PAR_NAME);
    String description = iwc.getParameter(PAR_DESCRIPTION);
    String price = iwc.getParameter(PAR_PRICE);
    String image = iwc.getParameter(PAR_IMAGE);

    Integer fileId = null;
    try {
      fileId = new Integer(image);
    }catch (NumberFormatException n) {}

    if (!name.equals("")) {
      if (_product == null) {
        try {
          _productId = ProductBusiness.createProduct(fileId, name, number, description, true);
          _product = ProductBusiness.getProduct(_productId);
          debug("Insert : id "+_productId);
          return true;
        }catch (Exception e) {
          e.printStackTrace(System.err);
        }
      }else {
        try {
          debug("Update : id "+_productId);
          ProductBusiness.updateProduct(this._productId, fileId, name, number, description, true);
          return true;
        }catch (Exception e) {
          e.printStackTrace(System.err);
        }
      }
    }

    return false;
  }

  private void saveFailed(IWContext iwc) {
    super.addLeft(iwrb.getLocalizedString("save_failed","Save failed"), "" );

    BackButton back = new BackButton(iwrb.getLocalizedImageButton("back","Back"));

    super.addSubmitButton(back);
  }

  private void verifyDelete(IWContext iwc) {
    super.addHiddenInput(new HiddenInput(this.PRODUCT_ID, Integer.toString(_productId)));
    StringBuffer text = new StringBuffer();
      text.append(iwrb.getLocalizedString("are_you_sure_you_want_to_delete","Are you sure you want to delete this product")).append(" : ").append(ProductBusiness.getProductName(_product));
    super.addLeft(iwrb.getLocalizedString("delete","Delete"), text.toString() );

    SubmitButton ok = new SubmitButton(iwrb.getLocalizedImageButton("ok","OK"), this.ACTION, this.PAR_DEL_VERIFIED);
    SubmitButton cancel = new SubmitButton(iwrb.getLocalizedImageButton("cancel","Cancel"), this.ACTION, "");

    super.addSubmitButton(ok);
    super.addSubmitButton(cancel);
  }

  private boolean deleteProduct(IWContext iwc) {
    try {
      ProductBusiness.deleteProduct(_product);
      return true;
    }catch (SQLException sql) {
      sql.printStackTrace(System.err);
      return false;
    }
  }

  private void closeWindow() {
    this.setParentToReload();
    this.close();
  }

  private Text getText(String content) {
    Text text = new Text(content);

    return text;
  }
 }