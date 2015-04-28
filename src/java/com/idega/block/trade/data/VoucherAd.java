package com.idega.block.trade.data;

import com.idega.data.IDOEntity;

public interface VoucherAd extends IDOEntity{
	public String getUrl();
	public void setUrl(String url);
	public Integer getPosition();
	public void setPostion(Integer position);
	public Integer getFileId();
	public void setFileId(Integer fileId);
}
