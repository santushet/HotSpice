angular.module('hotSpiceApp')
  .controller('SidebarController', function ($scope, Category,$location) {
    $scope.category = Category.query();

    $scope.isActive = function(route) {
      //  console.log($location.path().indexOf(route));
         return $location.path().indexOf(route)>-1;
       };
  });
