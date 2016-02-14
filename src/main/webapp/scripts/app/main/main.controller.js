'use strict';

angular.module('hotSpiceApp')
    .controller('MainController', function ($scope, $state, Dish,Principal) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });

        $scope.dishs = [];
        $scope.loadAll = function() {
            Dish.query(function(result) {
               $scope.dishs = result;
            });
        };
        $scope.loadAll();
        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.dish = {
                name: null,
                price: null,
                stock: null,
                description: null,
                id: null
            };
        };

    });
