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
          .otherwise({
            //redirectTo: '/login'

            //redirect : function(){
            //  $window.location.href = '/login';
            //}
          })
  ;

});

myApp.config(function ($httpProvider) {
  $httpProvider.interceptors.push(function ($q,$location,$window) {
    return {
      'response': function (response) {
        //Will only be called for HTTP up to 300
        console.log(response);
        return response;
      },
      'responseError': function (rejection) {
        if (rejection.status === 401) {
          console.log("ERRROOOORRR");
          console.log($location);
          $window.location.href = '/login';
          //location.reload();
          //$location.url("login");
          //$location.replace();
        }
        return $q.reject(rejection);
      }
    };
  });
});

