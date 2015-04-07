<%-- 
    Document   : index
    Created on : 26-Mar-2015, 12:43:50 AM
    Author     : brian
--%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css" />
        <!-- Optional theme -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css"/>
        <!-- Latest compiled and minified JavaScript -->
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css"/>

        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular.min.js"></script>
        <script src="resources/js/hotelangular.js"></script>
        <style>
            .btn-product { width: 100%; }
        </style>               
        <title>Bookworm Online Store</title>
    </head>
    <body ng-app="hotelAgent" ng-controller="roomListController as roomCtrl">
       <div class="container">        
           <div class="row" ng-repeat="room in roomCtrl.roomsList">
                <div class="col-md-12">
                    <div class="col-sm-6 col-md-4">
                        <div class="thumbnail" >
                            <h4 class="text-center"><span class="label label-info"> {{room.roomType}}</span></h4>
                            <a href> <img src="http://placehold.it/320x200&text=room" class="img-responsive"></a>
                            <div class="caption">
                                <div class="row">
                                    <div class="col-md-6 col-xs-6">
                                        <h3>{{room.roomSize}}</h3>
                                    </div>
                                    <div class="col-md-6 col-xs-6 price">
                                        <h3>
                                            <label>{{room.roomPrice}}</label></h3>
                                    </div>
                                </div>
                                <p>{{room.roomDescription}}</p>
                                <p> </p>
                            </div>
                        </div>
                    </div>                
                </div>    
            </div> <!-- row -->
        </div>            <!-- container -->     
    </body>
</html>

