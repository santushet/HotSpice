'use strict';

angular.module('hotSpiceApp')
    .controller('MainController', function ($scope, $state, Dish,Principal,Websocket) {
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

        Websocket.receive().then(null, null, function(dish) {
            showActivity(dish);
        });

        function showActivity(dish) {
            var index=$scope.dishs.indexOf(dish);
            console.log(index);
            if(index==-1){
              $scope.dishs.push(dish);
            }
            else{
              $scope.dishs.splice(index,1);
            }
            $scope.loadAll();
        };
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
