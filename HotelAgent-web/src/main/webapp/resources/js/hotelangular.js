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
    
    
})();