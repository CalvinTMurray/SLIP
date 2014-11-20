var points;
$(document).ready(function() {
	$.getJSON("http://172.20.132.175:8080/position-data", {
		sessionID : 26,
		chartType : "HIGHCHART_SCATTER_PLOT"
	}, function(json) {
		console.log(json);
		points = json;
	});
})