'use strict';

angular.module('hotSpiceApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


