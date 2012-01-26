package com.idega.block.trade.business;


/**
 * Title:TradeBundleStarter
 * Description: TradeBundleStarter implements the IWBundleStartable interface. The start method of this
 * object is called during the Bundle loading when starting up a idegaWeb applications.
 * Copyright:    Copyright (c) 2001
 * Company:      idega software
 * @author Eirikur S. Hrafnsson eiki@idega.is
 * @version 1.0
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.CreateException;

import com.idega.block.trade.stockroom.business.SupplierManagerBusinessBean;
import com.idega.block.trade.stockroom.data.ResellerStaffGroupBMPBean;
import com.idega.block.trade.stockroom.data.SupplierStaffGroupBMPBean;
import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.core.component.data.ICObject;
import com.idega.core.data.ICApplicationBinding;
import com.idega.core.data.ICApplicationBindingHome;
import com.idega.data.IDOFactory;
import com.idega.data.IDOLookup;
import com.idega.data.IDOLookupException;
import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWBundleStartable;
import com.idega.idegaweb.IWMainApplicationSettings;
import com.idega.user.business.GroupBusiness;
import com.idega.util.EventTimer;
import com.idega.util.database.ConnectionBroker;
import com.idega.util.database.PoolManager;

public class TradeBundleStarter implements IWBundleStartable,ActionListener{
	
	private IWBundle bundle_;
	private EventTimer timer;
	public static final String IW_CURRENCY_TIMER = "iw_currency_timer";
	public static final String DATASOURCE = "travel.datasource";
	
	public TradeBundleStarter() {
	}
	
	public void start(IWBundle bundle){
		this.bundle_ = bundle;
		checkDataSource(bundle);
		this.timer = new EventTimer(EventTimer.THREAD_SLEEP_24_HOURS/2,IW_CURRENCY_TIMER);
		this.timer.addActionListener(this);
		//Starts the thread while waiting for 3 mins. before the idegaWebApp starts up.
		// -- Fix for working properly on Interebase with entity-auto-create-on.
		this.timer.start(3*60*1000);
		System.out.println("Trade bundle starter: starting");
		createGroupTypes(bundle);
	}
	
	private void createGroupTypes(IWBundle bundle) {
		try {
			GroupBusiness gb = (GroupBusiness) IBOLookup.getServiceInstance(bundle.getApplication().getIWApplicationContext(), GroupBusiness.class);
			gb.createGroupTypeOrUpdate(SupplierStaffGroupBMPBean.GROUP_TYPE_VALUE, false);
			gb.createGroupTypeOrUpdate(ResellerStaffGroupBMPBean.GROUP_TYPE_VALUE, false);
			gb.createGroupTypeOrUpdate(SupplierManagerBusinessBean.SUPPLIER_ADMINISTRATOR_GROUP_DESCRIPTION, true);
			gb.createGroupTypeOrUpdate(SupplierManagerBusinessBean.SUPPLIER_MANAGER_ADMIN_GROUP_TYPE, true);
			gb.createGroupTypeOrUpdate(SupplierManagerBusinessBean.SUPPLIER_MANAGER_BOOKING_STAFF_TYPE, true);
			gb.createGroupTypeOrUpdate(SupplierManagerBusinessBean.SUPPLIER_MANAGER_CASHIER_STAFF_TYPE, true);
			gb.createGroupTypeOrUpdate(SupplierManagerBusinessBean.SUPPLIER_MANAGER_GROUP_TYPE, true);
			gb.createGroupTypeOrUpdate(SupplierManagerBusinessBean.SUPPLIER_MANAGER_GROUP_TYPE_COLLECTION, true);
			gb.createGroupTypeOrUpdate(SupplierManagerBusinessBean.SUPPLIER_MANAGER_PARTNER_STAFF_TYPE, true);
			gb.createGroupTypeOrUpdate(SupplierManagerBusinessBean.SUPPLIER_MANAGER_RESELLER_GROUP_TYPE, true);
			gb.createGroupTypeOrUpdate(SupplierManagerBusinessBean.SUPPLIER_MANAGER_SUPPLIER_GROUP_TYPE, true);
			gb.createGroupTypeOrUpdate(SupplierManagerBusinessBean.SUPPLIER_MANAGER_TRAVEL_AGENT_STAFF_TYPE, true);
			gb.createGroupTypeOrUpdate(SupplierManagerBusinessBean.SUPPLIER_MANAGER_USER_GROUP_TYPE, true);
		} catch (IBOLookupException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @param bundle
	 */
	private void checkDataSource(IWBundle bundle) {
		// Switching the datasource
		IWMainApplicationSettings settings = bundle.getApplication().getIWApplicationContext().getApplicationSettings();
		String dataSource = settings.getProperty(DATASOURCE);
		
		if (dataSource == null) {
			dataSource = bundle.getProperty("datasource");
			if (dataSource != null) {
				settings.setProperty(DATASOURCE, dataSource, "travel.binding");
			}
		}
		
		try {
			if (dataSource != null && (PoolManager.getInstance().hasDatasource(dataSource) || ConnectionBroker.getDataSource(dataSource) != null)) {
				Collection entities = bundle.getDataObjects();
				if (entities != null){
					Iterator iter = entities.iterator();
					while (iter.hasNext())
					{
						ICObject ico = (ICObject) iter.next();
						try
						{
							Class c = ico.getObjectClass();
							IDOFactory home = (IDOFactory) IDOLookup.getHome(c);
							home.setDatasource(dataSource, false);
						}
						catch (ClassNotFoundException e)
						{
							System.out.println("Cant set the dataSource : Class " + e.getMessage() + " not found");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actionPerformed(ActionEvent event) {
		try{
			if (event.getActionCommand().equalsIgnoreCase(IW_CURRENCY_TIMER)) {
				CurrencyBusiness.getCurrencyMap(this.bundle_);
			}
		}
		catch (com.idega.data.IDONoDatastoreError error) {
			System.err.println("TradeBundleStarter.actionPerformed() Error: "+error.getMessage());
		}
		catch (RemoteException re) {
			System.err.println("TradeBundleStarter.actionPerformed() Error: "+re.getMessage());
		}
		catch (Exception re) {
			System.err.println("TradeBundleStarter.actionPerformed() Error: "+re.getMessage());
			re.printStackTrace();
		}
	}
	
	/**
	 * @see com.idega.idegaweb.IWBundleStartable#stop(IWBundle)
	 */
	public void stop(IWBundle starterBundle) {
		if (this.timer != null) {
			this.timer.stop();
			this.timer = null;
		}
	}
}
