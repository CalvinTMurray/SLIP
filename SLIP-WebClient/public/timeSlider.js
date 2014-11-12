function createTimeSlider() {

	$("#timeSlider").slider();
	$("#timeSlider").on('slide', function(slideEvt) {
		$("#timeSliderValue").text(slideEvt.value);
		
		var chart = $('#container').highcharts();
		chart.series[0].setData(points[slideEvt.value].coords);
	});
}