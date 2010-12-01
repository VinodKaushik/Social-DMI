<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${!(empty errors)}">
	<div class="sysmessage">
	<p class="errmessage"><c:forEach var="error" items="${errors}">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			${error}
			<br />
	</c:forEach></p>
	</div>
</c:if>

<c:if test="${!(empty message)}">
	<div class="sysmessage">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	${message}</div>
</c:if>

