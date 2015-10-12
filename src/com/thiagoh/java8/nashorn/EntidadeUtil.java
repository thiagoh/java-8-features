package com.thiagoh.java8.nashorn;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class EntidadeUtil {

	private static AtomicInteger atomicInteger = new AtomicInteger(1);

	private static Entidade objetivo;
	private static List<Entidade> acaoPrioritariaList;
	private static List<Entidade> acaoPrioritariaFilteredList;

	public static Entidade getObjetivo() {

		if (objetivo == null) {

			EntidadeImpl entidadeImpl = new EntidadeImpl("Objetivo 1", 100, 70);

			objetivo = entidadeImpl;
		}

		return objetivo;
	}

	public static List<Entidade> getAcaoPrioritariaList() {

		if (acaoPrioritariaList == null) {

			acaoPrioritariaList = new ArrayList<>();

			for (int i = 0; i < 1000; i++) {

				EntidadeImpl child = new EntidadeImpl("Acao " + atomicInteger.getAndIncrement(), 100,
						Math.random() * 100);

				child.addData(new ExecutionDataImpl(80 + (Math.random() * 20), Math.random() * 100));
				child.addData(new ExecutionDataImpl(80 + (Math.random() * 20), Math.random() * 100));
				child.addData(new ExecutionDataImpl(80 + (Math.random() * 20), Math.random() * 100));
				child.addData(new ExecutionDataImpl(80 + (Math.random() * 20), Math.random() * 100));
				child.addData(new ExecutionDataImpl(80 + (Math.random() * 20), Math.random() * 100));
				child.addData(new ExecutionDataImpl(80 + (Math.random() * 20), Math.random() * 100));
				child.addData(new ExecutionDataImpl(80 + (Math.random() * 20), Math.random() * 100));
				child.addData(new ExecutionDataImpl(80 + (Math.random() * 20), Math.random() * 100));
				child.addData(new ExecutionDataImpl(80 + (Math.random() * 20), Math.random() * 100));

				acaoPrioritariaList.add(child);
			}
		}

		return acaoPrioritariaList;
	}

	public static List<Entidade> getAcaoPrioritariaFilteredList() {

		if (acaoPrioritariaFilteredList == null) {

			acaoPrioritariaFilteredList = new ArrayList<>();

			for (int i = 0; i < 100; i++) {

				EntidadeImpl child = new EntidadeImpl("Acao " + atomicInteger.getAndIncrement(), 100,
						Math.random() * 100);

				child.addData(new ExecutionDataImpl(80 + (Math.random() * 20), Math.random() * 100));
				child.addData(new ExecutionDataImpl(80 + (Math.random() * 20), Math.random() * 100));
				child.addData(new ExecutionDataImpl(80 + (Math.random() * 20), Math.random() * 100));

				acaoPrioritariaFilteredList.add(child);
			}
		}

		return acaoPrioritariaFilteredList;
	}
}
