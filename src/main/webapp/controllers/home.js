/**
 * Created on 15-6-3.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

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
        $.bootstrapGrowl("Error adding of expense of " + $scope.amount + ' BGN' , {type: 'danger'});
        resetForm();
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