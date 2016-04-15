'use strict';

angular.module('warehouseApp')
    .controller('AddressDetailController', function ($scope, $rootScope, $stateParams, entity, Address) {
        $scope.address = entity;
        $scope.load = function (id) {
            Address.get({id: id}, function(result) {
                $scope.address = result;
            });
        };
        var unsubscribe = $rootScope.$on('warehouseApp:addressUpdate', function(event, result) {
            $scope.address = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
