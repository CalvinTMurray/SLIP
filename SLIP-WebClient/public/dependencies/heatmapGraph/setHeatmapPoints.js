var heatmapPoints = new Array();

function setHeatmapPoints(sessionID) {
	$.getJSON("http://localhost:8080/position-data", {
		sessionID : sessionID,
		chartType : "HEATMAP"
	})
		.success (function(json) {
		heatmapPoints = json;
		createHeatmapChart();
		console.log(heatmapPoints);
	})
		.error (function() {
		heatmapPoints = [];
		createHeatmapChart();
		console.log(heatmapPoints);
	});
}


