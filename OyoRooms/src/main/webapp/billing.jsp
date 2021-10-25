<%@page import="com.java.oyo.Billing"%>
<%@page import="com.java.oyo.Room"%>
<%@page import="com.java.oyo.Status"%>
<%@page import="java.util.List"%>
<%@ page import="com.java.oyo.MainProg" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
	Billing bill = new Billing();
	bill.setBookid(request.getParameter("bookid"));
	bill.setRoomid(request.getParameter("roomid"));

		MainProg obj=new MainProg();
		int res = obj.dobilling(bill); 
		out.println("The Amount Due is "+res); 
	%>
</body>
</html>