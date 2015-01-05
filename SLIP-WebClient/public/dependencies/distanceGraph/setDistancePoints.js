var distancePoints = new Array();

function setDistancePoints(sessionID) {
	$.getJSON("http://192.168.0.15:8080/position-data", {
		sessionID : sessionID,
		chartType : "DISTANCE"
	})
		.success (function(json) {
		createPointsDataArray(json);
		createDistanceChart();
	})
		.error (function() {
		distancePoints = [];
		createDistanceChart();
	});
}


function createPointsDataArray(jsonPoints) {
	var tempPoints = [];
	var i;
	for (i = 0; i < jsonPoints.length; i++) {
		var jsonPoint = jsonPoints[i];
		var dataPoint = [ Math.floor(jsonPoint.timestamp / 1000) * 1000, jsonPoint.distance ];
		tempPoints.push(dataPoint);
	}

	distancePoints = tempPoints;

	console.log(distancePoints);
}
