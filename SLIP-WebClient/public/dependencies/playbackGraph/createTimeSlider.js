$(document).ready(function() {

	var chart = $('#playbackGraphContainer').highcharts();
	var questionMark = chart.renderer.image('../assets/images/questionMark.svg', 180, 150, 100, 100);
	
	$("#timeSlider").on('slide', function(slideEvt) {
		
		$("#timeSliderValue").text(formatHHMMSS(slideEvt.value));
		
		if (points[slideEvt.value].position !== null) {
			$(questionMark.element).remove();
			chart.series[0].data[0].update(points[slideEvt.value].position);
			chart.series[0].show();
		} else {
			chart.series[0].hide();	
			questionMark.attr({
				zIndex: 2
			}).add();
		}
		
	});

})

function formatHHMMSS(numSeconds) {

	var sec_num = parseInt(numSeconds, 10);
	var hours = Math.floor(sec_num / 3600);
	var minutes = Math.floor((sec_num - (hours * 3600)) / 60);
	var seconds = sec_num - (hours * 3600) - (minutes * 60);

	if (hours < 10) {
		hours = "0" + hours;
	}

	if (minutes < 10) {
		minutes = "0" + minutes;
	}

	if (seconds < 10) {
		seconds = "0" + seconds;
	}

	var time = hours + ':' + minutes + ':' + seconds;
	return time;
}
