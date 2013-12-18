package com.idega.block.trade.data.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name = Currency.TABLE_NAME)
@NamedQueries(
	{
		@NamedQuery(
				name = Currency.QUERY_GET_BY_ID,
				query = "FROM Currency c WHERE (c.id = :"+Currency.idProp+")"
		)
	}
)
public class Currency implements Serializable{
	private static final long serialVersionUID = -4640301270632728010L;
	public static final String TABLE_NAME="TR_CURRENCY";
	public static final String COLUMN_CURRENCY_NAME="CURRENCY_NAME";
	public static final String COLUMN_CURRENCY_ABBREVIATION="CURRENCY_ABBREVIATION";
	
	public static final String QUERY_GET_BY_ID = "currency.getById";
	public static final String idProp = TABLE_NAME + "_" + "id";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;
    
    public static final String nameProp = TABLE_NAME + "_" + COLUMN_CURRENCY_NAME;
    @Column(name = COLUMN_CURRENCY_NAME)
    private String name;
    
    public static final String abbreviationProp = TABLE_NAME + "_" + COLUMN_CURRENCY_ABBREVIATION;
    @Column(name = COLUMN_CURRENCY_ABBREVIATION)
    private String currencyAbbreviation;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCurrencyAbbreviation() {
		return currencyAbbreviation;
	}
	public void setCurrencyAbbreviation(String currencyAbbreviation) {
		this.currencyAbbreviation = currencyAbbreviation;
	}

}
