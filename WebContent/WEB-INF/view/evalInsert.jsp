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
	EvaluationVO vo = new EvaluationVO();
%>
<%
	vo.setNo(Integer.parseInt(request.getParameter("no")));
	vo.setComment(request.getParameter("comment"));
	vo.setStar(Integer.parseInt(request.getParameter("star")));
	vo.setDate(request.getParameter("date"));
%>
<%=dao.insertEvaluation(vo)%>