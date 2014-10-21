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
  
  // Function to replicate setInterval using $timeout service.
  $scope.intervalFunction = function(){
    $timeout(function() {
      $scope.getData();
      $scope.intervalFunction();
    }, 1000)
  };
  
  $scope.intervalFunction();
  
});