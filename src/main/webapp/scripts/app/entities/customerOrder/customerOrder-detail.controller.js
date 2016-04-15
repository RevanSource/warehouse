'use strict';

angular.module('warehouseApp')
    .controller('CustomerOrderDetailController', function ($scope, $rootScope, $stateParams, entity, CustomerOrder, Store, Customer) {
        $scope.customerOrder = entity;
        $scope.load = function (id) {
            CustomerOrder.get({id: id}, function(result) {
                $scope.customerOrder = result;
            });
        };
        var unsubscribe = $rootScope.$on('warehouseApp:customerOrderUpdate', function(event, result) {
            $scope.customerOrder = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
