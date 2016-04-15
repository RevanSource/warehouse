'use strict';

angular.module('warehouseApp')
    .factory('OrderItemSearch', function ($resource) {
        return $resource('api/_search/orderItems/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
