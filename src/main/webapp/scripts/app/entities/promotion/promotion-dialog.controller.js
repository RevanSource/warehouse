'use strict';

angular.module('warehouseApp').controller('PromotionDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Promotion',
        function($scope, $stateParams, $uibModalInstance, entity, Promotion) {

        $scope.promotion = entity;
        $scope.load = function(id) {
            Promotion.get({id : id}, function(result) {
                $scope.promotion = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('warehouseApp:promotionUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.promotion.id != null) {
                Promotion.update($scope.promotion, onSaveSuccess, onSaveError);
            } else {
                Promotion.save($scope.promotion, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForDateFrom = {};

        $scope.datePickerForDateFrom.status = {
            opened: false
        };

        $scope.datePickerForDateFromOpen = function($event) {
            $scope.datePickerForDateFrom.status.opened = true;
        };
        $scope.datePickerForDateTo = {};

        $scope.datePickerForDateTo.status = {
            opened: false
        };

        $scope.datePickerForDateToOpen = function($event) {
            $scope.datePickerForDateTo.status.opened = true;
        };
}]);
