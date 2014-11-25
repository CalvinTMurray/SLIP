angular.module('mtsModule', [])

.controller('SessionController', ['$http', '$scope', function($http, $scope) {
	
	$http.get('http://localhost:8080/all-sessions').
		success(function(data, status, headers, config) {
			$scope.sessionIDs = data;
			$scope.selectedSession = data[0].sessionID;
			
			$scope.updateData($scope.selectedSession);
			
		}).error(function(data, status, headers, config) {
			console.log('Error on getting session IDs')
		});
	
	
	
	$scope.updateData = function (sessionID) {
		$scope.selectedSession = sessionID;
		console.log("Updating the data " + sessionID)
		
		setPoints($scope.selectedSession);
		setDistancePoints($scope.selectedSession);
		
	}
	
}])

