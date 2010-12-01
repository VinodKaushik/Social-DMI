<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="banner.jsp" />

<div class="sysmessage">
	<p class="errmessage">
	Ooops! You should not have seen this.. Panic!! Call the developers!<br />
	${message }</p>
  Or Try visiting <a href="home.do" title="go to home page ">homepage </a>again.
</div>
<jsp:include page="footer.jsp" />