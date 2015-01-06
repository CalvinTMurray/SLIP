function createScatterChart() {
	$('#scatterGraphContainer').highcharts({
		chart : {
			width : 500,
			height : 500,
			animation: {
				duration: 10
			},
			type:'scatter',
			plotBackgroundColor: '#0096D1'
		},
		credits: {
			enabled: false
		},
		title : {
			text : 'Scatter'
		},
		exporting : {
			enabled : true
		},
		tooltip : {
			formatter : function() {
				return 'Position <br/>x:<b>' + this.x + '</b>' +
							'<br/>y:<b>' + this.y + '</b>'+'<br/>'+this.point.name;
				}
		},
		plotOptions: {
			series : {
				turboThreshold: 0
			}
        },
		series : [{
			name: 'Tracked Points',
			lineWidth:1,
			data : scatterPoints ,
			color: 'black'
			}],
		yAxis : {
			min : 0,
			max : 70,
			gridLineWidth : 1,
			title : {
				text: 'Y coordinate'
			},
			tickInterval: 10
		},
		xAxis : {
			min : 0,
			max : 70,
			gridLineWidth : 1,
			title : {
				text: 'X coordinate'
			},
			tickLength : 3,
			tickInterval: 10
		},
	});
}
