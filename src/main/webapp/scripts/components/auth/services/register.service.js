'use strict';

angular.module('warehouseApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


