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
	ToiletVO vo = null;
%>
<%
	vo = dao.getToiletInfo(Integer.parseInt(request.getParameter("no")));
%>
[
	{
	no:"<%=vo.getNo()%>",
	kind:"<%=vo.getKind()%>",
	name:"<%=vo.getName()%>",
	roadAddr:"<%=vo.getRoadAddr()%>",
	areaAddr:"<%=vo.getAreaAddr()%>",
	isUnisex:"<%=vo.isUnisex()%>",
	contact:"<%=vo.getContact()%>",
	opentime:"<%=vo.getOpenTime()%>",
	x:"<%=vo.getX()%>",
	y:"<%=vo.getY()%>",
	management:"<%=vo.getManagement()%>",
	isForChildren:"<%=vo.isForChiledren()%>",
	isForDisabled:"<%=vo.isForDisabled()%>"
	}
]