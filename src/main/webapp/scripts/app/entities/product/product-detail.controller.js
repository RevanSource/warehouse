'use strict';

angular.module('warehouseApp')
    .controller('ProductDetailController', function ($scope, $rootScope, $stateParams, entity, Product, ProductType) {
        $scope.product = entity;
        $scope.load = function (id) {
            Product.get({id: id}, function(result) {
                $scope.product = result;
            });
        };
        var unsubscribe = $rootScope.$on('warehouseApp:productUpdate', function(event, result) {
            $scope.product = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
