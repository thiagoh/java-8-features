package com.thiagoh.java8.nashorn;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

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

					randomAccessFile.seek(0);
					randomAccessFile.writeUTF("var value = " + c + ";");
				}

			} catch (URISyntaxException | IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void load(ClassLoader contextClassLoader) {

		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();

		ScriptEngine engine = scriptEngineManager.getEngineByName("nashorn");

		try {

			URL resourceUrl = contextClassLoader.getResource(_PATH);

			URLConnection urlConnection = resourceUrl.openConnection();

			urlConnection.setUseCaches(false);

			InputStream inputStream = urlConnection.getInputStream();

			engine.eval(new InputStreamReader(inputStream));

		} catch (ScriptException | IOException e) {
			e.printStackTrace();
		}

		long t1 = System.currentTimeMillis();
		Object result = engine.get("value");
		long t2 = System.currentTimeMillis();

		System.out.println(result.toString() + " in " + (t2 - t1) + "ms");
	}

}
