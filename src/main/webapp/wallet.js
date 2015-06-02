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
});

//CONTROLLERS
myApp.controller('homeController', function ($scope, $http) {

    console.log('homeController');

    $scope.hasErrorMessage = false;
    $scope.errorMessage = '';

    //GET TODAY DATE
    $scope.amount = '';
    $scope.description = '';
    $scope.expenseDate = getCurrentDate2();


    $scope.$watch('amount', function () {

        var allButLast = $scope.amount.slice(0, -1);
        var lastSymbol = $scope.amount.slice(-1);

        if (allButLast.match(/[0-9]+\.[0-9]{2}/)) {
            $scope.amount = allButLast;
        }

        if (lastSymbol == '.' && $scope.amount.length == 1) {
            $scope.amount = '';
        }

        if (lastSymbol == '.' && allButLast.indexOf('.') != -1) {
            $scope.amount = allButLast;
        }

    });



    $('.datepicker').datepicker({autoclose: true, format: 'dd-MM-yyyy', orientation: 'top'});

    // EXTRACT FORM DATA AND SEND IT TO THE SERVER TO BE SAVED
    $scope.add = function () {

        var chosenDate = document.getElementById('expenseDate').value;
        var e = document.getElementById("select");
        var type = e.options[e.selectedIndex].text;

        if (document.getElementById("select").value == 0) {
            $scope.errorMessage = "Please select the type!"
            $scope.hasErrorMessage = true;
            return;
        }

        if ($scope.amount.slice(-1) == '.' || $scope.amount == '') {
            $scope.errorMessage = "Please enter proper amount!";
            $scope.hasErrorMessage = true;
            return;
        }

        $http.post('rest/put', {
            'type': type, 'amount': $scope.amount, 'date': chosenDate, 'description': $scope.description, 'id': ''
        }).success(function (result) {
            $.bootstrapGrowl("Successful adding Expense of " + $scope.amount + '.00 BGN', {
                type: 'success',
                width: 'auto',
                align: 'left'
            });
            //resetExpenseForm();
            $scope.amount = '';
            $scope.description = '';
            document.getElementById("select").value = "0";
            document.getElementById('expenseDate').value = getCurrentDate();
            $scope.hasErrorMessage = false;
            $scope.errorMessage = '';

        }).error(function (result) {
            $.bootstrapGrowl("Error adding of expense of " + $scope.amount + '.00 BGN', {type: 'danger'});
        });
    };


});

myApp.controller('reportController', function ($scope, $http) {
    console.log('reportcontroller');

    $http.get('rest/get').success(function (result) {
        $scope.datalists = result;
    });

    var pagesShown = 1;

    var pageSize = 3;

    $scope.paginationLimit = function (data) {
        return pageSize * pagesShown;
    };

    $scope.hasMoreItemsToShow = function () {
        return pagesShown < ($scope.datalists.length / pageSize);
    };

    $scope.showMoreItems = function () {
        pagesShown = pagesShown + 1;
    };

    $scope.showAllItems = function () {
        pagesShown = $scope.datalists.length / pageSize;
    };

});

