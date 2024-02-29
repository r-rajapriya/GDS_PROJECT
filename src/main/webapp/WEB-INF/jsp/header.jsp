<c:set var="now" value="<%=new java.util.Date()%>" />
<div class="header"> <!-- Defining the header section of the page with the appropriate tag -->
	<h1>GDS</h1>
	<h2>Restaurant Picker</h2>	
	<c:if test="${not empty loginUser}">	
	<div id="sessiondetails">Welcome ${loginUser.userName}  &nbsp;&nbsp;&nbsp;
	<a href="/gds/session/listSessions" class="up">Home</a>&nbsp; 
	|| &nbsp;<a href="/gds/logout" class="up">Logout</a>&nbsp;</div>
	</c:if>
</div>