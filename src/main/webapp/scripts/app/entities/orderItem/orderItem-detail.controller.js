'use strict';

angular.module('warehouseApp')
    .controller('OrderItemDetailController', function ($scope, $rootScope, $stateParams, entity, OrderItem, CustomerOrder, Product) {
        $scope.orderItem = entity;
        $scope.load = function (id) {
            OrderItem.get({id: id}, function(result) {
                $scope.orderItem = result;
            });
        };
        var unsubscribe = $rootScope.$on('warehouseApp:orderItemUpdate', function(event, result) {
            $scope.orderItem = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
