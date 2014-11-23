angular.module('mtsModule', [])

.controller('sessionDropdownController', ['$http', '$scope', function($http, $scope) {
	$http.get('http://172.20.128.98:8080/all-sessions').
		success(function(data, status, headers, config) {
			$scope.sessionsIDs = data;
		}).error(function(data, status, headers, config) {
			console.log('Error on getting session IDs')
		});
}])

