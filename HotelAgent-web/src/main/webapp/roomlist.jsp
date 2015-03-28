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
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>        
        <style>
            .btn-product { width: 100%; }
        </style>               
        <title>Bookworm Online Store</title>
    </head>
    <body>
       <div class="container">        
        <c:set var="row" value="${0}" />
        <c:forEach items="${requestScope.ROOMS_LIST}" var="room">
            <c:url value="/" var="hotelctrl">
                <c:param name="action"   value="${'bookroom'}" />
                <c:param name="roomId"   value="${room.roomId}" />
            </c:url>             
            <c:if test="${row  == 0}">
            <div class="row">
                <div class="col-md-12">
            </c:if>                    
                    <div class="col-sm-6 col-md-4">
                        <div class="thumbnail" >
                            <h4 class="text-center"><span class="label label-info"> ${room.roomType}</span></h4>
                            <a href="${hotelctrl}"> <img src="http://placehold.it/320x200&text=room" class="img-responsive"></a>
                            <div class="caption">
                                <div class="row">
                                    <div class="col-md-6 col-xs-6">
                                        <h3>${room.roomSize}</h3>
                                    </div>
                                    <div class="col-md-6 col-xs-6 price">
                                        <h3>
                                            <label>${room.roomPrice}</label></h3>
                                    </div>
                                </div>
                                <p>${room.roomDescription}</p>
                                <p> </p>
                            </div>
                        </div>
                    </div>
                
            <c:set var="row" value="${row +1}" />
            <c:if test="${row == 3}">                
                <c:set var="row" value="${0}" />
                </div>    
            </div> <!-- row -->
            </c:if>
        </c:forEach>    
        </div>            <!-- container -->     
    </body>
</html>

