/**
 *
 */

var EntidadeUtil = Java.type('com.thiagoh.java8.nashorn.EntidadeUtil');

print(EntidadeUtil.class);
print(EntidadeUtil.class.static);

load('bin/com/thiagoh/java8/nashorn/underscore-min.js');
// /home/thiago/dev/java/workspace/java8-ex/src/com/thiagoh/java8/nashorn/status.js

var getStatus = function(entidade) {

	var child = _.find(EntidadeUtil.getAcaoPrioritariaList(), function(curChild) {

		_.map(curChild.getDataEntries(), function(dataEntry) {
			print('expected: ', dataEntry.expected, ' accomplished: ', dataEntry.accomplished);
		});

		print('previsto: ', curChild.previsto, ' realizado: ', curChild.realizado);

		return curChild.realizado < (curChild.previsto * 0.01);
	});

	print('Result child: ', child);

	if (typeof child === 'undefined') {
		return false;
	}

	return true;
};