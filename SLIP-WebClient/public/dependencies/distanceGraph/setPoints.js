var points = new Array();

function setDistancePoints() {
	$.getJSON("http://localhost:8080/position-data", {
		sessionID : sessionID,
		chartType : "DISTANCE"
	}, function(json) {
		createPointsDataArray(json);
	});
}


function createPointsDataArray(jsonPoints) {
	var i;
	for (i = 0; i < jsonPoints.length; i++) {
		var jsonPoint = jsonPoints[i];
		console.log(jsonPoint.timestamp / 1000);
		var dataPoint = [ Math.floor(jsonPoint.timestamp / 1000) * 1000, jsonPoint.distance ];
		points.push(dataPoint);
	}
	
}
