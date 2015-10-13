#!/usr/bin/env jjs

print("Arguments: " + arguments);

var CollectionsAndFiles = new JavaImporter(java.io, java.lang, java.util);

with(CollectionsAndFiles) {

	var filename = arguments[0] || 'test.out';

	var dir = new File("output/");
	if (!dir.exists()) {
		dir.mkdirs();
	}

	var filepath = dir.getAbsolutePath() + '/' + filename;
	var fw = new FileWriter(filepath);

	for (var i = 0; i < 100; i++) {

		fw.write(Math.random().toString());
	}

	fw.flush();
	fw.close();

	print("File '" + filepath + "' was written successfully");
}