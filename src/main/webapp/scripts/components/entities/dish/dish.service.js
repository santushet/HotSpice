'use strict';

angular.module('hotSpiceApp')
    .factory('Dish', function($resource, DateUtils,Upload, $q) {
        var resource = $resource('api/dishs/:id/:controller', {}, {
            'query': {
                method: 'GET',
                isArray: true
            },
            'get': {
                method: 'GET',
                transformResponse: function(data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': {
                method: 'PUT'
            },
            'catalog': {
                method: 'GET',
                isArray: true,
                params: {
                    controller: 'catalog'
                }
            }
        });

        resource.upload = function(file, productId) {
            var d = $q.defer();

            if (file && !file.$error) {
                file.upload = Upload.upload({
                    url: '/api/dishs/' + productId + '/upload',
                    file: file
                });

                file.upload.then(function(response) {
                    $timeout(function() {
                        d.resolve(response.data);
                    });
                }, function(response) {
                    if (response.status > 0) {
                        d.reject(response);
                    }
                });

                file.upload.progress(function(evt) {
                    d.notify({
                        progress: Math.min(100, parseInt(100.0 * evt.loaded / evt.total))
                    }, evt);
                });
            } else {
                d.reject(file ? file.$error : 'No picture file');
            }
            return d.promise;
        };
        return resource;
    });
