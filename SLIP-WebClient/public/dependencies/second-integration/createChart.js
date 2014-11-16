$(document).ready($(function() {
	$('#container').highcharts({
		chart : {
			width : 455,
			height : 500,
			plotBackgroundImage : '../assets/images/court.svg'
		},
		series : [ {
			data : []
		} ],
		yAxis : {
			min : 0,
			max : 500,
			gridLineWidth : 0
		},
		xAxis : {
			min : 0,
			max : 455
		}
	})
}))
