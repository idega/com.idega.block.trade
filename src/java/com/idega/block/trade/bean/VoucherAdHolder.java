package com.idega.block.trade.bean;

import java.util.Collection;

import com.idega.block.trade.data.VoucherAd;

public interface VoucherAdHolder {
	public Collection getVoucherAds();
	public void addVoucherAd(VoucherAd voucherAd);
	public void remove(VoucherAd voucherAd);
	public void store();
}
