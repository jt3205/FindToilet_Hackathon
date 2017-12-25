<%@page import="toilet.ToiletVO"%>
<%@page import="toilet.ToiletDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%
response.addHeader("Access-Control-Allow-Origin", "*");  
response.setHeader("Access-Control-Allow-Headers", "origin, x-requested-with, content-type, accept");
%>
<%! 
	ToiletDAO dao = new ToiletDAO();
	ToiletVO vo[] = null;
%>
<%
	vo = dao.getAroundToilet(Double.parseDouble(request.getParameter("x")), Double.parseDouble(request.getParameter("y")));
%>
[
	<% for (int i = 0; i < vo.length; i++){ %>
	{
	no:"<%=vo[i].getNo()%>", 
	name:"<%=vo[i].getName()%>",
	x:<%=vo[i].getX()%>,
	y:<%=vo[i].getY()%>,
	kind:"<%=vo[i].getKind()%>"
	}<%if(i != vo.length-1) {%>,<%}%>
	<% } %>
];