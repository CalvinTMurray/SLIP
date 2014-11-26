var distancePoints = new Array();

function setDistancePoints(sessionID) {
	$.getJSON("http://localhost:8080/position-data", {
		sessionID : sessionID,
		chartType : "DISTANCE"
	}, function(json) {
		createPointsDataArray(json);
		createDistanceChart();
	});
}


function createPointsDataArray(jsonPoints) {
	var i;
	for (i = 0; i < jsonPoints.length; i++) {
		var jsonPoint = jsonPoints[i];
		var dataPoint = [ Math.floor(jsonPoint.timestamp / 1000) * 1000, jsonPoint.distance ];
		distancePoints.push(dataPoint);
	}
	
	console.log(distancePoints);
}
