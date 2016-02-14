 'use strict';

angular.module('hotSpiceApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-hotSpiceApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-hotSpiceApp-params')});
                }
                return response;
            }
        };
    });
