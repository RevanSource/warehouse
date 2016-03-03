'use strict';

angular.module('warehouseApp')
    .controller('PromotionController', function ($scope, $state, Promotion, PromotionSearch, ParseLinks) {

        $scope.promotions = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 0;
        $scope.loadAll = function() {
            Promotion.query({page: $scope.page, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.promotions.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 0;
            $scope.promotions = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            PromotionSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.promotions = result;
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
            $scope.promotion = {
                name: null,
                dateFrom: null,
                dateTo: null,
                description: null,
                otherDetails: null,
                id: null
            };
        };
    });
