var i = 1;
$(document).ready(function() {
	$('#button').click(function() {
		setInterval(function() {
			var mySlider = $("#timeSlider").slider();
			mySlider.slider('setValue', i, true);
			i++;
		}, 100);

	})
})