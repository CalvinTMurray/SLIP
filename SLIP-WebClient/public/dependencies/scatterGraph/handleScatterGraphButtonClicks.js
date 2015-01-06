$(document).ready(function handleClick() {

	$('#markerButton').click(function() {
		changeState("markerButton");
	});

	$('#lineButton').click(function() {
		changeState("lineButton");
	})

	function changeState(buttonID) {
		var button = document.getElementById(buttonID);
    	var nextState = button.innerText;

    	if (buttonID == "markerButton") {
    		setState(button,buttonID,nextState,"Markers");
    	}

    	if (buttonID == "lineButton") {
    		setState(button,buttonID,nextState,"Line");
    	}

	}

	function setState(button,buttonID,nextState,buttonSpecificText) {
		if (nextState == ("Hide "+buttonSpecificText)) {
       		button.innerHTML='<span aria-hidden="true"></span>Show '+buttonSpecificText;
       		setOtherButtonDisabledTo(buttonID,true); //Disable other button
			hideGraphEntity(buttonID);
  		}

       	if (nextState == ("Show "+buttonSpecificText)) {
       		button.innerHTML='<span aria-hidden="true"></span>Hide '+buttonSpecificText;
       		setOtherButtonDisabledTo(buttonID,false); //Enable other button
       		showGraphEntity(buttonID);
   		}
	}

	function setOtherButtonDisabledTo(buttonID,enabled) {
		if (buttonID == "markerButton") {
			$('#lineButton').prop('disabled', enabled);
		}

		if (buttonID == "lineButton") {
			$('#markerButton').prop('disabled', enabled);
        }
	}

	function hideGraphEntity(buttonID) {
		if (buttonID == "markerButton") {
			updateGraph("Markers","Hide");
		} else {
			updateGraph("Line","Hide")
		}
	}

	function showGraphEntity(buttonID) {
		if (buttonID == "markerButton") {
   			updateGraph("Markers","Show");
   		} else {
   			updateGraph("Line","Show")
   		}
    }

    function updateGraph(entity,visibility) {
		var chart = $('#scatterGraphContainer').highcharts();

    	if (entity == "Markers" && visibility == "Show") {
    		setMarkersEnabled(chart,true);
    	}

    	if (entity == "Markers" && visibility == "Hide") {
    		setMarkersEnabled(chart,false);
        }

        if (entity == "Line" && visibility == "Show") {
        	setLineEnabled(chart,1);
        }

        if (entity == "Line" && visibility == "Hide") {
        	setLineEnabled(chart,0);
        }
    }

    function setMarkersEnabled(chart,boolean) {
    	chart.series[0].update({
        	marker: {
        		enabled: boolean
            }
		});
    }

     function setLineEnabled(chart,width) {
     	chart.series[0].update({
        	lineWidth: width
    	});
     }

});

function resetScatterGraphButtons() {
	$('#markerButton').prop('disabled', false);
    var markerButton = document.getElementById("markerButton");
  	markerButton.innerHTML='<span aria-hidden="true"></span>Hide Markers';

	$('#lineButton').prop('disabled', false);
	var lineButton = document.getElementById("lineButton");
	lineButton.innerHTML='<span aria-hidden="true"></span>Hide Line';
}
