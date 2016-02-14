'use strict';

angular.module('hotSpiceApp')
    .config(function($stateProvider) {
        $stateProvider
            .state('home', {
                parent: 'site',
                url: '/',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/main/main.html',
                        controller: 'MainController'
                    }
                },
                resolve: {

                }
            })
            .state('viewDish', {
                url: '/dish/{id}',
                data: {
                    authorities: [],
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
                        return Dish.get({
                            id: $stateParams.id
                        });
                    }]
                }
            })
            .state('cart', {
                parent: 'home',
                url: '/cart',
                data: {
                    authorities: ['ROLE_USER','ROLE_CHEF','ROLE_OPERATOR','ROLE_ADMIN']

                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/dish/dish-cart.html',
                             controller: 'DishCheckoutController'
                    }
                }
            })
            .state('dishCatalog', {
                parent: 'home',
                url: '/dishs/:slug/catalog',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/main/main.html',
                        controller: 'DishCatalogController'
                    }
                }
            });
    });
