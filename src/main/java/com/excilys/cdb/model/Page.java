package main.java.com.excilys.cdb.model;

import java.util.ArrayList;
import java.util.List;

public class Page<E> {
	private int limit = 10;
	private int index = 1;
	private int indexLow;
	private int indexHigh;
	private List<E> content;
	private String search = "";
	
	public Page() {
		content = new ArrayList<>();
	}

	public Page(int index, int limit) {
		this.limit = limit;
		this.index = index;
		content = new ArrayList<>();
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIndexLow() {
		return indexLow;
	}

	public void setIndexLow(int indexLow) {
		this.indexLow = indexLow;
	}

	public int getIndexHigh() {
		return indexHigh;
	}

	public void setIndexHigh(int indexHigh) {
		this.indexHigh = indexHigh;
	}

	public List<E> getContent() {
		return content;
	}

	public void setContent(List<E> content) {
		this.content = content;
	}
	
	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
	
	public int valueOfIndexLow(int indexMax) {
		int indexLow = 1;
		if(index >= 3 && index <= indexMax - 2) {
			indexLow = index - 2;
		} else if(index < 3) {
			indexLow = 1;
		} else if(index > indexMax -2) {
			indexLow = indexMax - 4;
		}
		 return indexLow;
	}
	
	public int valueOfIndexHigh(int indexMax) {
		int indexHigh = 5;
		if(index >= 3 && index <= indexMax - 2){
			indexHigh = index + 2;
		} else if(index < 3) {
			indexHigh = 5;
		} else if(index > indexMax - 2) {
			indexHigh = indexMax;
		}
		 return indexHigh;
	}

}
