'use strict';

angular.module('hotSpiceApp')
    .controller('CategoryController', function ($scope, $state, Category, ParseLinks) {

        $scope.categorys = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Category.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.categorys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.category = {
                name: null,
                slug: null,
                info: null,
                active: null,
                id: null
            };
        };
    })
    .controller('CategoryDialogController',
      ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Category',
          function($scope, $stateParams, $uibModalInstance, entity, Category) {

          $scope.category = entity;
          $scope.load = function(id) {
              Category.get({id : id}, function(result) {
                  $scope.category = result;
              });
          };

          var onSaveSuccess = function (result) {
              $scope.$emit('hotSpiceApp:categoryUpdate', result);
              $uibModalInstance.close(result);
              $scope.isSaving = false;
          };

          var onSaveError = function (result) {
              $scope.isSaving = false;
          };

          $scope.save = function () {
              $scope.isSaving = true;
              if ($scope.category.id != null) {
                  Category.update($scope.category, onSaveSuccess, onSaveError);
              } else {
                  Category.save($scope.category, onSaveSuccess, onSaveError);
              }
          };

          $scope.clear = function() {
              $uibModalInstance.dismiss('cancel');
          };
  }])
  .controller('CategoryDetailController', function ($scope, $rootScope, $stateParams, entity, Category) {
      $scope.category = entity;
      $scope.load = function (id) {
          Category.get({id: id}, function(result) {
              $scope.category = result;
          });
      };
      var unsubscribe = $rootScope.$on('hotSpiceApp:categoryUpdate', function(event, result) {
          $scope.category = result;
      });
      $scope.$on('$destroy', unsubscribe);

  })
  .controller('CategoryDeleteController', function($scope, $uibModalInstance, entity, Category) {

        $scope.category = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Category.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
