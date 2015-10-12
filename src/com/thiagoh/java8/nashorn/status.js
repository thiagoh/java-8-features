/**
 *
 */

load('/home/thiago/dev/java/workspace/java8-ex/src/com/thiagoh/java8/nashorn/underscore-min.js');
// /home/thiago/dev/java/workspace/java8-ex/src/com/thiagoh/java8/nashorn/status.js

var getStatus = function(entidade) {

	var child = _.find(entidade.getChildren(), function(curChild) {

		//print('previsto: ', curChild.previsto, ' realizado: ', curChild.realizado);

		return curChild.realizado < (curChild.previsto * 0.01);
	});

	print('Result child: ', child);

	if (typeof child === 'undefined') {
		return false;
	}

	return true;
};