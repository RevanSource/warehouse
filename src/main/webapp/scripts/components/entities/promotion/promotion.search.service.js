'use strict';

angular.module('warehouseApp')
    .factory('PromotionSearch', function ($resource) {
        return $resource('api/_search/promotions/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
