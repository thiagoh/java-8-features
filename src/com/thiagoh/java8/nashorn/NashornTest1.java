package com.thiagoh.java8.nashorn;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class NashornTest1 {

	public static void main(String[] args) {

		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();

		ScriptEngine engine = scriptEngineManager.getEngineByName("nashorn");

		String path = "/home/thiago/dev/java/workspace/java8-ex/src/com/thiagoh/java8/nashorn/status.js";

		try {

			engine.eval(new FileReader(path));

			try (BufferedReader r = new BufferedReader(new FileReader(path))) {
				// r.lines().forEach(System.out::println);
			}

		} catch (ScriptException | IOException e) {
			e.printStackTrace();
		}

		Invocable invocable = (Invocable) engine;

		EntidadeImpl entidade1 = new EntidadeImpl("Objetivo 1", 100, 70);

		for (int i = 0; i < 1000; i++) {
			entidade1.add(new EntidadeImpl("Acao " + i, 100, Math.random() * 100));
		}

		try {

			long t1 = System.currentTimeMillis();
			Object result = invocable.invokeFunction("getStatus", entidade1);
			long t2 = System.currentTimeMillis();

			System.out.println(result.toString() + " in " + (t2 - t1) + "ms");

		} catch (NoSuchMethodException | ScriptException e) {
			e.printStackTrace();
		}

		long t1 = System.currentTimeMillis();
		Object result = getStatus(entidade1);
		long t2 = System.currentTimeMillis();

		System.out.println(result.toString() + " in " + (t2 - t1) + "ms");
	}

	private static boolean getStatus(Entidade entidade) {

		Entidade child = entidade.getChildren().stream().filter((cur) -> {

			// System.out.println("previsto: " + cur.getPrevisto() +
			// " realizado: " + cur.getRealizado());

				return cur.getRealizado() < cur.getPrevisto() * 0.01;
				
			}).findFirst().get();

		System.out.println("Result child: " + child);

		if (child == null) {
			return false;
		}

		return true;
	}
}
