package com.idega.block.trade.data.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.idega.user.data.bean.Group;

@Entity
@Table(name = DebitCardInformation.ENTITY_NAME)
@NamedQueries({

		@NamedQuery(name = DebitCardInformation.findBySupplierManager, query = "select dci from DebitCardInformation dci left join dci.supplierManager where dci.supplierManager.groupID = :"
				+ DebitCardInformation.supplierProp),
		@NamedQuery(name = DebitCardInformation.findByMerchant, query = "select dci from DebitCardInformation dci where dci."
				+ DebitCardInformation.mpkProp + " = :" + DebitCardInformation.mpkProp + " and dci."
				+ DebitCardInformation.typeProp + " = :" + DebitCardInformation.typeProp),
		@NamedQuery(name = DebitCardInformation.findByPrimaryKey, query = "select dci from DebitCardInformation dci where dci."
				+ DebitCardInformation.idProp + " = :" + DebitCardInformation.idProp) })

public class DebitCardInformation {
	public static final String ENTITY_NAME = "DC_INFORMATION";
	private static final String COLUMN_TYPE = "DC_TYPE";
	private static final String COLUMN_MERCHANT_PK = "DC_MERCHANT_PK";
	private static final String COLUMN_SUPPLIER_MANAGER_ID = "SUPPLIER_MANAGER_ID";

	public static final String findBySupplierManager = "DebitCardInformation.findBySupplierManager";
	public static final String findByMerchant = "DebitCardInformation.findByMerchant";
	public static final String findByPrimaryKey = "DebitCardInformation.findByPrimaryKey";

	public static final String idProp = "id";
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = DebitCardInformation.ENTITY_NAME + "_ID")
	private Integer id;

	public static final String typeProp = "type";
	@Column(name = COLUMN_TYPE)
	private String type;

	public static final String mpkProp = "merchantPK";
	@Column(name = COLUMN_MERCHANT_PK)
	private String merchantPK;

	public static final String supplierProp = "supplierManager";
	@OneToOne
	@JoinColumn(name = COLUMN_SUPPLIER_MANAGER_ID)
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
