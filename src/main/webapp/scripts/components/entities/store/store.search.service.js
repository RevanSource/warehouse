'use strict';

angular.module('warehouseApp')
    .factory('StoreSearch', function ($resource) {
        return $resource('api/_search/stores/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
