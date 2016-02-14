'use strict';

angular.module('hotSpiceApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('dish', {
                parent: 'entity',
                url: '/dishs',
                data: {

                    pageTitle: 'Dishs'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/dish/dishs.html',
                        controller: 'DishController'
                    }
                },
                resolve: {
                }
            })
            .state('dish.detail', {
                parent: 'entity',
                url: '/dish/{id}',
                data: {

                    pageTitle: 'Dish'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/dish/dish-detail.html',
                        controller: 'DishDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Dish', function($stateParams, Dish) {
                        return Dish.get({id : $stateParams.id});
                    }]
                }
            })
            .state('dish.new', {
                parent: 'dish',
                url: '/new',
                data: {

                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/dish/dish-dialog.html',
                        controller: 'DishDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    price: null,
                                    stock: null,
                                    description: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('dish', null, { reload: true });
                    }, function() {
                        $state.go('dish');
                    })
                }]
            })
            .state('dish.edit', {
                parent: 'dish',
                url: '/{id}/edit',
                data: {

                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/dish/dish-dialog.html',
                        controller: 'DishDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Dish', function(Dish) {
                                return Dish.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('dish', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('dish.delete', {
                parent: 'dish',
                url: '/{id}/delete',
                data: {

                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/dish/dish-delete-dialog.html',
                        controller: 'DishDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Dish', function(Dish) {
                                return Dish.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('dish', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
