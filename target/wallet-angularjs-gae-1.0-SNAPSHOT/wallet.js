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

//CONTROLLERS
myApp.controller('homeController', function ($scope, $http) {

  resetForm();
  setUpDatePicker();
  monitorAmountForEnteringProperValues();


  // EXTRACT FORM DATA AND SEND IT TO THE SERVER TO BE SAVED
  $scope.add = function () {
    if (chosenTypeIsValid() && amountIsValid()) {
      $http.post('rest/put', {
        'type': $scope.selectedType,
        'amount': $scope.amount,
        'date': $scope.expenseDate,
        'description': $scope.description,
        'id': ''
      }).success(function (result) {
        $.bootstrapGrowl("Successful adding Expense of " + $scope.amount + ' BGN', {
          type: 'success',
          width: 'auto',
          align: 'left'
        });
        resetForm();
      }).error(function (result) {
        $.bootstrapGrowl("Error adding of expense of " + $scope.amount + ' BGN', {type: 'danger'});
      });
    }
  };

  //INIT TYPES
  $scope.expenseTypes = ["Books", "Bills", "Car", "Computer", "Food", "Fuel"];
  //END INIT TYPES

  //SETUP DATEPICKER
  function setUpDatePicker() {
    $('.datepicker').datepicker({autoclose: true, format: 'dd-MM-yyyy', orientation: 'top'});
  }

  //END SETUP DATEPICKER
  //RESET FORM VALUES
  function resetForm() {
    $scope.amount = '';
    $scope.description = '';
    $scope.selectedType = '-Select Expense type-';
    $scope.expenseDate = getCurrentDate2();
    $scope.hasErrorMessage = false;
    $scope.errorMessage = '';
  }

  //END RESET FORM VALUES
  function chosenTypeIsValid() {
    if ($scope.selectedType == "-Select Expense type-" || $scope.selectedType == null) {
      $scope.errorMessage = "Please select the type!";
      $scope.hasErrorMessage = true;
      return false;
    }
    return true;
  }

  function amountIsValid() {
    if ($scope.amount == '' || $scope.amount.slice(-1) == '.' || $scope.amount == 0) {
      $scope.errorMessage = "Please enter proper amount!";
      $scope.hasErrorMessage = true;
      return false;
    }
    if ($scope.amount.substr($scope.amount.length - 2, 1) == '.') {
      $scope.amount += "0";
    }
    if ($scope.amount.indexOf('.') == -1) {
      $scope.amount += ".00";
    }
    return true;
  }

  //MONITOR THE AMOUNT INPUT
  function monitorAmountForEnteringProperValues() {
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
  }

  //END MONITOR THE AMOUNT INPUT

});

myApp.controller('reportController', function ($scope, $http) {
  console.log('reportcontroller');

  //GET ALL EXPENSES
  $http.get('rest/get').success(function (result) {
    $scope.datalists = result;
  });
  //END GET ALL EXPENSES
  // DELETE EXPENSE
  $scope.deleteExpense = function (id) {
    $http.delete('rest/del/' + id).success(function (result) {
      for (var i = 0; i < $scope.datalists.length; i++) {
        if ($scope.datalists[i].id == id) {
          $scope.datalists.splice(i, 1);
          break;
        }
      }
    });
  };
  // END DELETE EXPENSE
  //DELETE ALL EXPENSES
  $scope.deleteAllExpenses = function () {
    $http.delete('rest/del/all').success(function (result) {
      $scope.datalists = [];
    });
  };
  //END DEL ALL EXPENSES
  //PAGINATION
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
  //END PAGINATION
});

myApp.controller("editController", function ($scope, $http, $routeParams,$location) {

  $http.get('rest/get/'+$routeParams.expenseId).success(function (result) {
    console.log(result);

    $scope.expense = result;
    setForm();
  });

  setUpDatePicker();
  monitorAmountForEnteringProperValues();

  function setForm() {
    $scope.id=$scope.expense.id;
    $scope.amount = $scope.expense.amount;
    $scope.description = $scope.expense.description;
    $scope.selectedType = $scope.expense.type;
    $scope.expenseDate = $scope.expense.date;
    $scope.hasErrorMessage = false;
    $scope.errorMessage = '';
  }

  // EXTRACT FORM DATA AND SEND IT TO THE SERVER TO BE SAVED
  $scope.edit = function () {
    if (chosenTypeIsValid() && amountIsValid()) {
      $http.put('rest/edit/'+$scope.id, {
        'type': $scope.selectedType,
        'amount': $scope.amount,
        'date': $scope.expenseDate,
        'description': $scope.description,
        'id': $scope.id
      }).success(function (result) {
        $.bootstrapGrowl("Successful editing" , {
          type: 'success',
          width: 'auto',
          align: 'left'
        });
        $location.path("report");
        //resetForm(); //todo redirect to the home page
      }).error(function (result) {
        $.bootstrapGrowl("Error at edit", {type: 'danger'});
      });
    }
  };

  //INIT TYPES
  $scope.expenseTypes = ["Books", "Bills", "Car", "Computer", "Food", "Fuel"];
  //END INIT TYPES

  //SETUP DATEPICKER
  function setUpDatePicker() {
    $('.datepicker').datepicker({autoclose: true, format: 'dd-MM-yyyy', orientation: 'top'});
  }

  //END SETUP DATEPICKER
  //RESET FORM VALUES
  function resetForm() {
    $scope.amount = '';
    $scope.description = '';
    $scope.selectedType = '-Select Expense type-';
    $scope.expenseDate = getCurrentDate2();
    $scope.hasErrorMessage = false;
    $scope.errorMessage = '';
  }

  //END RESET FORM VALUES
  function chosenTypeIsValid() {
    if ($scope.selectedType == "-Select Expense type-" || $scope.selectedType == null) {
      $scope.errorMessage = "Please select the type!";
      $scope.hasErrorMessage = true;
      return false;
    }
    return true;
  }

  function amountIsValid() {
    if ($scope.amount == '' || $scope.amount.slice(-1) == '.' || $scope.amount == 0) {
      $scope.errorMessage = "Please enter proper amount!";
      $scope.hasErrorMessage = true;
      return false;
    }
    if ($scope.amount.substr($scope.amount.length - 2, 1) == '.') {
      $scope.amount += "0";
    }
    if ($scope.amount.indexOf('.') == -1) {
      $scope.amount += ".00";
    }
    return true;
  }

  //MONITOR THE AMOUNT INPUT
  function monitorAmountForEnteringProperValues() {
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
  }
  //END MONITOR THE AMOUNT INPUT

});




