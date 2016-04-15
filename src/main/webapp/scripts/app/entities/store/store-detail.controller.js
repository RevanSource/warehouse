'use strict';

angular.module('warehouseApp')
    .controller('StoreDetailController', function ($scope, $rootScope, $stateParams, entity, Store, Address) {
        $scope.store = entity;
        $scope.load = function (id) {
            Store.get({id: id}, function(result) {
                $scope.store = result;
            });
        };
        var unsubscribe = $rootScope.$on('warehouseApp:storeUpdate', function(event, result) {
            $scope.store = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
