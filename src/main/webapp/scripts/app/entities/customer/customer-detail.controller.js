'use strict';

angular.module('warehouseApp')
    .controller('CustomerDetailController', function ($scope, $rootScope, $stateParams, entity, Customer, Address) {
        $scope.customer = entity;
        $scope.load = function (id) {
            Customer.get({id: id}, function(result) {
                $scope.customer = result;
            });
        };
        var unsubscribe = $rootScope.$on('warehouseApp:customerUpdate', function(event, result) {
            $scope.customer = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
