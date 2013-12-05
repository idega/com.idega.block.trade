package com.idega.block.trade.data;


import com.idega.core.file.data.ICFile;
import com.idega.data.GenericEntity;
import com.idega.util.StringUtil;

public class VoucherAdBMPBean extends GenericEntity implements VoucherAd {

	private static final long serialVersionUID = 5324196262827813226L;
	
	private static final String COLUMN_URL = "URL";
	private static final String COLUMN_POSITION = "POSITION";
	private static final String COLUMN_FILE_ID = "IC_FILE_ID";
	
	public String getUrl() {
		String url = getStringColumnValue(COLUMN_URL);
		if(!StringUtil.isEmpty(url)){
			return url;
		}
		Integer fileId = getFileId();
		if(fileId != null){
			return "/servlet/MediaServlet?media_id=" + fileId;
		}
		return null;
	}
	
	public void setUrl(String url){
		setStringColumn(COLUMN_FILE_ID, null);
		setStringColumn(COLUMN_URL, url);
	}

	public Integer getPosition() {
		return getIntegerColumnValue(COLUMN_POSITION);
	}
	
	public void setPostion(Integer position){
		setColumn(COLUMN_POSITION, position);
	}

	public String getEntityName() {
		return "tr_voucher_add";
	}

	public void initializeAttributes() {
		addAttribute(getIDColumnName());
		addAttribute(COLUMN_URL, "url", String.class);
		addAttribute(COLUMN_POSITION, "position", Integer.class);
//		addAttribute(COLUMN_FILE_ID,"file", ICFile.class);
		addOneToOneRelationship(COLUMN_FILE_ID, ICFile.class);
	}

	public Integer getFileId() {
		setUrl(null);
		return getIntegerColumnValue(COLUMN_FILE_ID);
	}
	
	public void setFileId(Integer fileId){
		setStringColumn(COLUMN_URL, null);
		setColumn(COLUMN_FILE_ID, fileId);
	}
}
