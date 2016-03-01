'use strict';

angular.module('hotSpiceApp')
    .factory('Websocket', function ($q, $timeout) {
      var service={};
        // var stompClient = null;
        var subscriber = null;
        var listener = $q.defer();
        var socket = {
			client: null,
			stomp: null
		};
        // var connected = $q.defer();
        // var alreadyConnectedOnce = false;

        service.RECONNECT_TIMEOUT = 30000;

        service.send = function(result) {
          socket.stomp.send('/api/newDish',
              {},
              JSON.stringify(result));
      		};


          var reconnect = function() {
        			$timeout(function() {
        				initialize();
        			}, this.RECONNECT_TIMEOUT);
        		};

        service.receive = function() {
    return listener.promise;
  };

        var startListener = function() {
          socket.stomp.subscribe("/topic/newDish", function(data) {
              listener.notify(JSON.parse(data.body));
          });
  };

            var initialize= function () {
                //building absolute path so that websocket doesnt fail when deploying with a context path
                var loc = window.location;
                console.log(loc);
                var url = '//' + loc.host + loc.pathname + 'websocket/dish';
                console.log(url);
                socket.client = new SockJS(url);
                socket.stomp = Stomp.over(socket.client);
                socket.stomp.connect({},startListener);
                socket.stomp.onclose=reconnect;
              };
        initialize();
      		return service;
    });
