$(document).ready(function() {
	$("#timeSlider").slider();
	$("#timeSlider").on('slide', function(slideEvt) {
		$("#timeSliderValue").text(slideEvt.value);

		var chart = $('#container').highcharts();
		chart.series[0].data[0].update(points[slideEvt.value].position);
		console.log(points[slideEvt.value].position);
	});
})