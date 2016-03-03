'use strict';

angular.module('warehouseApp')
    .controller('PromotionDetailController', function ($scope, $rootScope, $stateParams, entity, Promotion) {
        $scope.promotion = entity;
        $scope.load = function (id) {
            Promotion.get({id: id}, function(result) {
                $scope.promotion = result;
            });
        };
        var unsubscribe = $rootScope.$on('warehouseApp:promotionUpdate', function(event, result) {
            $scope.promotion = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
