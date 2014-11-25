function createDistanceChart() {
//	console.log(distancePoints);
	$('#distanceGraphContainer').highcharts('StockChart', {
		 chart: {
             zoomType: 'x'
         },

		rangeSelector : {
			buttons : [ {
				type : 'second',
				count : 30,
				text : '30s'
			},
			{
				type : 'minute',
				count : 1,
				text : '1m'
			}, 
			{
				type : 'minute',
				count : 5,
				text : '5m'
			},
			{
				type : 'all',
				text : 'All'
			} ],
			selected : 1
		},
		title : {
			text : 'Distance Travelled'
		},
		series : [ {
			name : 'Distance Travelled',
			data : distancePoints,
			type : 'spline',
			tooltip : {
				valueDecimals : 2
			}
		} ]

	});
}