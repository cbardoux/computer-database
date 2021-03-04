<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<style><%@include file="../../resources/css/bootstrap.min.css"%></style>
<style><%@include file="../../resources/css/font-awesome.css"%></style>
<style><%@include file="../../resources/css/main.css"%></style>
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard.html"> Application -
				Computer Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">${rows} Computers found</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="Search name" /> <input
							type="submit" id="searchsubmit" value="Filter by name"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer"
						href="${pageContext.request.contextPath}/home/add">Add
						Computer</a> <a class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();">Edit</a>
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
						<th>Computer name</th>
						<th>Introduced date</th>
						<!-- Table header for Discontinued Date -->
						<th>Discontinued date</th>
						<!-- Table header for Company -->
						<th>Company</th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach items="${computers}" var="computer">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="0"></td>
							<td><a
								href="${pageContext.request.contextPath}/home/edit?id=${computer.id}"
								onclick="">${computer.name}</a></td>
							<td>${computer.introduced}</td>
							<td>${computer.discontinued}</td>
							<td>${computer.company_name}</td>
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
				  		<c:param name="limit" value="10"/>
						</c:url>" class="btn btn-default">10</a>
				<a href="<c:url value="/home">
				  		<c:param name="limit" value="20"/>
						</c:url>" class="btn btn-default">20</a>
				<a href="<c:url value="/home">
				  		<c:param name="limit" value="50"/>
						</c:url>" class="btn btn-default">50</a>
			</div>
		</div>
	</footer>
	<script src="resources/js/jquery.min.js"></script>
	<script src="resources/js/bootstrap.min.js"></script>
	<script src="resources/js/dashboard.js"></script>

</body>
</html>