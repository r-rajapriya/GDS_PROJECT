<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
    	<%@include file="include.jsp" %> 
  
        <script>
        	function submitLogin() {
        		var elementName = document.getElementById('userId');
        		var elementValue = elementName.value;        		
        		if (! isValidUsername(elementValue)) {
        			alert ("Invalid User ID");
        			elementName.focus();
        			return false;
        		} 
        		
        		elementName = document.getElementById('password');
        		elementValue = elementName.value;
        		if (! isValidPassword(elementValue)) {
        			alert ("Invalid Password");
        			elementName.focus();
        			return false;
        		} 
        		document.getElementById("LoginForm").submit();
        	}
        	function frmClear() {
        		document.getElementById("LoginForm").reset();
        		document.getElementById('userId').value = "";
        		document.getElementById('password').value = "";
        	} 	
        </script>
        
    </head>
    
    <body>

    	<div class="section" id="page"> <!-- Defining the #page section with the section tag -->
    
            <%@include file="header.jsp" %>
            
            <div class="section" id="articles"> <!-- A new section with the articles -->
                <div class="line"></div>  <!-- Dividing line -->
                   <form action="processLogin" method="post" id="LoginForm" class="white-pink">
				    <h1>Login</h1>
				    <c:if test="${not empty msg}"> 
				    <label style="color: #00ff00;">
				        ${msg}
				    </label>
				    </c:if>
				    <c:if test="${not empty errMsg}"> 
				    <label style="color: #ff0000;">
				        ${errMsg}
				    </label>
				    </c:if>
				    <table id="gdsForm">
				    	<tr>
				    		<td class="lblTD">User Id :</td>
				    		<td class="inputTD">
				    			<input id="userId" type="text" name="userId" maxlength="10" value="${user.userId}"/>
				    		</td>
				    		<td class="lblTD">&nbsp;</td>
				    		<td class="inputTD">&nbsp;</td>
				    	</tr>
				    	<tr>
				    		<td class="lblTD">Password :</td>
				    		<td class="inputTD">
				    			<input id="password" type="password" name="password" maxlength="12" value="${user.password}"/>
				    		</td>
				    		<td class="lblTD">&nbsp;</td>
				    		<td class="inputTD">&nbsp;</td>
				    	</tr>
				    </table>
				    <input type="button" class="button" value="Login" onclick="submitLogin();"/>&nbsp;
  					<input type="button" class="button" value="Clear" onclick="frmClear();"/>&nbsp;	 					
				</form>
            </div>
                	
		</div> <!-- Closing the #page section -->
    </body>
</html>
