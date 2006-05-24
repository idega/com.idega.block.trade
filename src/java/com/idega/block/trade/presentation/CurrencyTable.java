/*
 * $Id$
 * Created on May 24, 2006
 *
 * Copyright (C) 2006 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.trade.presentation;

import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;

import com.idega.block.trade.business.CurrencyBusiness;
import com.idega.block.trade.business.CurrencyHolder;
import com.idega.idegaweb.IWResourceBundle;
import com.idega.presentation.Block;
import com.idega.presentation.IWContext;
import com.idega.presentation.Span;
import com.idega.presentation.Table2;
import com.idega.presentation.TableCell2;
import com.idega.presentation.TableRow;
import com.idega.presentation.TableRowGroup;
import com.idega.presentation.text.Text;


public class CurrencyTable extends Block {

	public static final String IW_BUNDLE_IDENTIFIER = "com.idega.block.trade";
	
	private int iMinimumFractionDigits = 3;
	private int iMaximumFractionDigits = 3;
	
	public void main(IWContext iwc) {
		IWResourceBundle iwrb = getResourceBundle(iwc);
		
		List currencies = CurrencyBusiness.getCurrencyList();
		
		Table2 table = new Table2();
		table.setCellpadding(0);
		table.setCellspacing(0);
		
		TableRowGroup group = table.createHeaderRowGroup();
		TableRow row = group.createRow();
		
		TableCell2 cell = row.createHeaderCell();
		cell.setStyleClass("firstColumn");
		cell.setStyleClass("currency");
		cell.add(new Span(new Text(iwrb.getLocalizedString("currency", "Currency"))));
		
		cell = row.createHeaderCell();
		cell.setStyleClass("buyValue");
		cell.add(new Span(new Text(iwrb.getLocalizedString("buy_value", "Buy value"))));
		
		cell = row.createHeaderCell();
		cell.setStyleClass("lastColumn");
		cell.setStyleClass("sellValue");
		cell.add(new Span(new Text(iwrb.getLocalizedString("sell_value", "Sell value"))));
		
		group = table.createBodyRowGroup();
		int iRow = 1;
		NumberFormat format = NumberFormat.getInstance(iwc.getCurrentLocale());
		format.setMinimumFractionDigits(this.iMinimumFractionDigits);
		format.setMaximumFractionDigits(this.iMaximumFractionDigits);
		
		Iterator iter = currencies.iterator();
		while (iter.hasNext()) {
			row = group.createRow();
			
			CurrencyHolder holder = (CurrencyHolder) iter.next();
			
			cell = row.createCell();
			cell.setStyleClass("firstColumn");
			cell.setStyleClass("currency");
			cell.setStyleClass(holder.getCurrencyAbbreviation());
			cell.add(new Text(holder.getCurrencyAbbreviation()));

			cell = row.createCell();
			cell.setStyleClass("buyValue");
			cell.add(new Text(format.format(holder.getBuyValue())));
			
			cell = row.createCell();
			cell.setStyleClass("lastColumn");
			cell.setStyleClass("sellValue");
			cell.add(new Text(format.format(holder.getSellValue())));
			
			if (iRow % 2 == 0) {
				row.setStyleClass("evenRow");
			}
			else {
				row.setStyleClass("oddRow");
			}
			iRow++;
		}
		
		add(table);
	}
		
	public String getBundleIdentifier(){
		return IW_BUNDLE_IDENTIFIER;
	}

	
	public void setMaximumFractionDigits(int maximumFractionDigits) {
		this.iMaximumFractionDigits = maximumFractionDigits;
	}

	
	public void setMinimumFractionDigits(int minimumFractionDigits) {
		this.iMinimumFractionDigits = minimumFractionDigits;
	}
}