angular.module('hotSpiceApp')
  .factory('Catalog', function ($resource) {
    return $resource('/api/catalogs/:id');
  });
