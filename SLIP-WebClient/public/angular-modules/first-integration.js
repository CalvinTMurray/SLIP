angular.module('firstIntegrationModule', []).
controller('Data', function($scope, $http, $timeout) {
	
	var allData = [];
	var lastTimestamp = 0;

	// Function to get the data
	$scope.getData = function() {
		$http.get('http://localhost:8080/payloads' + '?session-id=24&timestamp=' + lastTimestamp)
		.success(function(data, status, headers, config) {

			allData = allData.concat(data);	
			$scope.payloads = allData;

			var totalData = data.length;
			lastTimestamp = data[totalData - 1].timestamp;
			
			
			$("html, body").animate({ scrollTop: $(document).height() }, "slow");
			
			console.log('Fetched data!');
		});
	}; 

	// Function to replicate setInterval using $timeout service.
	$scope.intervalFunction = function(){
		$timeout(function() {
			$scope.getData();
			$scope.intervalFunction();
		}, 1000)
	};
	
	$scope.intervalFunction();
	
});