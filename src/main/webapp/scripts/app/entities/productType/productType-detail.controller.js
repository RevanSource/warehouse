'use strict';

angular.module('warehouseApp')
    .controller('ProductTypeDetailController', function ($scope, $rootScope, $stateParams, entity, ProductType) {
        $scope.productType = entity;
        $scope.load = function (id) {
            ProductType.get({id: id}, function(result) {
                $scope.productType = result;
            });
        };
        var unsubscribe = $rootScope.$on('warehouseApp:productTypeUpdate', function(event, result) {
            $scope.productType = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
