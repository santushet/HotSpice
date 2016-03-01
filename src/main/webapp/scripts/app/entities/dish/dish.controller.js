'use strict';

var uploadHander;
angular.module('hotSpiceApp')
    .controller('DishController', function($scope, $state, Dish,Websocket) {

      $scope.dishs = [];
      $scope.loadAll = function() {
          Dish.query(function(result) {
              $scope.dishs = result;
          });
      };
      $scope.loadAll();
      // Websocket.receive().then(null, null, function(dish) {
      //     showActivity(dish);
      // });

      // function showActivity(dish) {
      //     var index=$scope.dishs.indexOf(dish);
      //     console.log(index);
      //     if(index==-1){
      //       $scope.dishs.push(dish);
      //     }
      //     else{
      //       $scope.dishs.splice(index,1);
      //     }
      //     $scope.loadAll();
      // };

      $scope.refresh = function() {
          $scope.loadAll();
          $scope.clear();
      };

      $scope.clear = function() {

          $scope.dish = {
              name: null,
              price: null,
              stock: null,
              description: null,
              // id: null
          };
      };


    })
    .controller('DishDialogController',
        function($scope, $stateParams, $uibModalInstance, entity, Category, Dish,Upload, $timeout,Websocket) {

            $scope.dish = entity;
            $scope.cat = Category.query();
            $scope.load = function(id) {
                Dish.get({
                    id: id
                }, function(result) {
                    $scope.dish = result;
                    // console.log($scope.dish);
                });
            };

            var onSaveSuccess = function(result) {
                $scope.$emit('hotSpiceApp:dishUpdate', result);
                // Tracker.sendActivity(result);
                Websocket.send(result);
                $uibModalInstance.close(result);
                $scope.isSaving = false;

            };

            var onSaveError = function(result) {
                $scope.isSaving = false;
            };

            $scope.upload = uploadHander($scope, Upload, $timeout);


            $scope.save = function() {
                $scope.isSaving = true;
                if ($scope.dish.id != null) {
                    Dish.update($scope.dish, onSaveSuccess, onSaveError).$promise.then(function(dish){
                      Dish.upload($scope.dish.picture, dish.id);
                    })
                } else {
                    Dish.save($scope.dish, onSaveSuccess, onSaveError).$promise.then(function(dish){
                      Dish.upload($scope.dish.picture, dish.id);
                })
              }
            };





            $scope.clear = function() {
                $uibModalInstance.dismiss('cancel');
            };

            })
    .controller('DishDetailController', function($scope, $rootScope, $stateParams, entity, Dish) {
        $scope.dish = entity;
        $scope.load = function(id) {
            Dish.get({
                id: id
            }, function(result) {
                $scope.dish = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotSpiceApp:dishUpdate', function(event, result) {
            $scope.dish = result;
        });
        $scope.$on('$destroy', unsubscribe);

    })
    .controller('DishDeleteController', function($scope, $uibModalInstance, entity, Dish,Websocket) {

        $scope.dish = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function(id) {
          Dish.get({
              id: id
          }, function(result) {
            Websocket.send(result);
          });
            Dish.delete({
                    id: id
                },
                function() {
                    $uibModalInstance.close(true);
                });
        };

    })
    .controller('DishCatalogController', function($scope, $stateParams, Dish) {
        $scope.dishs = Dish.catalog({
            id: $stateParams.slug
        });
        //  $scope.query = $stateParams.slug;
    })
    .controller('DishCheckoutController',
        function($scope, Order, ngCart, $state, Principal) {
            //   $scope.errors = '';

            $scope.msg = 'No items in cart.';
            $scope.customer = {};
            var cartitems = [];
            $scope.count = ngCart.getTotalItems();

            $scope.placeOrder = function() {
                var overall = 0;
                angular.forEach(ngCart.getItems(), function(item) {
                    var order = {
                        dish: item._data,
                        quantity: item._quantity,
                        total: item._quantity * item._price
                    };
                    overall += (item._quantity * item._price);
                    cartitems.push(order);

                });
                Principal.identity().then(function(user) {
                    var data = {
                        name: $scope.customer.name,
                        shippingAddress: $scope.customer.address,
                        status: 'Order Placed',
                        items: cartitems,
                        user: user,
                        total: overall
                    };
                    // console.log(JSON.stringify(user));
                    // console.log(JSON.stringify(data));
                    Order.save(data);
                }, function(error) {
                    console.log(error);
                });
                ngCart.empty(true);
                $state.go('order');
            };


        }
    );
  uploadHander =  function ($scope, Upload, $timeout) {
  return function(file) {
    if (file && !file.$error) {
      $scope.file = file;
      file.upload = Upload.upload({
        url: '/api/dishs/'+$scope.dish._id+'/upload',
        file: file
      });

      file.upload.then(function (response) {
        $timeout(function () {
          file.result = response.data;
        });
      }, function (response) {
        if (response.status > 0){
          console.log(response.status + ': ' + response.data);
          errorHandler($scope)(response.status + ': ' + response.data);
        }
      });

      file.upload.progress(function (evt) {
        file.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
      });
    }
  };
};
