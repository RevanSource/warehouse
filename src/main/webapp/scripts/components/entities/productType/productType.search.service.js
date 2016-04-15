'use strict';

angular.module('warehouseApp')
    .factory('ProductTypeSearch', function ($resource) {
        return $resource('api/_search/productTypes/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
