'use strict';

describe('Controller Tests', function() {

    describe('OrderItem Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockOrderItem, MockCustomerOrder, MockProduct;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockOrderItem = jasmine.createSpy('MockOrderItem');
            MockCustomerOrder = jasmine.createSpy('MockCustomerOrder');
            MockProduct = jasmine.createSpy('MockProduct');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'OrderItem': MockOrderItem,
                'CustomerOrder': MockCustomerOrder,
                'Product': MockProduct
            };
            createController = function() {
                $injector.get('$controller')("OrderItemDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'warehouseApp:orderItemUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
