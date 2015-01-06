var scatterPoints = new Array();

function setScatterPoints(sessionID) {
	$.getJSON("http://localhost:8080/position-data", {
		sessionID : sessionID,
		chartType : "HIGHCHART_SCATTER_PLOT"
	})
		.success(function(json) {
			createScatterPointsDataArray(getNonNullJson(json));
			createScatterChart();
			resetScatterGraphButtons();
			console.log(scatterPoints);
		})
		.error(function (response) {
			scatterPoints = [];
			createScatterChart();
			resetScatterGraphButtons();
			console.log(scatterPoints);
		})
}

function getNonNullJson(receivedJsonPoints) {
	nonNullJson = [];
	var i;

	for (i = 0; i < receivedJsonPoints.length ; i++) {
		if (receivedJsonPoints[i].position !== null) {
			var position = new Object();
			position.x = receivedJsonPoints[i].position[0];
			position.y = receivedJsonPoints[i].position[1];
			nonNullJson.push(position);
		}
	}

	return nonNullJson;
}

function createScatterPointsDataArray(jsonPoints) {
	var tempPoints = [];
	var N = jsonPoints.length;

	if (N == 1) {
		setFirstPoint(tempPoints,jsonPoints);
	}
	else if (N == 2) {
		setFirstPoint(tempPoints,jsonPoints);
		setLastPoint(tempPoints,jsonPoints);
	}
	else {
		setFirstPoint(tempPoints,jsonPoints);
		setIntermediatePoints(tempPoints,jsonPoints)
        setLastPoint(tempPoints,jsonPoints);
	}

	scatterPoints = tempPoints;
}

function setFirstPoint(tempPoints,jsonPoints) {
	var dataPoint = new Object();
	dataPoint.name = "Starting Point";
	dataPoint.color = 'green';
	dataPoint.marker = new Object();
	dataPoint.marker.radius = 6;
	setXAndYCoords(dataPoint,jsonPoints[0]);
	tempPoints.push(dataPoint);
}

function setIntermediatePoints(tempPoints,jsonPoints) {
	var i;
	for(i = 1; i < jsonPoints.length-1;i++) {
	var dataPoint = new Object();
		dataPoint.name = "";
    	setXAndYCoords(dataPoint,jsonPoints[i]);
    	tempPoints.push(dataPoint);
	}
}

function setLastPoint(tempPoints,jsonPoints) {
	var dataPoint = new Object();
	var lastElementIndex = jsonPoints.length - 1;
	dataPoint.name = "Finishing Point";
	dataPoint.color = 'red';
	dataPoint.marker = new Object();
	dataPoint.marker.radius = 6;
	setXAndYCoords(dataPoint,jsonPoints[lastElementIndex]);
	tempPoints.push(dataPoint);
}

function setXAndYCoords(dataPoint,jsonPoint) {
	dataPoint.x = jsonPoint.x;
    dataPoint.y = jsonPoint.y;
}

