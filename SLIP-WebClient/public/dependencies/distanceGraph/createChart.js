$(document).ready($(function() {
	console.log(points);
	$('#distanceGraphContainer').highcharts('StockChart', {
		 chart: {
             zoomType: 'x'
         },

		rangeSelector : {
			buttons : [ {
				type : 'second',
				count : 120,
				text : '120s'
			}, {
				type : 'minute',
				count : 5,
				text : '5m'
			}, {
				type : 'all',
				text : 'All'
			} ],
			selected : 0
		},
		title : {
			text : 'Distance Travelled'
		},
		series : [ {
			name : 'Distance Travelled',
			data : points,
			type : 'spline',
			tooltip : {
				valueDecimals : 2
			}
		} ]

	});
}))