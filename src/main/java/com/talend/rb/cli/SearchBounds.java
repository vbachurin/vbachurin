package com.talend.rb.cli;

public class SearchBounds {

	public SearchBounds(int from, int to) {
		this.from = from;
		this.to = to;
		System.out.println(this);
	}

	@Override
	public String toString() {
		return "Processing records between timestamps [from=" + from + ", to=" + to + "]";
	}

	private int from;
	private int to;

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

}
