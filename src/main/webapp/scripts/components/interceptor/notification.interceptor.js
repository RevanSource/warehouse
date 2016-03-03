 'use strict';

angular.module('warehouseApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-warehouseApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-warehouseApp-params')});
                }
                return response;
            }
        };
    });
