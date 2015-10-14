package com.thiagoh.java8.nashorn;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

public class NashornTest1 {

	public static void main(String[] args) {

		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();

		ScriptEngine engine = scriptEngineManager.getEngineByName("nashorn");

		String path1 = "bin/com/thiagoh/java8/nashorn/status.js";
		String path2 = "bin/com/thiagoh/java8/nashorn/charts.js";

		try {

			engine.eval(new FileReader(path1));
			engine.eval(new FileReader(path2));

			try (BufferedReader r = new BufferedReader(new FileReader(path1))) {
				// r.lines().forEach(System.out::println);
			}

		} catch (ScriptException | IOException e) {
			e.printStackTrace();
		}

		Invocable invocable = (Invocable) engine;

		Entidade entidade1 = EntidadeUtil.getObjetivo();

		try {

			long t1 = System.currentTimeMillis();
			Object result = invocable.invokeFunction("getStatus", entidade1);
			long t2 = System.currentTimeMillis();

			System.out.println(result.toString() + " in " + (t2 - t1) + "ms");

		} catch (NoSuchMethodException | ScriptException e) {
			e.printStackTrace();
		}

		{
			long t1 = System.currentTimeMillis();
			Object result = getStatus(entidade1);
			long t2 = System.currentTimeMillis();

			System.out.println(result.toString() + " in " + (t2 - t1) + "ms");
		}

		try {

			long t1 = System.currentTimeMillis();
			Object result = invocable.invokeFunction("getChart");

			ScriptObjectMirror mirror = (ScriptObjectMirror) result;

			long t2 = System.currentTimeMillis();

			System.out.println(result.toString() + " in " + (t2 - t1) + "ms");

			System.out.println(Arrays.asList(mirror.getOwnKeys(true)).stream().reduce("", (acc, key) -> {

				return acc + key + ": " + mirror.get(key) + ",\n" ;

			}) + " in " + (t2 - t1) + "ms");

		} catch (NoSuchMethodException | ScriptException e) {
			e.printStackTrace();
		}
	}

	private static boolean getStatus(Entidade entidade) {

		Entidade child = EntidadeUtil.getAcaoPrioritariaList().stream().filter((cur) -> {

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
