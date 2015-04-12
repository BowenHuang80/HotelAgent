/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


(function(){
    var app = angular.module("hotelAgent",[]);
    app.controller("roomListController", ['$http','$log', function($http, $log){
        var roomsSrv = this;
        var roomsList = [];
        
        $http.get("restapiv1/rooms")
                .success(function(data){
                    roomsSrv.roomsList = data;
                })
                .error(function(msg){
                var error = msg;
                });
    }]);
    
    app.controller("MsgController", ['$http', '$log', function($http, $log){
        var msgSrv = this;
        var msgList = [];
        $http.get("restapiv1/message")
                .success(function(msg) {
                    msgSrv = msg;
                })
                .error(function(msg){
                   var error = msg; 
                });
    }]);

    app.controller("RealtimeMsgController", ['$http', '$log', '$scope', '$interval', function($http, $log, $scope, $interval){

        $scope.format = 'M/d/yy h:mm:ss a';
        $scope.msgList = [];
        $scope.msgNum = 0;
        var msgMonitor;
        
        $scope.startMsgMonitor = function() {
          // Don't start a new fight if we are already fighting
          if ( angular.isDefined(msgMonitor) ) return;

          msgMonitor = $interval(function() {
            var datetime = new Date().getTime(); 
            $http.get("restapiv1/message" + datetime)
                .success(function(msg) {
                    if( msg.length > 0  ) {
                        $scope.msgNum += $scope.msgNum;
                        $scope.msgList.push(msgNum);
                    }
                })
                .error(function(msg){
                   var error = msg; 
                });
              
          }, 3000);
        };

        $scope.stopMsgMonitor = function() {
          if (angular.isDefined(msgMonitor)) {
            $interval.cancel(msgMonitor);
            msgMonitor = undefined;
          }
        };

        $scope.resetMsgArea = function() {
          $scope.msgArea = "";
        };

        $scope.$on('$destroy', function() {
          // Make sure that the interval is destroyed too
          $scope.stopMsgMonitor();
        });                
                
    }]);

    app.directive('msgMonitor', ['$interval', 'dateFilter', '$http','$scope', function($interval, dateFilter, $http, $scope){
        return function(scope, element, attrs) {
            var format,  // date format
                stopTime; // so that we can cancel the time updates

            // used to update the UI
            function updateMsg() {
                var datetime = new Date().getTime();
                

              element.text(dateFilter(new Date(), format));
            }
            
            scope.$watch(attrs.msgMonitor, function(value) {
              format = value;
              updateTime();
            });
          
            //stopTime = $interval(updateMsg, 2000);

            // listen on DOM destroy (removal) event, and cancel the next UI update
            // to prevent updating time after the DOM element was removed.
//            element.on('$destroy', function() {
//              $interval.cancel(stopTime);
//            });
        }
        
    }]);
})();