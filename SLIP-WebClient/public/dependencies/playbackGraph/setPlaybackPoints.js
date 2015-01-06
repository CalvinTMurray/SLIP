var points = new Array();
$(document).ready(function() {
	var timeSlider = $("#timeSlider").slider({
		max : -1
	});
});

function setPoints(sessionID) {

	$.getJSON("http://localhost:8080/position-data", {
		sessionID : sessionID,
		chartType : "HIGHCHART_SCATTER_PLOT"
	})
		.success(function(json) {

			points = json;
			setTimesliderMax();
			resetPlayback(); // Reset playback just in case we are playing data from another session
			console.log(points);

		})
		.error(function (response) {

			points = [{"position":[0,0]}];
			setTimesliderMax();
			resetPlayback(); // Reset playback just in case we are playing data from another session
			console.log(points);

		})


}

function setTimesliderMax() {
	timeSlider = $("#timeSlider").slider({
		max : points.length - 1
	});
}