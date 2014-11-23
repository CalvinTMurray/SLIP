var points = new Array();
var timeSlider;

function setPoints(sessionID) {
	
	$.getJSON("http://localhost:8080/position-data", {
		sessionID : sessionID,
		chartType : "HIGHCHART_SCATTER_PLOT"
	}, function(json) {

		points = json;
		timeSlider = $("#timeSlider").slider({
			max : points.length - 1
		});

		console.log(points);

	});

};