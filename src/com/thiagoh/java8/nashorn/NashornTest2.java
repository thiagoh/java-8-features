package com.thiagoh.java8.nashorn;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

public class NashornTest2 {

	public static void main(String[] args) {

		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();

		int c = 0;
		while (c++ < 10) {
			load(contextClassLoader);
		}
	}

	private static void load(ClassLoader contextClassLoader) {

		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();

		ScriptEngine engine = scriptEngineManager.getEngineByName("nashorn");

		try {

			URL resourceUrl = contextClassLoader.getResource("/com/thiagoh/java8/nashorn/charts.js");

			URLConnection urlConnection = resourceUrl.openConnection();

			urlConnection.setUseCaches(false);

			InputStream inputStream = urlConnection.getInputStream();

			engine.eval(new InputStreamReader(inputStream));

		} catch (ScriptException | IOException e) {
			e.printStackTrace();
		}

		Invocable invocable = (Invocable) engine;

		try {

			long t1 = System.currentTimeMillis();
			Object result = invocable.invokeFunction("getChart");

			ScriptObjectMirror mirror = (ScriptObjectMirror) result;

			long t2 = System.currentTimeMillis();

			System.out.println(result.toString() + " in " + (t2 - t1) + "ms");

			System.out.println(Arrays.asList(mirror.getOwnKeys(true)).stream().reduce("", (acc, key) -> {

				return acc + key + ": " + mirror.get(key) + ",\n";

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
