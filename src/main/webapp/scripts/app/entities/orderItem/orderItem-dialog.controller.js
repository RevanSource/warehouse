'use strict';

angular.module('warehouseApp').controller('OrderItemDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'OrderItem', 'CustomerOrder', 'Product',
        function($scope, $stateParams, $uibModalInstance, entity, OrderItem, CustomerOrder, Product) {

        $scope.orderItem = entity;
        $scope.customerorders = CustomerOrder.query();
        $scope.products = Product.query();
        $scope.load = function(id) {
            OrderItem.get({id : id}, function(result) {
                $scope.orderItem = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('warehouseApp:orderItemUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.orderItem.id != null) {
                OrderItem.update($scope.orderItem, onSaveSuccess, onSaveError);
            } else {
                OrderItem.save($scope.orderItem, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
