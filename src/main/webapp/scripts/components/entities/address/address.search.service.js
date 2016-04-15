'use strict';

angular.module('warehouseApp')
    .factory('AddressSearch', function ($resource) {
        return $resource('api/_search/addresss/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
