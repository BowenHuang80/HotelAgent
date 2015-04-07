<%-- 
    Document   : buybook
    Created on : 5-Mar-2015, 8:54:11 PM
    Author     : brian
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
        <!-- Optional theme -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
        <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">        
        <!-- Latest compiled and minified JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>        
        <script>
        $(function() {
          $( "#datepicker" ).datepicker();
        });
        </script>        
        <style>
            .btn-product { width: 100%; }
        </style>               

        <title>JSP Page</title>
    </head>
    <body>
        <c:set var="book" value="${requestScope.TARGET_BOOK}" />
        <c:url value="ListController" var="addUrl">
            <c:param name="action"   value="${'addtocart'}" />
            <c:param name="bookId"   value="${book.bookId}" />
            <c:param name="bookName"    value="${book.bookName}" />
        </c:url> 
        <c:url value="ListController" var="checkoutUrl">
            <c:param name="action"   value="${'checkout'}" />
        </c:url> 
        
        <div class="container">
            <div class="row">
                <div class="col-sm-2 col-md-2">
                    <img src="http://thetransformedmale.files.wordpress.com/2011/06/bruce-wayne-armani.jpg"
                    alt="" class="img-rounded img-responsive" />
                </div>
                <div class="col-sm-4 col-md-4">
                    <blockquote>
                        <p><c:out value="${book.bookName}" /></p> 
                        <small><cite title="Source Title"><c:out value="${book.author}" /></cite></small>
                    </blockquote>
                    
                    <p> 
                        <i class="glyphicon glyphicon-usd"></i> <c:out value="${book.bookPrice}" /><br />   
                        <i class="glyphicon glyphicon-info-sign"></i> <c:out value="${book.bookDesc}"/>
                    </p>
                    <form>
                    <p>Date: <input type="text" id="datepicker"></p>
                    <div class="btn-group">
                        <div class="col-md-6">
                            <a href="<c:out value="${addUrl}"/>" class="btn btn-primary btn-product"><span class="glyphicon glyphicon-thumbs-up"></span> Add to Cart</a> 
                        </div>
                    </div>
                    </form>
                </div>
            </div>
        </div>        
        
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>                    
    </body>
</html>