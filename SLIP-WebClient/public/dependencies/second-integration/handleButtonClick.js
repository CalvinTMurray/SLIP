var i = 0;
$(document).ready(function() {
	$('#button').click(function() {
		setInterval(function() {
			var mySlider = $("#timeSlider").slider();
			mySlider.slider('setValue', i, true);
			i++;
		}, 250);

	})
})