'use strict';

angular.module('warehouseApp')
    .factory('CustomerOrder', function ($resource, DateUtils) {
        return $resource('api/customerOrders/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.orderDate = DateUtils.convertDateTimeFromServer(data.orderDate);
                    data.plannedDeliveryDate = DateUtils.convertDateTimeFromServer(data.plannedDeliveryDate);
                    data.actualDeliveryDate = DateUtils.convertDateTimeFromServer(data.actualDeliveryDate);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
