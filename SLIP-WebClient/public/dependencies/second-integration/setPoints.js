var points = [];
$(document).ready(function() {
	var i;
	for (i = 0; i < 1000; i++) {
		var r = 100;
		var x = r * Math.sin(i * Math.PI / 180) + 250;
		var y = r * Math.cos(i * Math.PI / 180) + 250;

		points[i] = {
			"timestamp" : i,
			"coords" : [ [ x, y ] ]
		};
	}
})