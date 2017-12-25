<%@page import="evaluation.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%
response.addHeader("Access-Control-Allow-Origin", "*");  
response.setHeader("Access-Control-Allow-Headers", "origin, x-requested-with, content-type, accept");
%>
<%! 
	EvaluationDAO dao = new EvaluationDAO();
	EvaluationVO vo[];
%>
<%
	vo = dao.selectEvaluation(Integer.parseInt(request.getParameter("no")));
%>
[
<%	for(int i = 0; i < vo.length; i++){%>
	{
		comment:"<%=vo[i].getComment()%>",
		star:<%=vo[i].getStar()%>,
		date:<%=vo[i].getDate()%>
	}<%if(i != vo.length-1) {%>,<%}%>
<%	}%>
];