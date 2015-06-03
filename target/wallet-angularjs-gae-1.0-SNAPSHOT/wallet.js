var myApp = angular.module('walletApp', ['ngRoute', 'ngAnimate']);

//ROUTES
myApp.config(function ($routeProvider) {
  $routeProvider
          .when('/', {
            templateUrl: 'pages/home.htm',
            controller: 'homeController'
          })
          .when('/report', {
            templateUrl: 'pages/report.htm',
            controller: 'reportController'
          })
          .when('/edit/:expenseId', {
            templateUrl: 'pages/edit.htm',
            controller: 'editController'
          })
});



