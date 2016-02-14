'use strict';

angular.module('hotSpiceApp')
    .factory('Order', function ($resource, DateUtils) {
      var obj={};
        obj =$resource('api/orders/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
        obj.status=['Order Placed','Shipped','Delivered','Cancelled'];
        return obj;
    });
