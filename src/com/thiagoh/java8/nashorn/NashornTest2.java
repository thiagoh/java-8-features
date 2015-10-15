package com.thiagoh.java8.nashorn;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class NashornTest2 {

	private static String _PATH = "com/thiagoh/java8/nashorn/value.js";

	public static void main(String[] args) {

		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();

		int c = 0;

		while (c++ < 10) {

			load(contextClassLoader);

			try {

				Thread.sleep(1000);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			URL resourceUrl = contextClassLoader.getResource(_PATH);

			try {

				File file = new File(resourceUrl.toURI());

				try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");) {

					String content = "var value = " + c + ";\n" + "var f = function() {\n" + "	return \"foo bar\";\n"
							+ "};";

					randomAccessFile.seek(0);
					randomAccessFile.writeBytes(content);
					randomAccessFile.setLength(content.length());
				}

			} catch (URISyntaxException | IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void load(ClassLoader contextClassLoader) {

		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();

		ScriptEngine engine = scriptEngineManager.getEngineByName("nashorn");

		Bindings bindings = engine.getBindings(ScriptContext.ENGINE_SCOPE);

		try {

			URL resourceUrl = contextClassLoader.getResource(_PATH);

			URLConnection urlConnection = resourceUrl.openConnection();

			urlConnection.setUseCaches(false);

			StringBuilder sb = new StringBuilder();

			InputStream inputStream = urlConnection.getInputStream();

			try (BufferedReader buffered = new BufferedReader(new InputStreamReader(inputStream));) {

				String line = null;

				while ((line = buffered.readLine()) != null) {
					sb.append(line).append("\n");
				}
			}

			// System.out.println("File content: " + sb.toString());
			engine.eval(new StringReader(sb.toString()), bindings);

		} catch (ScriptException | IOException e) {
			e.printStackTrace();
		}

		long t1 = System.currentTimeMillis();

		int resultValue = ((Integer) bindings.getOrDefault("value", 0)).intValue();

		bindings.put("value", resultValue + 1);

		long t2 = System.currentTimeMillis();

		System.out.println("Value is: " + resultValue + " in " + (t2 - t1) + "ms");
	}

}
