package com.idega.block.trade.data.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.idega.user.data.bean.Group;

@Entity
@Table(name = CreditCardInformation.ENTITY_NAME)
@NamedQueries({
	@NamedQuery(name = CreditCardInformation.findBySupplierManager, query = "select u from CreditCardInformation u where u.supplierManager = :supplierManager" ),
	@NamedQuery(name = CreditCardInformation.findByMerchant, query = "select cci from CreditCardInformation cci where cci." + CreditCardInformation.mpkProp + " = :" + CreditCardInformation.mpkProp + " and cci." + CreditCardInformation.typeProp + " = :" + CreditCardInformation.typeProp),
	@NamedQuery(name = CreditCardInformation.findByPrimaryKey, query = "select cci from CreditCardInformation cci where cci." + CreditCardInformation.idProp + " = :" + CreditCardInformation.idProp )
})
public class CreditCardInformation {
	public static final String ENTITY_NAME = "CC_INFORMATION";
	private static final String COLUMN_TYPE = "CC_TYPE";
	private static final String COLUMN_MERCHANT_PK = "CC_MERCHANT_PK";
	private static final String COLUMN_SUPPLIER_MANAGER_ID = "SUPPLIER_MANAGER_ID";

	public static final String findBySupplierManager = "CreditCardInformation.findBySupplierManager";
	public static final String findByMerchant = "CreditCardInformation.findByMerchant";
	public static final String findByPrimaryKey = "CreditCardInformation.findByPrimaryKey";
	
	public static final String idProp = "id";
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = CreditCardInformation.ENTITY_NAME+"_ID")
	private Integer id;
	
	public static final String typeProp = "type";
	@Column(name = COLUMN_TYPE)
	private String type;
	
	public static final String mpkProp = "merchantPK";
	@Column(name = COLUMN_MERCHANT_PK)
	private String merchantPK;

	public static final String supplierProp = "supplierManager";
	@Column(name = COLUMN_SUPPLIER_MANAGER_ID)
	private Group supplierManager;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMerchantPK() {
		return merchantPK;
	}

	public void setMerchantPK(String merchantPK) {
		this.merchantPK = merchantPK;
	}

	public Group getSupplierManager() {
		return supplierManager;
	}

	public void setSupplierManager(Group supplierManager) {
		this.supplierManager = supplierManager;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
