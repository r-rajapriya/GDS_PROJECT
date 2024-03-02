<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
    	<%@include file="include.jsp" %>
    </head>
    
    <body>
    
    	<div class="section" id="page"> <!-- Defining the #page section with the section tag -->
    
            <%@include file="header.jsp" %>
            
            <div class="section" id="articles"> <!-- A new section with the articles -->
                	<div class="line"></div>  <!-- Dividing line -->
                   <form action="error" method="post" id="errForm" class="white-pink">
				    <h1>Error</h1>
				    <label style="color: #ff0000;">
				        An unknown error has occured while processing your request. Please try again.
				    </label>
					</form>
            </div>
               	
		</div> <!-- Closing the #page section -->
    </body>
</html>
