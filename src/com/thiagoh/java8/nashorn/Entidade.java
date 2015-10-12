package com.thiagoh.java8.nashorn;

import java.util.List;

public interface Entidade {

	public String getName();

	public double getPrevisto();

	public double getRealizado();

	public List<ExecutionData> getDataEntries();
}
