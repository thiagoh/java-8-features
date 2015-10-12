package com.thiagoh.java8.nashorn;

public class ExecutionDataImpl implements ExecutionData {

	private double expected;
	private double accomplished;

	public ExecutionDataImpl(double expected, double accomplished) {
		this.expected = expected;
		this.accomplished = accomplished;
	}

	public double getExpected() {
		return expected;
	}

	public void setExpected(double expected) {
		this.expected = expected;
	}

	public double getAccomplished() {
		return accomplished;
	}

	public void setAccomplished(double accomplished) {
		this.accomplished = accomplished;
	}
	
}
