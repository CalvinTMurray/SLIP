var running = false;
var sliderValue = 1;
var moveSliderAlong;

$(document).ready(function handleClick() {
	
	$('#button').click(function() {
		if (!running) {
			startPlayback();
		} else {
			stopPlayback();
		}
	})

})

function startPlayback() {
	document.getElementById("button").innerHTML="Stop";
	running = true;
	// Start playback from current slider position
	sliderValue = timeSlider.slider('getValue');
	moveSliderAlong = setInterval(function() {
		timeSlider.slider('setValue', sliderValue, true);
		sliderValue++;
		checkEndReached();
	}, 500);
}

function checkEndReached() {
	if (sliderValue > timeSlider.slider('getAttribute', 'max')) {
		stopPlayback();
	}
}

function stopPlayback() {
	document.getElementById("button").innerHTML="Play";
	running = false;
	clearInterval(moveSliderAlong);
}
