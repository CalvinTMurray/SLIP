$(document).ready(
		$(function() {
			$('#playbackGraphContainer').highcharts(
					{
						chart : {
							width : 455,
							height : 500,
							animation: {
				                duration: 10
				            },
				            plotBackgroundImage : '../assets/images/blueBackground.svg' 
					
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
							data : [ [ 0, 0 ] ] ,
							color: 'white'
						} ],
						yAxis : {
							min : 0,
							max : 500,
							gridLineWidth : 1,
							title : {
								text: 'Y coordinate'
							},
							tickInterval: 50
						},
						xAxis : {
							min : 0,
							max : 500,
							gridLineWidth : 1,
							title : {
								text: 'X coordinate'
							},
							tickLength : 3,
							tickInterval: 50
						},
						plotOptions : {
							series : {
								animation : false
							}
						}
					})
		}))
