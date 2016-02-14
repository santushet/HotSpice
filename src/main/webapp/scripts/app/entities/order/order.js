'use strict';

angular.module('hotSpiceApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('order', {
                parent: 'entity',
                url: '/orders',
                data: {
                    authorities: ['ROLE_USER','ROLE_OPERATOR','ROLE_ADMIN'],
                    pageTitle: 'Orders'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/order/orders.html',
                        controller: 'OrderController'
                    }
                },
                resolve: {
                }
            })

    });
