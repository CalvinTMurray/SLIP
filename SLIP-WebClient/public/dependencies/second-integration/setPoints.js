var points = new Array();
var timeSlider;
$(document).ready(function() {
	$.getJSON("http://172.20.128.98:8080/position-data", {
		sessionID : 29,
		chartType : "HIGHCHART_SCATTER_PLOT"
	}, function(json) {

		points = json;
		timeSlider = $("#timeSlider").slider({
			max : points.length - 1
		});

		console.log(points);

	});

})