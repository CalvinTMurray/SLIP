angular.module('applicationServerTime', []).
controller('server-time-controller', function($scope, $http, $timeout) {

  // Function to get the data
  $scope.getData = function(){
    $http.get('http://localhost:8080/greeting')
      .success(function(data, status, headers, config) {

    	  $scope.greeting = data;
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

  // Kick off the interval
  $scope.intervalFunction();
  
});