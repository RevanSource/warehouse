'use strict';

describe('Controller Tests', function() {

    describe('Promotion Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPromotion;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPromotion = jasmine.createSpy('MockPromotion');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Promotion': MockPromotion
            };
            createController = function() {
                $injector.get('$controller')("PromotionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'warehouseApp:promotionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
