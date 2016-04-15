'use strict';

angular.module('warehouseApp').controller('CustomerOrderDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'CustomerOrder', 'Store', 'Customer',
        function($scope, $stateParams, $uibModalInstance, entity, CustomerOrder, Store, Customer) {

        $scope.customerOrder = entity;
        $scope.stores = Store.query();
        $scope.customers = Customer.query();
        $scope.load = function(id) {
            CustomerOrder.get({id : id}, function(result) {
                $scope.customerOrder = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('warehouseApp:customerOrderUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.customerOrder.id != null) {
                CustomerOrder.update($scope.customerOrder, onSaveSuccess, onSaveError);
            } else {
                CustomerOrder.save($scope.customerOrder, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForOrderDate = {};

        $scope.datePickerForOrderDate.status = {
            opened: false
        };

        $scope.datePickerForOrderDateOpen = function($event) {
            $scope.datePickerForOrderDate.status.opened = true;
        };
        $scope.datePickerForPlannedDeliveryDate = {};

        $scope.datePickerForPlannedDeliveryDate.status = {
            opened: false
        };

        $scope.datePickerForPlannedDeliveryDateOpen = function($event) {
            $scope.datePickerForPlannedDeliveryDate.status.opened = true;
        };
        $scope.datePickerForActualDeliveryDate = {};

        $scope.datePickerForActualDeliveryDate.status = {
            opened: false
        };

        $scope.datePickerForActualDeliveryDateOpen = function($event) {
            $scope.datePickerForActualDeliveryDate.status.opened = true;
        };
}]);
