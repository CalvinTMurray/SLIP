var running = false;
var sliderValue;
var moveSliderAlong;

$(document).ready(function handleClick() {
	
	$('#playButton').click(function() {
		if (!running) {
			startPlayback();
		} else {
			stopPlayback();
		}
	})
	
	$('#resetButton').click(function() {
		resetPlayback();
	})

})

function startPlayback() {
	
	document.getElementById("playButton").innerHTML="Stop";
	running = true;
	
	// Start playback from current slider position
	sliderValue = timeSlider.slider('getValue');
	
	moveSliderAlong = setInterval(function() {
		if(!shouldContinue()) {
			return;
		};

		timeSlider.slider('setValue', sliderValue, true);
		sliderValue++;
		
	}, 100);
}

function shouldContinue() {
	
	if (sliderValue < timeSlider.slider('getAttribute', 'max')) {
		return true;
	} else {
		stopPlayback();
		return false;
	}
}

function stopPlayback() {
	running = false;
	document.getElementById("playButton").innerHTML="Play";
	clearInterval(moveSliderAlong);
}

function resetPlayback() {
	stopPlayback();
	sliderValue = 0;
	timeSlider.slider('setValue', sliderValue, true);
}