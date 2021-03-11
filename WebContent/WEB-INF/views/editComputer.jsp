<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<style><%@include file="../../resources/css/bootstrap.min.css"%></style>
<style><%@include file="../../resources/css/font-awesome.css"%></style>
<style><%@include file="../../resources/css/main.css"%></style>
<script><%@include file="../../resources/js/addComputer.js"%></script>
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/home"> Application - Computer Database </a>
        </div>
    </header>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        id: ${computer.id}
                    </div>
                    <h1>Edit Computer</h1>

                    <form action="${pageContext.request.contextPath}/home/edit" method="POST">
                        <input type="hidden" value="${computer.id}" id="id" name="id"/> <!-- TODO: Change this value with the computer id -->
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName">Computer name</label>
                                <input type="text" class="form-control" id="name" name="name" value="${computer.name}">
                            </div>
                            <div class="form-group">
                                <label for="introduced">Introduced date</label>
                                <input type="date" class="form-control" id="introduced" name="introduced" onchange = limitMinDate(this.value) value="${computer.introduced}">
                            </div>
                            <div class="form-group">
                                <label for="discontinued">Discontinued date</label>
                                <input type="date" class="form-control" id="discontinued" name="discontinued" onchange = limitMaxDate(this.value) value="${computer.discontinued}">
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label>
                                <select class="form-control" id="companyId" name="companyId">
                                	<option value="${computer.companyId}">${computer.companyName}</option>
                                    <c:forEach items="${companies}" var="company">
        								<option value="${company.id}">${company.name}</option>
    								</c:forEach>
                                </select>
                            </div>          
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Edit" class="btn btn-primary">
                            or
                            <a href="${pageContext.request.contextPath}/home" class="btn btn-default">Cancel</a>
                        </div>
                        <div>
                            ${errorMessage}
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</body>
</html>