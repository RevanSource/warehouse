'use strict';

angular.module('warehouseApp').controller('StoreDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Store', 'Address',
        function($scope, $stateParams, $uibModalInstance, entity, Store, Address) {

        $scope.store = entity;
        $scope.addresss = Address.query();
        $scope.load = function(id) {
            Store.get({id : id}, function(result) {
                $scope.store = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('warehouseApp:storeUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.store.id != null) {
                Store.update($scope.store, onSaveSuccess, onSaveError);
            } else {
                Store.save($scope.store, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
