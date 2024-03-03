<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <%@include file="include.jsp" %>  
        <script>
        	function createSession() {        		
        		elementName = document.getElementById('sessionName');
	       		elementValue = elementName.value;
        		if (elementValue.trim() == "") {
        			alert ("Enter valid Session Name");
        			document.getElementById('sessionName').value = '';
        			elementName.focus();
        			return false;
        		}
        		
        		elementName = document.getElementById('eventDate');
        		elementValue = elementName.value;
        		if (elementValue.trim() == "") {
        			alert ("Enter valid Event Date");
        			document.getElementById('eventDate').value = getCurrentDate();
        			elementName.focus();
        			return false;
        		}       		 
        		document.getElementById("crtSessForm").action = "createSession";        		
        		document.getElementById("crtSessForm").submit();
        	}
        	function frmClear() {
        		document.getElementById("crtSessForm").reset();
        	} 	
          	        	
        </script>   
    </head>    
    <body>
    	<div class="section" id="page"> <!-- Defining the #page section with the section tag -->
    
            <%@include file="header.jsp" %>
            
            <div class="section" id="articles"> <!-- A new section with the articles -->
                <div class="line"></div>  <!-- Dividing line -->
                	<form action="createSession" method="post" id="crtSessForm" class="white-pink" style="max-width: 900px;">                  
				    <h1>Sessions</h1>
				    <label style="color: #00ff00;">
				        <span>${msg}</span>
				    </label>
				    <c:if test="${not empty errMsg}"> 
				    <label style="color: #FF0000;">
				        ${errMsg}
				    </label>
				    </c:if>
				    <h4>Enter the details to initiate new session for team lunch</h4>
				    <table id="gdsForm">
				    	<tr>
				    		<td class="lblTD">Session Name :</td>
				    		<td class="inputTD">
				    			<input id="sessionName" type="text" name="sessionName" maxlength="60" value="${pickerSession.sessionName}"/>
				    		</td>				    		
				    	</tr>
				    	<tr>
				    		<td class="lblTD">Event Date :</td>
				    		<td class="inputTD">
				    			<input id="eventDate" type="text" name="eventDate" maxlength="10" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${pickerSession.eventDate}"/>" required onclick="GetDate(this);"/>
				    			<script>document.getElementById("eventDate").value = getCurrentDate();</script>
				    		</td>
				    	</tr>
				    	<tr>
					    	<td class="lblTD">Invite All Team Members : </td>
			                <td><input type="checkbox" name="inviteAll" id="inviteAll" value="invited"/></td>
		                </tr>				    	
				    </table><br/>	
				    		<label> 
				    			<input type="button" class="button" value="Submit" onclick="createSession();"/>&nbsp;
			       				<input type="button" class="button" value="Clear" onclick="frmClear();"/>&nbsp;				    
				  			 </label>
				    <br/>				    	
			        <label style="color: #000000;">
				        <span>${chMsg}</span>
				    </label>
				    <div class="CSSTableGenerator" >
		                <table>
		                    <tr>
		                        <td>S.No</td>
		                        <td>Session Name</td>
		                        <td>Session Status</td>
		                        <td>Event On</td>
			                    <td>Created By</td>	
			                    <td>Joined On</td>		                    
			                    <td>Chosen Restaurant</td>
		                    </tr>
		                    <% int i = 1; %>
		                    <c:forEach items="${listSessions}" var="aSession">
		                    <tr>
		                    	<td><%= i++ %></td>
		                        <td>
		                        	<c:choose>
						  				<c:when test="${aSession.userJoinDate != null}"> 
		                        			<a href="/gds/session/viewSession?sessionId=<c:out value="${aSession.sessionId}"/>" class="up">${aSession.sessionName}</a>
				                        </c:when>
									  <c:otherwise>
									  	${aSession.sessionName}
									  </c:otherwise>
									</c:choose> 		                        
		                        </td>
		                        <td>${aSession.sessionStatus}</td>
		                        <td><fmt:formatDate pattern="dd/MM/yyyy" value="${aSession.eventDate}"/></td>
		                        <td>${aSession.createdBy}</td>
		                        <td>
		                        	<c:choose>		                        		
						  				<c:when test="${aSession.userJoinDate != null}">
		                        			<fmt:formatDate pattern="d/M/yyyy, hh:mm:ss aa" value="${aSession.userJoinDate}"/>
				                        </c:when>
				                        <c:when test="${aSession.sessionStatus == 'Open'}">
				                        	<a href="/gds/choice/join?sessionId=<c:out value="${aSession.sessionId}"/>" class="up">Click to join</a>
				                        </c:when>
									  <c:otherwise>
									  	    -	  
									  </c:otherwise>
									</c:choose> 
								</td>		                        
		                        <td>${aSession.selectedRestaurant}</td>
		                    </tr>
			                </c:forEach>
		                </table>
		            </div>
				    <br/>
				</form>
            </div>       	
        	
		</div> <!-- Closing the #page section -->
    </body>
</html>

