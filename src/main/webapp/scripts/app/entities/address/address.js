'use strict';

angular.module('warehouseApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('address', {
                parent: 'entity',
                url: '/addresss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'warehouseApp.address.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/address/addresss.html',
                        controller: 'AddressController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('address');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('address.detail', {
                parent: 'entity',
                url: '/address/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'warehouseApp.address.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/address/address-detail.html',
                        controller: 'AddressDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('address');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Address', function($stateParams, Address) {
                        return Address.get({id : $stateParams.id});
                    }]
                }
            })
            .state('address.new', {
                parent: 'address',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/address/address-dialog.html',
                        controller: 'AddressDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    line1: null,
                                    line2: null,
                                    city: null,
                                    state: null,
                                    country: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('address', null, { reload: true });
                    }, function() {
                        $state.go('address');
                    })
                }]
            })
            .state('address.edit', {
                parent: 'address',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/address/address-dialog.html',
                        controller: 'AddressDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Address', function(Address) {
                                return Address.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('address', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('address.delete', {
                parent: 'address',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/address/address-delete-dialog.html',
                        controller: 'AddressDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Address', function(Address) {
                                return Address.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('address', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
