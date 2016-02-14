'use strict';

angular.module('hotSpiceApp')
    .controller('OrderController', function($scope, $state, Order) {

        $scope.orderStatusLov = Order.status;
        $scope.orders = [];
        $scope.loadAll = function() {
            Order.query(function(result) {
                $scope.orders = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function() {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function() {
            $scope.order = {
                name: null,
                shippingAddress: null,
                billingAddress: null,
                shipping: null,
                tax: null,
                discount: null,
                subTotal: null,
                total: null,
                status: null,
                id: null
            };
        };

        $scope.changeStatus = function(order) {
            Order.update({
                id: order._id
            }, order).$promise.then(function(res) {
                console.log(res);
            }, function(error) { // error handler
                console.log(error);

            });
        };
    });
