'use strict';

angular.module('warehouseApp')
    .factory('Promotion', function ($resource, DateUtils) {
        return $resource('api/promotions/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.dateFrom = DateUtils.convertDateTimeFromServer(data.dateFrom);
                    data.dateTo = DateUtils.convertDateTimeFromServer(data.dateTo);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
