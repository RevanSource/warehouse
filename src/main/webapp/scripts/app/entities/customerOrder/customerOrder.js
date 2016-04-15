'use strict';

angular.module('warehouseApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('customerOrder', {
                parent: 'entity',
                url: '/customerOrders',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'warehouseApp.customerOrder.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/customerOrder/customerOrders.html',
                        controller: 'CustomerOrderController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('customerOrder');
                        $translatePartialLoader.addPart('statusType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('customerOrder.detail', {
                parent: 'entity',
                url: '/customerOrder/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'warehouseApp.customerOrder.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/customerOrder/customerOrder-detail.html',
                        controller: 'CustomerOrderDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('customerOrder');
                        $translatePartialLoader.addPart('statusType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'CustomerOrder', function($stateParams, CustomerOrder) {
                        return CustomerOrder.get({id : $stateParams.id});
                    }]
                }
            })
            .state('customerOrder.new', {
                parent: 'customerOrder',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/customerOrder/customerOrder-dialog.html',
                        controller: 'CustomerOrderDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    orderDate: null,
                                    plannedDeliveryDate: null,
                                    actualDeliveryDate: null,
                                    status: null,
                                    otherDetails: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('customerOrder', null, { reload: true });
                    }, function() {
                        $state.go('customerOrder');
                    })
                }]
            })
            .state('customerOrder.edit', {
                parent: 'customerOrder',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/customerOrder/customerOrder-dialog.html',
                        controller: 'CustomerOrderDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['CustomerOrder', function(CustomerOrder) {
                                return CustomerOrder.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('customerOrder', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('customerOrder.delete', {
                parent: 'customerOrder',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/customerOrder/customerOrder-delete-dialog.html',
                        controller: 'CustomerOrderDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['CustomerOrder', function(CustomerOrder) {
                                return CustomerOrder.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('customerOrder', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
