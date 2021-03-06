<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<!-- Bootstrap -->
<style><%@include file="../../resources/css/bootstrap.min.css"%></style>
<style><%@include file="../../resources/css/font-awesome.css"%></style>
<style><%@include file="../../resources/css/main.css"%></style>
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="${pageContext.request.contextPath}/home"><fmt:message key="label.all.display.application"/></a>
			<a class="pull-right" href="?lang=fr"><fmt:message key="label.lang.fr" /></a>
			<a class="pull-right">|</a>
        	<a class="pull-right" href="?lang=en"><fmt:message key="label.lang.en" /></a>
		</div>
		
	</header>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">${rows} <fmt:message key="label.dashboard.display.number"/></h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="<fmt:message key="label.dashboard.input.filter"/>" /> <input
							type="submit" id="searchsubmit" value="<fmt:message key="label.dashboard.button.filter"/>"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer"
						href="${pageContext.request.contextPath}/home/add"><fmt:message key="label.all.button.add"/></a> <a class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();"><fmt:message key="label.dashboard.button.edit"/></a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="#" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th><a href="<c:url value="/home">
				  		<c:param name="orderBy" value="name"/>
						</c:url>"><fmt:message key="label.dashboard.display.name"/></a></th>
						<th><a href="<c:url value="/home">
				  		<c:param name="orderBy" value="introduced"/>
						</c:url>"><fmt:message key="label.dashboard.display.introduced"/></a></th>
						<!-- Table header for Discontinued Date -->
						<th><a href="<c:url value="/home">
				  		<c:param name="orderBy" value="discontinued"/>
						</c:url>"><fmt:message key="label.dashboard.display.discontinued"/></a></th>
						<!-- Table header for Company -->
						<th><a href="<c:url value="/home">
				  		<c:param name="orderBy" value="company_id"/>
						</c:url>"><fmt:message key="label.dashboard.display.company"/></a></th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach items="${computers}" var="computer">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${computer.id}"></td>
							<td><a
								href="<c:url value="/home/edit"><c:param name="id" 
								value="${computer.id}"/></c:url>">${computer.name}</a></td>
							<td>${computer.introduced}</td>
							<td>${computer.discontinued}</td>
							<td>${computer.companyName}</td>
						</tr>
					</c:forEach>

				</tbody>
			</table>
		</div>
		<div>
            ${errorMessage}
        </div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<ul class="pagination">
				<li><a href="<c:url value="/home">
			  		<c:param name="index" value="${index = 1}"/>
			  		</c:url>"
			  		 aria-label="Previous"> <span
						aria-hidden="true">&laquo;</span>
				</a></li>
				<c:forEach var="i" begin="${indexLow}" end="${indexHigh}"
					step="1">
					<li><a
						href="<c:url value="/home">
				  		<c:param name="index" value="${i}"/>
						</c:url>">
					${i}</a></li>
				</c:forEach>
				<li><a href="<c:url value="/home">
			  		<c:param name="index" value="${index = indexMax}"/>
					</c:url>" 
					aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
			</ul>

			<div class="btn-group btn-group-sm pull-right" role="group">
				<a href="<c:url value="/home">
				  		<c:param name="limitPage" value="10"/>
						</c:url>" class="btn btn-default">10</a>
				<a href="<c:url value="/home">
				  		<c:param name="limitPage" value="20"/>
						</c:url>" class="btn btn-default">20</a>
				<a href="<c:url value="/home">
				  		<c:param name="limitPage" value="50"/>
						</c:url>" class="btn btn-default">50</a>
			</div>
		</div>
	</footer>
	<script> <%@include file="../../resources/js/jquery.min.js"%></script>
	<script> <%@include file="../../resources/js/bootstrap.min.js"%></script>
	<script> <%@include file="../../resources/js/dashboard.js"%></script>
	<script type="text/javascript">
        var strings = new Array();
        strings['view'] = "<fmt:message key="label.dashboard.button.view" />";
        strings['edit'] = "<fmt:message key="label.dashboard.button.edit" />";
    </script>

</body>
</html>