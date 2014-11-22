$(document).ready(
		$(function() {
			$('#container').highcharts(
					{
						chart : {
							width : 455,
							height : 500,
							animation: {
				                duration: 10
				            },
							plotBackgroundImage : '../assets/images/court.svg'
						},
						credits: {
				            enabled: false
				        },
						title : {
							text : ''
						},
						tooltip : {
							formatter : function() {
								return 'Position <br/> x:<b>' + this.x
										+ '</b> <br/> y:<b>' + this.y + '</b>';
							}
						},
						series : [ {
							name: 'Object',
							data : [ [ 0, 0 ] ]
						} ],
						yAxis : {
							min : 0,
							max : 500,
							gridLineWidth : 0,
							title : {
								enabled : false
							},
							labels : {
								enabled : false
							},
							tickLength : 0
						},
						xAxis : {
							min : 0,
							max : 455,
							labels : {
								enabled : false
							},
							tickLength : 0,
							lineWidth : 0

						},
						plotOptions : {
							series : {
								animation : false
							}
						}
					})
		}))
