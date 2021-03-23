<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<title><fmt:message key="label.all.display.application"/></title>
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
            <a class="navbar-brand" href="${pageContext.request.contextPath}/home"><fmt:message key="label.all.display.application"/></a>
            <a class="pull-right" href="?lang=fr"><fmt:message key="label.lang.fr" /></a>
			<a class="pull-right">|</a>
        	<a class="pull-right" href="?lang=en"><fmt:message key="label.lang.en" /></a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1><fmt:message key="label.all.display.addComputer"/></h1>
                    <form action="${pageContext.request.contextPath}/home/add" method="POST">
                        <fieldset>
                            <div class="form-group">
                                <label for="name"><fmt:message key="label.dashboard.display.name"/></label>
                                <input type="text" class="form-control" id="name" name="name" placeholder="<fmt:message key="label.dashboard.display.name"/>" required>
                            </div>
                            <div class="form-group">
                                <label for="introduced"><fmt:message key="label.dashboard.display.introduced"/></label>
                                <input type="date" class="form-control" id="introduced" name="introduced" placeholder="<fmt:message key="label.all.input.name"/>" onchange = limitMinDate(this.value)>
                            </div>
                            <div class="form-group">
                                <label for="discontinued"><fmt:message key="label.dashboard.display.discontinued"/></label>
                                <input type="date" class="form-control" id="discontinued" name="discontinued" placeholder="<fmt:message key="label.all.input.name"/>" onchange = limitMaxDate(this.value)>
                            </div>
                            <div class="form-group">
                                <label for="companyId"><fmt:message key="label.dashboard.display.company"/></label>
                                <select class="form-control" id="companyId" name="companyId">
                                    <option value="0"><fmt:message key="label.addComputer.input.company"/></option>
                                    <c:forEach items="${companies}" var="company">
        								<option value="${company.id}">${company.name}</option>
    								</c:forEach>
                                </select>
                            </div>                  
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="<fmt:message key="label.all.button.add"/>" class="btn btn-primary">
                            or
                            <a href="${pageContext.request.contextPath}/home" class="btn btn-default"><fmt:message key="label.all.button.cancel"/></a>
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