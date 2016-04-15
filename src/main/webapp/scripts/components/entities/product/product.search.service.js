'use strict';

angular.module('warehouseApp')
    .factory('ProductSearch', function ($resource) {
        return $resource('api/_search/products/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
