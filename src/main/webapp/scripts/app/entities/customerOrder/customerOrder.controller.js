'use strict';

angular.module('warehouseApp')
    .controller('CustomerOrderController', function ($scope, $state, CustomerOrder, CustomerOrderSearch, ParseLinks) {

        $scope.customerOrders = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 0;
        $scope.loadAll = function() {
            CustomerOrder.query({page: $scope.page, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.customerOrders.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 0;
            $scope.customerOrders = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            CustomerOrderSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.customerOrders = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.reset();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.customerOrder = {
                orderDate: null,
                plannedDeliveryDate: null,
                actualDeliveryDate: null,
                status: null,
                otherDetails: null,
                id: null
            };
        };
    });
