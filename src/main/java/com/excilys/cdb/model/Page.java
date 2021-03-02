package main.java.com.excilys.cdb.model;

import java.util.ArrayList;
import java.util.List;

public class Page<E> {
	private int limit;
	private int index;
	private List<E> content;

	public Page(int index, int limit) {
		this.limit = limit;
		this.index = index;
		content = new ArrayList<>();
	}

	public List<E> getContent() {
		return content;
	}

	public void setContent(List<E> content) {
		this.content = content;
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

}
