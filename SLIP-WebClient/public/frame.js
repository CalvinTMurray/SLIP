angular.module('appN', []).
controller('Frame', function($scope, $http, $timeout) {

  // Function to get the data
  $scope.getData = function(){
    $http.get('http://localhost:8080/payloads')
      .success(function(data, status, headers, config) {

    	  $scope.payloads = data;
      console.log('Fetched data!');
    });
  }; 
  
});