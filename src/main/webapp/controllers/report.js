/**
 * Created on 15-6-3.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

myApp.controller('reportController', function ($scope, $http,$q,$timeout) {
  console.log('reportcontroller');

  // MAY NEED IT LATER
  var defer = $q.defer();
  defer.promise.then(function (d) {

  });

  $scope.showLoadingBar = true;
  $('#my-modal').modal('show');
  //GET ALL EXPENSES
  $http.get('rest/get').success(function (result) {
    $scope.datalists = result;
    $('#my-modal').modal('hide'); // MOVE TO INTERCEPTOR
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