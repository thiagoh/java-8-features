package com.thiagoh.java8.nashorn;

import java.util.ArrayList;
import java.util.List;

public class EntidadeImpl implements Entidade {

	private String name;
	private double previsto;
	private double realizado;
	private List<ExecutionData> dataEntries;

	public EntidadeImpl(String name, double previsto, double realizado) {
		this.name = name;
		this.previsto = previsto;
		this.realizado = realizado;
		this.dataEntries = new ArrayList<>();
	}

	@Override
	public List<ExecutionData> getDataEntries() {
		return dataEntries;
	}

	public void addData(ExecutionData data) {
		this.dataEntries.add(data);
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public double getPrevisto() {
		return previsto;
	}

	public void setPrevisto(double previsto) {
		this.previsto = previsto;
	}

	@Override
	public double getRealizado() {
		return realizado;
	}

	public void setRealizado(double realizado) {
		this.realizado = realizado;
	}

	@Override
	public String toString() {
		return "EntidadeImpl [name=" + name + ", previsto=" + previsto + ", realizado=" + realizado + "]";
	}

}