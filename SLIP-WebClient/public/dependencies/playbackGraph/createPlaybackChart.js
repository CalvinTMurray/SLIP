$(document).ready(

	$(function() {
		$('#playbackGraphContainer').highcharts(
			{
				chart : {
					width : 500,
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
				exporting : {
					enabled : false
				},
				tooltip : {
					formatter : function() {
						return 'Position <br/>x:<b>' + this.x + '</b>' +
							'<br/>y:<b>' + this.y + '</b>';
					}
				},
				series : [ {
					name: 'Object',
					data : [ [ 0, 0 ] ] ,
					color: 'black'
				} ],
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
				plotOptions : {
					series : {
						animation : false ,
						marker: {
							radius: 8
						}
					}
				}
			})
	}));
