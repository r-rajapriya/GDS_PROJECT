<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <%@include file="include.jsp" %>  
        <script>
        async function submitChoice() {  
        		var sessId = document.getElementById('sessionId').value;
        		var restName = document.getElementById('restaurantName').value;
        		if (restName.trim() == "") {
        			alert ("Enter valid Restaurant Name");
        			document.getElementById('restaurantName').value = '';
        			document.getElementById('restaurantName').focus();        			
        			return false;
        		}        		
        		
        		try
           		{   		  			
           			const reqUrl = "${pageContext.request.contextPath}"+"/api/submitChoice?sessionId="+sessId+"&restaurantName="+restName; 
           			
           			const settings = {
           			        method: 'POST',
           			        headers: {
           			            Accept: 'application/json',
           			            'Content-Type': 'application/json',
           			        }
           			    };
           			
           			//Calls Rest controller
           			const response = await fetch(reqUrl, settings);           					
           			if(!response.ok)
 					{
 						alert("Error while calling rest api : "+response.status);
 						return;
 					}
 					const myJson = await response.json();		        
			    	
 					//Process the response Json to create Choice List
 					var msg = '<label style="color: #000000;"><span>Your Choice got submitted successfully</span></label>';
 					var headerStr = "<table><tr><td>S.No</td><td>User ID</td><td>Joined On</td><td>Restaurant Name</td><td>Submitted On</td></tr>";
 					var endStr = "</table>";
 					var contentStr = "";
					var chList = myJson.userChoices;
					
					for(var i = 0; i < chList.length; i++) 
					{
            		    var aChoice = chList[i];
						contentStr += '<tr><td>' + (i+1) + '</td>';
            		    contentStr += '<td>' + aChoice.userId + '</td>';            		    
            		    
            		    if(aChoice.joinDate != null) {
            		    	const joinDate = new Date(aChoice.joinDate);
            		    	contentStr += '<td>' + joinDate.toLocaleString('en-IN') + '</td>';
            		    }          		    	
            		    else
            		    	contentStr += '<td>&nbsp;</td>';
            		    	
            		    if(aChoice.restaurantChoice != null)
            		    	contentStr += '<td>' + aChoice.restaurantChoice + '</td>';
           		    	else
               		    	contentStr += '<td>&nbsp;</td>';
               		    	
            		    if(aChoice.submitDate != null) {
            		    	var submitDate = new Date(aChoice.submitDate);
            		    	contentStr += '<td>' + submitDate.toLocaleString('en-IN') + '</td>';
            		    }
            		    else
            		    	contentStr += '<td>&nbsp;</td>';
					}
 					
					//Set new table of choices to the choice Div object
 					document.getElementById("choiceListDiv").innerHTML = msg+headerStr+contentStr+endStr; 
           		}
        		catch(err)
        		{        			
        			alert("Error while refresh Choice List : "+err);
        		}        		
        	}
        	function frmClear() {
        		document.getElementById("crtChoiceForm").reset();
        	} 
        	function frmBack() {
    			document.getElementById("crtChoiceForm").action = "/gds/session/listSessions";
        		document.getElementById("crtChoiceForm").submit();        			
        	}      	  
        	function inviteAll() {
    			document.getElementById("crtChoiceForm").action = "/gds/session/inviteAll";
        		document.getElementById("crtChoiceForm").submit();        			
        	} 
        	function endSession() {
    			document.getElementById("crtChoiceForm").action = "/gds/session/endSession";
        		document.getElementById("crtChoiceForm").submit();        			
        	}        	        	
        </script>   
    </head>    
    <body>
    	<div class="section" id="page"> <!-- Defining the #page section with the section tag -->
    
            <%@include file="header.jsp" %>
            
            <div class="section" id="articles"> <!-- A new section with the articles -->
                <div class="line"></div>  <!-- Dividing line -->
                	<form action="submitChoice" method="post" id="crtChoiceForm" class="white-pink" style="max-width: 900px;">                  
				    <h1>Session</h1>
				    <label style="color: #00ff00;">
				        <span>${msg}</span>
				    </label>
				    <c:if test="${not empty errMsg}"> 
				    <label style="color: #ff0000;">
				        ${errMsg}
				    </label>
				    </c:if>				    
				    <table id="gdsForm">
				    	<tr><td class="lblTD">Session Name :</td><td class="lblTD">${pickerSession.sessionName}
				    	<input id="sessionId" type="hidden" name="sessionId" value="${pickerSession.sessionId}"/></td>
				    	<td class="lblTD">Created By :</td><td class="lblTD">${pickerSession.createdBy}</td></tr>
				    	<tr><td class="lblTD">Session Status :</td><td class="lblTD">${pickerSession.sessionStatus}</td>
				    	<td class="lblTD">Started At :</td><td class="lblTD">${pickerSession.startDate}</td></tr>	
				    	<tr><td class="lblTD">Event On :</td><td class="lblTD"><fmt:formatDate pattern="dd/MM/yyyy" value="${pickerSession.eventDate}"/></td>
				    	<td class="lblTD">Completed At :</td><td class="lblTD">${pickerSession.endDate}</td></tr>				    	
	    				<c:choose>
						  <c:when test="${not empty pickerSession.selectedRestaurant}">
						  	<tr><td class="lblTD">Randomly Selected Restaurant :</td><td class="lblTD">${pickerSession.selectedRestaurant}</td></tr>
						  </c:when>
						  <c:otherwise>
							  <tr><td class="lblTD">Your choice of Restaurant :</td>
							  	<td class="inputTD">
					    			<input id="restaurantName" type="text" name="restaurantName" maxlength="60"/>
					    		</td></tr>						    
						  </c:otherwise>
						</c:choose>
				    </table>	
				    		<label> 
				    		<c:if test="${empty pickerSession.selectedRestaurant}">
				    			<input type="button" class="button" value="Submit" onclick="submitChoice();"/>&nbsp;				    			
			       				<input type="button" class="button" value="Clear" onclick="frmClear();"/>&nbsp;	
			       			</c:if>	
			       			
		       				<input type="button" class="button" value="Back" onclick="frmBack();"/>&nbsp;	
		       						       				
		       				<c:if test="${pickerSession.inviteAll ne 'invited'}"> 
		       					<input type="button" class="button" value="Invite All" onclick="inviteAll();"/>&nbsp;				    
		       				</c:if>	
		       				
		       				<c:if test="${(pickerSession.createdBy eq loginUser.userId) and (empty pickerSession.selectedRestaurant) and pickerSession.inviteAll eq 'invited'}"> 
		       					<input type="button" class="button" value="End Session" onclick="endSession();"/>&nbsp;	
		       				</c:if>	    
			  			 </label>
				    <br/><br/>				    	
			        <h1>Given Choices</h1>
				    <div id="choiceListDiv" class="CSSTableGenerator" >
		                <table>
		                    <tr>
		                        <td>S.No</td>
		                        <td>User ID</td>
		                        <td>Joined On</td>
		                        <td>Restaurant Name</td>
			                    <td>Submitted On</td>			                    
		                    </tr>
		                    <% int i = 1; %>
			                    <c:forEach items="${pickerSession.userChoices}" var="aChoice">
			                    <tr>
			                    	<td><%= i++ %></td>
			                        <td>${aChoice.userId}</td>
			                        <td><fmt:formatDate pattern="d/M/yyyy, hh:mm:ss aa" value="${aChoice.joinDate}"/></td>
			                        <td>${aChoice.restaurantChoice}</td>
			                        <td><fmt:formatDate pattern="d/M/yyyy, hh:mm:ss aa" value="${aChoice.submitDate}"/></td>
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

