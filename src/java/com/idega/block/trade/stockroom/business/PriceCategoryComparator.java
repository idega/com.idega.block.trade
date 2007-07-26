package com.idega.block.trade.stockroom.business;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import com.idega.block.trade.stockroom.data.PriceCategory;
import com.idega.util.IsCollator;

public class PriceCategoryComparator implements Comparator {

	public static final int NAME = 2;
	public static final int ORDER = 1;


	private int sortBy;

	public PriceCategoryComparator() {
		this.sortBy = ORDER;
	}

	public PriceCategoryComparator(int toSortBy) {
		this.sortBy = toSortBy;
	}

	public void sortBy(int toSortBy) {
		this.sortBy = toSortBy;
	}

	public int compare(Object o1, Object o2) {
		int result = 0;

		switch (this.sortBy) {
		case NAME     : result = nameSort(o1, o2);
		break;
		case ORDER   : result = orderSort(o1,o2);
		break;
		}

		return result;
	}

	private int nameSort(Object o1, Object o2) {
		PriceCategory p1 = (PriceCategory) o1;
		PriceCategory p2 = (PriceCategory) o2;

		String one = p1.getName()!=null?p1.getName():"";
		String two = p2.getName()!=null?p2.getName():"";

		return IsCollator.getIsCollator().compare(one,two);
	}

	private int orderSort(Object o1, Object o2) {
		PriceCategory p1 = (PriceCategory) o1;
		PriceCategory p2 = (PriceCategory) o2;

		if (p1.getOrderNumber() > p2.getOrderNumber()) {
			return 1;
		}else if (p2.getOrderNumber() > p1.getOrderNumber()) {
			return -1;
		}else {
			return 0;
		}
	}


	public Vector sortedArray(Vector list) {
		Collections.sort(list, this);
		return list;
	}




}
