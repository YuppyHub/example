<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Spring 4 MVC File Download Example</title>
	<link href="<c:url value='/static/css/bootstrap.css' />"  rel="stylesheet"></link>
	<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>
<body>
	<div class="form-container">
		<h1>Welcome to FileDownloader Example</h1>
		
		Click on below links to see FileDownload in action.<br/><br/>
		
		
		<a href="<c:url value='/download/internal' />">Download This File (located inside project)</a>  
		<br/>
		<a href="<c:url value='/download/external' />">Download This File (located outside project, on file system)</a>
		
	</div> 
</body>
</html>
