var module = angular.module('secondIntegrationModule', [])

module.controller('ChartController', function($scope, $http, $timeout) {

	$scope.addPoints = function () {
		var i;
		$scope.points = [];
		for (i = 0 ; i < 1000 ; i++) {
			var r = 100;
			var x = r*Math.sin(i*Math.PI/180)+250;
			var y = r*Math.cos(i*Math.PI/180)+250;

			$scope.points[i] = {
				"timestamp" : i,
				"coords" : [ [x, y] ]
			};

		}
		console.log($scope.points);
	};


	$scope.addHighChart = function() {
		$('#container').highcharts({
			chart : {
				width : 500,
				height : 500
			},
			series : [ {
				data : []
			} ],
			yAxis : {
				min : 0,
				max : 500
			},
			xAxis : {
				min : 0,
				max : 500
			}
		});

	};

	//----------------------------------------------------------

	$("#timeSlider").slider();
	$("#timeSlider").on('slide', function(slideEvt) {
		$("#timeSliderValue").text(slideEvt.value);

		var chart = $('#container').highcharts();
		chart.series[0].setData(points[slideEvt.value].coords);
	});

	//----------------------------------------------------

	var i = 0;
	$('#button').click(function() {

		setInterval(function() {
			var mySlider = $("#timeSlider").slider();
			mySlider.slider('setValue', i, true);
			i++;
		}, 250);

	})

});