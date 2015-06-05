var myApp = angular.module('walletApp', ['ngRoute','angular-loading-bar', 'ngAnimate']);

//ROUTES
myApp.config(function ($routeProvider) {
  $routeProvider
          .when('/', {
            templateUrl: 'pages/home.htm',
            controller: 'homeController'
          })
          .when('/report', {
            templateUrl: 'pages/report.htm',
            controller: 'reportController',
          })
          .when('/edit/:expenseId', {
            templateUrl: 'pages/edit.htm',
            controller: 'editController'
          })
          .otherwise({
            redirectTo: '/'
          })
  ;

});

// SECURITY FILTER
myApp.config(function ($httpProvider) {
  $httpProvider.interceptors.push(function ($q,$location,$window) {
    return {
      'response': function (response) {
        return response;
      },
      'responseError': function (rejection) {
        if (rejection.status === 401) {
          $window.location.href = '/login';
        }
        return $q.reject(rejection);
      }
    };
  });
});

